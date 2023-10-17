package com.app.colegio.Model;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Asignatura")
public class Asignatura {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Asignatura")
    private Integer id;

    @NotBlank(message = "El campo es obligatorio")
    @NotNull
    private String descripcion;

    @NotNull(message = "El campo es obligatorio")
    @Range(min = 1,max = 7, message = "El valor debe estar entra 1 y 7")
    private Integer horaSem;
}
