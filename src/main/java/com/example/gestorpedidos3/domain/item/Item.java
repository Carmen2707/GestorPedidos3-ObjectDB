package com.example.gestorpedidoshibernate.domain.item;

import com.example.gestorpedidoshibernate.domain.pedido.Pedido;
import com.example.gestorpedidoshibernate.domain.producto.Producto;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name="items")
public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//@Column(name = "código_pedido")
  //  private String código_pedido;
    @ManyToOne
    @JoinColumn(name = "código_pedido", referencedColumnName = "código")
private Pedido código;

    @Column(name = "cantidad")
    private int cantidad;

@OneToOne
@JoinColumn(name = "product_id")
    private Producto product_id;

}
