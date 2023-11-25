package com.example.gestorpedidos3.domain.item;


import com.example.gestorpedidos3.domain.pedido.Pedido;
import com.example.gestorpedidos3.domain.producto.Producto;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "items")
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "código_pedido")
    //  private String código_pedido;
    @ManyToOne
    @JoinColumn(name = "código_pedido", referencedColumnName = "código", nullable = false)
    private Pedido codigo;

    @Column(name = "cantidad")
    private int cantidad;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Producto producto;

    /**
     * @return
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

    public static void merge(Item origen, Item destino) {

        destino.setId(origen.getId());
        destino.setCodigo(origen.getCodigo());
        destino.setProducto(origen.getProducto());
        destino.setCantidad(origen.getCantidad());

    }
}
