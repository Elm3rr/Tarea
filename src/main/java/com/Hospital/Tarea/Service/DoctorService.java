package com.Hospital.Tarea.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Hospital.Tarea.DTO.DoctorRequest;
import com.Hospital.Tarea.Entity.Doctor;
import com.Hospital.Tarea.Repository.DoctorRepository;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;


    public List<Doctor> Lista_Pacientes(String estado){
        return doctorRepository.findByEstadoAndEliminadoFalseOrderByFechaCreacionAsc(estado, PageRequest.of(0, 10));
    }

    public Doctor Buscar(Long id){
        return doctorRepository.findById(id).get();
    }

    public List<Doctor> Doctor_buscado(String estado, String busqueda){
        return doctorRepository.findByEstadoAndNombreContainingOrEstadoAndColegiadoContaining(estado, busqueda, estado, busqueda);
    }

    public void ocultar_doctor(long id){
        Doctor doctor = doctorRepository.findById(id).get();
        doctor.setEliminado(true);
        doctorRepository.save(doctor);
    }

    public DoctorRequest ActualData(Doctor doctor){
        DoctorRequest doctorRequest = new DoctorRequest();
        doctorRequest.setId(doctor.getId());
        doctorRequest.setNombre(doctor.getNombre());
        doctorRequest.setCui(doctor.getCui());
        doctorRequest.setApellido(doctor.getApellido());
        doctorRequest.setEmail(doctor.getEmail());
        doctorRequest.setTelefono(doctor.getTelefono());
        doctorRequest.setEstado(doctor.getEstado());
        doctorRequest.setColegiado(doctor.getColegiado());
        doctorRequest.setEspecialidad(doctor.getEspecialidad());
        doctorRequest.setDescripcion(doctor.getDescripcion());
        doctorRequest.setSexo(doctor.getSexo());
        doctorRequest.setFecha_actualizacion(doctor.getFecha_actualizacion());
        
        return doctorRequest;
    }

    public Doctor save(DoctorRequest doctorRequest, String nombre){
        Doctor doctor;
        if(doctorRequest.getId()== null){
            doctor = new Doctor();
            doctor.setEliminado(false);
        }else{
            doctor = doctorRepository.findById(doctorRequest.getId()).get();
        }
        doctor.setNombre(doctorRequest.getNombre());
        doctor.setApellido(doctorRequest.getApellido());
        doctor.setEmail(doctorRequest.getEmail());
        doctor.setTelefono(doctorRequest.getTelefono());
        doctor.setEstado(doctorRequest.getEstado());
        doctor.setCui(doctorRequest.getCui());
        doctor.setImagen(nombre);
        doctor.setColegiado(doctorRequest.getColegiado());
        doctor.setEspecialidad(doctorRequest.getEspecialidad());
        doctor.setDescripcion(doctorRequest.getDescripcion());
        doctor.setSexo(doctorRequest.getSexo());
        return doctorRepository.save(doctor);
    }
}
