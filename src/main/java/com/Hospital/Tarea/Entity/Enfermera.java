package com.Hospital.Tarea.Entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Enfermera extends Persona {
    private String codigoAux;
    private String servicio;
    private String descripcion;
}
