package com.example.gestorpedidos3.domain.producto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.io.Serializable;

/**
 * La clase Producto representa los artículos que tienen los items.
 */
@Data
@Entity

public class Producto implements Serializable {
    public Producto(String nombre, String precio, int cantidad_disponible) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad_disponible = cantidad_disponible;
    }

    /**
     * Id del producto.
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * Nombre del producto.
     */

    private String nombre;
    /**
     * Precio del producto.
     */

    private String precio;
    /**
     * Cantidad disponible del producto.
     */

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
