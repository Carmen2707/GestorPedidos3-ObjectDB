package com.example.gestorpedidos3.domain.producto;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "precio")
    private String precio;
    @Column(name = "cantidad_disponible")
    private int cantidad_disponible;
}
