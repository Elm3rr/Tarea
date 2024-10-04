package com.Hospital.Tarea.DTO;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaRequest {
    private Long id;
    @NotBlank(message = "No puede estar vacío")
    private String nombre;
    @NotBlank(message = "No puede estar vacío")
    private String apellido;
    @NotBlank(message = "No puede estar vacío")
    @Size(min=13, max=13, message = "El CUI debe tener exactamente 13 dígitos")
    private String cui;
    @NotBlank(message = "No puede estar vacío")
    @Email(message = "Email no válido")
    private String email;
    @NotBlank(message = "No puede estar vacío")
    private String telefono; 
    @NotBlank(message = "No puede estar vacío")
    private String estado;
    @NotBlank(message = "No puede estar vacío")
    private String sexo;
    
    private MultipartFile imagen;
}
