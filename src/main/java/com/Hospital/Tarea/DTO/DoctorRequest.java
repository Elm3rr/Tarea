package com.Hospital.Tarea.DTO;

import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRequest extends PersonaRequest {
    @NotBlank(message = "No puede estar vacío")
    private String colegiado;
    
    @NotBlank(message = "No puede estar vacío")
    private String especialidad;

    @NotBlank(message = "No puede estar vacío")
    private String descripcion; 
    
    private Date fecha_actualizacion;
}
