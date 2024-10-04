package com.Hospital.Tarea.Repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Hospital.Tarea.Entity.Enfermera;

public interface EnfermeraRepository extends JpaRepository<Enfermera, Long>{
    List<Enfermera> findByEstadoAndEliminadoFalseOrderByFechaCreacionAsc(String estado, Pageable pageable);
    List<Enfermera> findByEstadoAndNombreContainingOrEstadoAndCodigoAuxContaining(String estado, String busqueda, String estado2, String busqueda2);
}
