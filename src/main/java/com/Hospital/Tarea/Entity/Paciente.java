package com.Hospital.Tarea.Entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Paciente extends Persona{
    private Date fecha_nacimiento;
    private String historia_medica;

    @ManyToOne
    private Doctor doctor;
}
