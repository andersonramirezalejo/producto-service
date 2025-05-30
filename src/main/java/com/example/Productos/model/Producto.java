package com.example.Productos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del producto", example = "101")
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre del producto", example = "Laptop Lenovo LOQ")
    private String nombre;

    @Schema(description = "Precio del producto", example = "5000000.00")
    @Column(nullable = false)
    private Double precio;

    @Schema(description = "Descripción del producto", example = "Memoria 24 Gigas")
    private String descripcion;
}