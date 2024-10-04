package com.Hospital.Tarea.Controller;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Hospital.Tarea.DTO.PacienteRequest;
import com.Hospital.Tarea.Entity.Paciente;
import com.Hospital.Tarea.Service.PacienteService;

import jakarta.validation.Valid;
@Controller
@RequestMapping("/paciente")
public class PacienteController {
    @Autowired
    PacienteService pacienteService;

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam Long id){
        try{
            pacienteService.ocultar_paciente(id);
            return "redirect:/pacientes";
        }catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect: /pacientes";
    }
    
    @GetMapping("/guardar")
    public String request(@RequestParam(required = false) Long id, Model model){
        PacienteRequest pacienteRequest;
        if(id!=null){
            try{
                Paciente paciente = pacienteService.Buscar(id);
                model.addAttribute("paciente", paciente);
                pacienteRequest = pacienteService.ActualData(paciente);
            }catch(Exception ex){
                System.out.println("Exception: " + ex.getMessage());
                return "redirect: /pacientes";
            }
        }else{
            pacienteRequest = new PacienteRequest();
            model.addAttribute("paciente", new Paciente());
        }

        model.addAttribute("request", pacienteRequest);
        
        return "r_paciente";
    }

    @PostMapping("/guardar")
    public String save(@RequestParam(required=false) Long id, 
    @Valid @ModelAttribute("request") PacienteRequest pacienteRequest, 
    BindingResult result, Model model){

        String nombre_imagen;

        if(result.hasErrors()){
            if(id!=null){
                Paciente paciente = pacienteService.Buscar(id);
                model.addAttribute("paciente", paciente);
            }else{
                model.addAttribute("paciente", new Paciente());
            }
            return "r_paciente";
        }

        try{
            //Logica de actualización
            if(id !=null){
                Paciente paciente = pacienteService.Buscar(id);
                model.addAttribute("paciente", paciente);
                if(!pacienteRequest.getImagen().isEmpty()){
                    String upload = "src/main/resources/static/images/";
                    Path oldPath = Paths.get(upload + paciente.getImagen());
                    try{
                        Files.delete(oldPath);
                    }catch(Exception ex){
                        System.out.println("Exception" + ex.getMessage());
                    }

                    MultipartFile image= pacienteRequest.getImagen();
                    Date creado = new Date();
                    nombre_imagen = creado.getTime() + "-" + image.getOriginalFilename();
                    try(InputStream inputStream = image.getInputStream()){
                        Files.copy(inputStream, Paths.get(upload, nombre_imagen), StandardCopyOption.REPLACE_EXISTING);
                    }
                }else{
                    nombre_imagen = paciente.getImagen();
                }
            }else{
                if(pacienteRequest.getImagen().isEmpty()){
                    result.addError(new FieldError("pacienteRequest", "image", "El archivo de imagen no puede estar vacío"));
                    return "r_paciente";
                }
                MultipartFile image = pacienteRequest.getImagen();
                Date creado = new Date();
                nombre_imagen = creado.getTime() + "-" + image.getOriginalFilename();

                String upload = "src/main/resources/static/images/";
                Path uploadPath = Paths.get(upload);

                if(!Files.exists(uploadPath)){
                    Files.createDirectories(uploadPath);
                }try(InputStream inputStream = image.getInputStream()){
                    Files.copy(inputStream, Paths.get(upload, nombre_imagen), StandardCopyOption.REPLACE_EXISTING);
                }
            }
            pacienteService.save(pacienteRequest, nombre_imagen);
        }catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/pacientes";
    }
}
