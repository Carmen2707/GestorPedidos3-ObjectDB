package com.example.gestorpedidos3.domain.producto;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * La clase Producto representa los artículos que tienen los items.
 */
@Data
@Entity
@Table(name = "producto")
public class Producto implements Serializable {
    /**
     * Id del producto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nombre del producto.
     */
    @Column(name = "nombre")
    private String nombre;
    /**
     * Precio del producto.
     */
    @Column(name = "precio")
    private String precio;
    /**
     * Cantidad disponible del producto.
     */
    @Column(name = "cantidad_disponible")
    private int cantidad_disponible;

    /**
     * @return devuelve el nombre del articulo con su precio.
     */
    @Override
    public String toString() {
        return " " + nombre +
                ", (" + precio + ")";
    }
}
