package com.example.gestorpedidos3.domain.pedido;


import com.example.gestorpedidos3.domain.item.Item;
import com.example.gestorpedidos3.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "código")
    private String código;
    @Column(name = "fecha")
    private String fecha;
    //@Column(name = "usuario")
    //private int usuario;

    @ManyToOne
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    private Usuario usuario;

    @OneToMany(mappedBy = "codigo", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    @Column(name = "total")
    private Double total;


    /**
     * @return
     */
    @Override
    public String toString() {
        return código;
    }

    public static void merge(Pedido origen, Pedido destino) {

        destino.setId(origen.getId());
        destino.setCódigo(origen.getCódigo());
        destino.setUsuario(origen.getUsuario());
        destino.setFecha(origen.getFecha());
        destino.setTotal(origen.getTotal());

    }
}
