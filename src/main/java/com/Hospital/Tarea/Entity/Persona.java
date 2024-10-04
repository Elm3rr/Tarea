package com.Hospital.Tarea.Entity;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Persona {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String cui;
    private String email;
    private String telefono; 
    private String estado;
    private boolean eliminado;
    private String imagen;
    private String sexo;
    
    @CreationTimestamp
    private Date fechaCreacion;

    @UpdateTimestamp
    private Date fecha_actualizacion;

}
