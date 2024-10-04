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

import com.Hospital.Tarea.DTO.EnfermeraRequest;
import com.Hospital.Tarea.Entity.Enfermera;
import com.Hospital.Tarea.Service.EnfermeraService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/enfermera")
public class EnfermeraController {
    @Autowired
    EnfermeraService enfermeraService;
    
    @GetMapping("/eliminar")
    public String eliminar(@RequestParam Long id){
        try{
            enfermeraService.ocultar_enfermera(id);
            return "redirect:/enfermeras";
        }catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect: /enfermeras";
    }
    @GetMapping("/guardar")
    public String request(@RequestParam(required = false) Long id, Model model){
        EnfermeraRequest enfermeraRequest;
        if(id!=null){
            try{
                Enfermera enfermera = enfermeraService.Buscar(id);
                model.addAttribute("enfermera", enfermera);
                enfermeraRequest = enfermeraService.ActualData(enfermera);
            }catch(Exception ex){
                System.out.println("Exception: " + ex.getMessage());
                return "redirect: /enfermeras";
            }
        }else{
            enfermeraRequest = new EnfermeraRequest();
            model.addAttribute("enfermera", new Enfermera());
        }

        model.addAttribute("request", enfermeraRequest);
        
        return "r_enfermera";
    }

    @PostMapping("/guardar")
    public String save(@RequestParam(required=false) Long id, 
    @Valid @ModelAttribute("request") EnfermeraRequest enfermeraRequest, 
    BindingResult result, Model model){

        String nombre_imagen;

        if(result.hasErrors()){
            if(id!=null){
                Enfermera enfermera = enfermeraService.Buscar(id);
                model.addAttribute("enfermera", enfermera);
            }else{
                model.addAttribute("enfermera", new Enfermera());
            }
            return "r_enfermera";

        }

        try{
            //Logica de actualización
            if(id !=null){
                Enfermera enfermera = enfermeraService.Buscar(id);
                model.addAttribute("enfermera", enfermera);
                if(!enfermeraRequest.getImagen().isEmpty()){
                    String upload = "src/main/resources/static/images/";
                    Path oldPath = Paths.get(upload + enfermera.getImagen());
                    try{
                        Files.delete(oldPath);
                    }catch(Exception ex){
                        System.out.println("Exception" + ex.getMessage());
                    }

                    MultipartFile image= enfermeraRequest.getImagen();
                    Date creado = new Date();
                    nombre_imagen = creado.getTime() + "-" + image.getOriginalFilename();
                    try(InputStream inputStream = image.getInputStream()){
                        Files.copy(inputStream, Paths.get(upload, nombre_imagen), StandardCopyOption.REPLACE_EXISTING);
                    }
                }else{
                    nombre_imagen = enfermera.getImagen();
                }
            }else{
                if(enfermeraRequest.getImagen().isEmpty()){
                    result.addError(new FieldError("enfermeraRequest", "image", "El archivo de imagen no puede estar vacío"));
                    return "r_enfermera";
                }
                MultipartFile image = enfermeraRequest.getImagen();
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
            enfermeraService.save(enfermeraRequest, nombre_imagen);
        }catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/enfermeras";
    }
}
