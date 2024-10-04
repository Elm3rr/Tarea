package com.Hospital.Tarea.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Hospital.Tarea.Entity.Doctor;
import com.Hospital.Tarea.Entity.Enfermera;
import com.Hospital.Tarea.Entity.Paciente;
import com.Hospital.Tarea.Service.DoctorService;
import com.Hospital.Tarea.Service.EnfermeraService;
import com.Hospital.Tarea.Service.PacienteService;

@Controller
@RequestMapping({"","/"})
public class PublicController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private EnfermeraService enfermeraService;

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/doctores")
    public String doctores(Model model,
    @RequestParam(value="estado", defaultValue = "Activo") String estado,
    @RequestParam(value="busqueda", required=false) String busqueda){
        List<Doctor> doctores;
        if(busqueda != null && !busqueda.isEmpty()){
            doctores = doctorService.Doctor_buscado(estado, busqueda);
        }else{
            doctores = doctorService.Lista_Pacientes(estado);
        }
        model.addAttribute("doctores", doctores);
        return "doctor";
    }

    @GetMapping("/enfermeras")
    public String enfermeras(Model model,
    @RequestParam(value="estado", defaultValue = "Activo") String estado,
    @RequestParam(value="busqueda", required=false) String busqueda){

        List<Enfermera> enfermeras;
        if(busqueda !=null && !busqueda.isEmpty()){
            enfermeras = enfermeraService.Lista_Nombre_Codigo(estado, busqueda);
        }else{
            enfermeras = enfermeraService.Lista_Pacientes(estado);
        } 
        model.addAttribute("enfermeras", enfermeras);
        return "enfermera";
    }

    @GetMapping("/pacientes")
    public String pacientes(Model model,
    @RequestParam(value="estado", defaultValue = "Activo") String estado,
    @RequestParam(value="busqueda", required=false) String busqueda){
        List<Paciente> pacientes;
        if(busqueda !=null && !busqueda.isEmpty()){
            pacientes = pacienteService.Lista_Nombre_Codigo(estado, busqueda);
        }else{
            pacientes = pacienteService.Lista_Pacientes(estado);
        } 
        model.addAttribute("pacientes", pacientes);
        return "paciente";
    }
}
