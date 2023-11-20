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

   @OneToMany(mappedBy = "codigo", fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>();

    @Column(name = "total")
    private Double total;


    public Pedido() {
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", código='" + código + '\'' +
                ", fecha=" + fecha +
            //   ", usuario=" + usuario +
                ", total=" + total +
                '}';
    }
}
