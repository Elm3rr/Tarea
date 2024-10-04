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

import com.Hospital.Tarea.DTO.DoctorRequest;
import com.Hospital.Tarea.Entity.Doctor;
import com.Hospital.Tarea.Service.DoctorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @GetMapping("/eliminar")
    public String eliminar(@RequestParam Long id){
        try{
            doctorService.ocultar_doctor(id);
            return "redirect:/doctores";
        }catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect: /doctores";
    }
    
    @GetMapping("/guardar")
    public String request(@RequestParam(required = false) Long id, Model model){
        DoctorRequest doctorRequest;
        if(id!=null){
            try{
                Doctor doctor = doctorService.Buscar(id);
                model.addAttribute("doctor", doctor);
                doctorRequest = doctorService.ActualData(doctor);
            }catch(Exception ex){
                System.out.println("Exception: " + ex.getMessage());
                return "redirect: /doctores";
            }
        }else{
            doctorRequest = new DoctorRequest();
            model.addAttribute("doctor", new Doctor());
        }

        model.addAttribute("request", doctorRequest);
        return "r_doctor";
    }

    @PostMapping("/guardar")
    public String save(@RequestParam(required=false) Long id, 
    @Valid @ModelAttribute("request") DoctorRequest doctorRequest, 
    BindingResult result, Model model){

        String nombre_imagen;

        if(result.hasErrors()){
            if(id!=null){
                Doctor doctor = doctorService.Buscar(id);
                model.addAttribute("doctor", doctor);
            }else{
                model.addAttribute("doctor", new Doctor());
            }
            return "r_doctor";
        }

        try{
            //Logica de actualización
            if(id !=null){
                Doctor doctor = doctorService.Buscar(id);
                model.addAttribute("doctor", doctor);
                if(!doctorRequest.getImagen().isEmpty()){
                    String upload = "src/main/resources/static/images/";
                    Path oldPath = Paths.get(upload + doctor.getImagen());
                    try{
                        Files.delete(oldPath);
                    }catch(Exception ex){
                        System.out.println("Exception" + ex.getMessage());
                    }

                    MultipartFile image= doctorRequest.getImagen();
                    Date creado = new Date();
                    nombre_imagen = creado.getTime() + "-" + image.getOriginalFilename();
                    try(InputStream inputStream = image.getInputStream()){
                        Files.copy(inputStream, Paths.get(upload, nombre_imagen), StandardCopyOption.REPLACE_EXISTING);
                    }
                }else{
                    nombre_imagen = doctor.getImagen();
                }
            }else{
                if(doctorRequest.getImagen().isEmpty()){
                    result.addError(new FieldError("doctorRequest", "image", "El archivo de imagen no puede estar vacío"));
                    return "r_doctor";
                }
                MultipartFile image = doctorRequest.getImagen();
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
            doctorService.save(doctorRequest, nombre_imagen);
        }catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
        return "redirect:/doctores";
    }


}
