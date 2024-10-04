package com.Hospital.Tarea.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Hospital.Tarea.Repository.PacienteRepository;
import com.Hospital.Tarea.DTO.PacienteRequest;
import com.Hospital.Tarea.Entity.Paciente;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;


    public List<Paciente> Lista_Pacientes(String estado){
        return pacienteRepository.findByEstadoAndEliminadoFalseOrderByFechaCreacionAsc(estado, PageRequest.of(0, 10));
    }

    public Paciente Buscar(Long id){
        return pacienteRepository.findById(id).get();
    }


    public List<Paciente> Lista_Nombre_Codigo(String estado, String busqueda){
        return pacienteRepository.findByEstadoAndNombreContainingOrEstadoAndCuiContaining(estado, busqueda, estado, busqueda);
    }

    public void ocultar_paciente(long id){
        Paciente paciente = pacienteRepository.findById(id).get();
        paciente.setEliminado(true);
        pacienteRepository.save(paciente);
    }

    public PacienteRequest ActualData(Paciente paciente){
        PacienteRequest pacienteRequest = new PacienteRequest();
        pacienteRequest.setId(paciente.getId());
        pacienteRequest.setNombre(paciente.getNombre());
        pacienteRequest.setApellido(paciente.getApellido());
        pacienteRequest.setEmail(paciente.getEmail());
        pacienteRequest.setTelefono(paciente.getTelefono());
        pacienteRequest.setEstado(paciente.getEstado());
        pacienteRequest.setSexo(paciente.getSexo());
        pacienteRequest.setFecha_nacimiento(paciente.getFecha_nacimiento());
        pacienteRequest.setHistoria_medica(paciente.getHistoria_medica());
        return pacienteRequest;
    }

    public Paciente save(PacienteRequest pacienteRequest, String imagen){
        Paciente paciente;
        if(pacienteRequest.getId()== null){
            paciente = new Paciente();
            paciente.setEliminado(false);
        }else{
            paciente = pacienteRepository.findById(pacienteRequest.getId()).get();
        }
        paciente.setNombre(pacienteRequest.getNombre());
        paciente.setApellido(pacienteRequest.getApellido());
        paciente.setEmail(pacienteRequest.getEmail());
        paciente.setTelefono(pacienteRequest.getTelefono());
        paciente.setSexo(pacienteRequest.getSexo());
        paciente.setCui(pacienteRequest.getCui());
        paciente.setEstado(pacienteRequest.getEstado());
        paciente.setSexo(pacienteRequest.getSexo());
        paciente.setImagen(imagen);
        paciente.setFecha_nacimiento(pacienteRequest.getFecha_nacimiento());
        paciente.setHistoria_medica(pacienteRequest.getHistoria_medica());
        return pacienteRepository.save(paciente);
    }
}
