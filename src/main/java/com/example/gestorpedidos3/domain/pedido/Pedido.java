package com.example.gestorpedidos3.domain.pedido;


import com.example.gestorpedidos3.domain.item.Item;
import com.example.gestorpedidos3.domain.usuario.Usuario;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase Pedido representa los pedidos que ha realizado el usuario.Cada pedido tiene un id, un código, la fecha en la que se realizo,
 * que usuario lo hizo y el precio total del pedido.
 */
@Data
@Entity

public class Pedido implements Serializable {
    /**
     * Id del pedido.
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * Código del pedido.
     */

    private String código;
    /**
     * Fecha en la que se realizo el pedido.
     */

    private String fecha;

    /**
     * Usuario que realizo el pedido.
     */
    @ManyToOne
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    private Usuario usuario;

    /**
     * Lista de items que contiene el pedido.
     */
    @OneToMany(mappedBy = "codigo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    /**
     * Precio total del pedido.
     */

    private Double total;


    /**
     * @return Devuelve el código del pedido.
     */
    @Override
    public String toString() {
        return código;
    }

    /**
     * Actualiza los datos.
     *
     * @param origen  el pedido de origen.
     * @param destino el pedido de destino.
     */
    public static void merge(Pedido origen, Pedido destino) {

        destino.setId(origen.getId());
        destino.setCódigo(origen.getCódigo());
        destino.setUsuario(origen.getUsuario());
        destino.setFecha(origen.getFecha());
        destino.setTotal(origen.getTotal());

    }
}
