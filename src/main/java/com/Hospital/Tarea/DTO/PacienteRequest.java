package com.Hospital.Tarea.DTO;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PacienteRequest extends PersonaRequest{

    @NotNull(message = "No puede estar vacío")
    @Past(message = "La fecha debe ser anterior a hoy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha_nacimiento;
    @NotBlank(message = "No puede estar vacío")
    private String historia_medica;

    private Date fecha_actualizacion;
}
