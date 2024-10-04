package com.Hospital.Tarea.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Hospital.Tarea.Entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findByEstadoAndEliminadoFalseOrderByFechaCreacionAsc(String estado, Pageable pageable);
    List<Paciente> findByEstadoAndNombreContainingOrEstadoAndCuiContaining(String estado, String busqueda, String estado2, String busqueda2);
}
