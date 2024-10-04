package com.Hospital.Tarea.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Hospital.Tarea.Entity.Doctor;

public interface DoctorRepository extends JpaRepository <Doctor, Long> {
    List<Doctor> findByEstadoAndEliminadoFalseOrderByFechaCreacionAsc(String estado, Pageable pageable);
    List<Doctor> findByEstadoAndNombreContainingOrEstadoAndColegiadoContaining(String estado, String nombre, String estado2, String colegiado);
}
