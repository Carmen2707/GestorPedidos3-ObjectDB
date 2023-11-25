package com.example.gestorpedidos3.domain.item;


import com.example.gestorpedidos3.domain.pedido.Pedido;
import com.example.gestorpedidos3.domain.producto.Producto;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

/**
 * La clase Item representa un elemento de un pedido que contiene información sobre un producto, la cantidad y el pedido al que pertenece.
 */
@Data
@Entity
@Table(name = "items")
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Pedido al que pertenece este item.
     */
    @ManyToOne
    @JoinColumn(name = "código_pedido", referencedColumnName = "código", nullable = false)
    private Pedido codigo;

    /**
     * Cantidad de items en el pedido.
     */
    @Column(name = "cantidad")
    private int cantidad;

    /**
     * Producto del item.
     */
    @OneToOne
    @JoinColumn(name = "product_id")
    private Producto producto;

    /**
     * @return Devuelve una cadena del item
     */
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", codigo=" + codigo.getCódigo() +
                ", cantidad=" + cantidad +
                ", producto=" + producto +
                '}';
    }

    /**
     * Actualiza los datos.
     * @param origen el ítem de origen
     * @param destino el ítem de destino
     */
    public static void merge(Item origen, Item destino) {

        destino.setId(origen.getId());
        destino.setCodigo(origen.getCodigo());
        destino.setProducto(origen.getProducto());
        destino.setCantidad(origen.getCantidad());

    }
}
