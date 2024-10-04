package com.Hospital.Tarea.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Hospital.Tarea.DTO.EnfermeraRequest;
import com.Hospital.Tarea.Entity.Enfermera;
import com.Hospital.Tarea.Repository.EnfermeraRepository;

@Service
public class EnfermeraService {

    @Autowired
    EnfermeraRepository enfermeraRepository;

    public List<Enfermera> Lista_Pacientes(String estado){
        return enfermeraRepository.findByEstadoAndEliminadoFalseOrderByFechaCreacionAsc(estado, PageRequest.of(0, 10));
    }

    public Enfermera Buscar(Long id){
        return enfermeraRepository.findById(id).get();
    }

    public List<Enfermera> Lista_Nombre_Codigo(String estado, String busqueda){
        return enfermeraRepository.findByEstadoAndNombreContainingOrEstadoAndCodigoAuxContaining(estado, busqueda, estado, busqueda);
    }

    public void ocultar_enfermera(long id){
        Enfermera enfermera = enfermeraRepository.findById(id).get();
        enfermera.setEliminado(true);
        enfermeraRepository.save(enfermera);
    }

    public EnfermeraRequest ActualData(Enfermera enfermera){
        EnfermeraRequest enfermeraRequest = new EnfermeraRequest();
        enfermeraRequest.setId(enfermera.getId());
        enfermeraRequest.setNombre(enfermera.getNombre());
        enfermeraRequest.setApellido(enfermera.getApellido());
        enfermeraRequest.setEmail(enfermera.getEmail());
        enfermeraRequest.setTelefono(enfermera.getTelefono());
        enfermeraRequest.setSexo(enfermera.getSexo());
        enfermeraRequest.setCui(enfermera.getCui());
        enfermeraRequest.setEstado(enfermera.getEstado());
        enfermeraRequest.setCodigoAux(enfermera.getCodigoAux());
        enfermeraRequest.setServicio(enfermera.getServicio());
        enfermeraRequest.setDescripcion(enfermera.getDescripcion());
        return enfermeraRequest;
    }

    public Enfermera save(EnfermeraRequest enfermeraRequest, String nombre){
        Enfermera enfermera;
        if(enfermeraRequest.getId()== null){
            enfermera = new Enfermera();
            enfermera.setEliminado(false);
        }else{
            enfermera = enfermeraRepository.findById(enfermeraRequest.getId()).get();
        }
        enfermera.setNombre(enfermeraRequest.getNombre());
        enfermera.setApellido(enfermeraRequest.getApellido());
        enfermera.setEmail(enfermeraRequest.getEmail());
        enfermera.setTelefono(enfermeraRequest.getTelefono());
        enfermera.setEstado(enfermeraRequest.getEstado());
        enfermera.setImagen(nombre);
        enfermera.setCui(enfermeraRequest.getCui());
        enfermera.setSexo(enfermeraRequest.getSexo());
        enfermera.setCodigoAux(enfermeraRequest.getCodigoAux());
        enfermera.setServicio(enfermeraRequest.getServicio());
        enfermera.setDescripcion(enfermeraRequest.getDescripcion());
        return enfermeraRepository.save(enfermera);
    }

    
}
