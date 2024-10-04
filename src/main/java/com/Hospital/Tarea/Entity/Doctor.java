package com.Hospital.Tarea.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter 
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Doctor extends Persona{
    private String colegiado;
    private String especialidad;
    private String descripcion;

    @OneToMany(mappedBy = "doctor")
    private List<Paciente> pacientes;
}
