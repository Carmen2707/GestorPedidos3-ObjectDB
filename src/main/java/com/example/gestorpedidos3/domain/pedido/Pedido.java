package com.example.gestorpedidoshibernate.domain.pedido;


import com.example.gestorpedidoshibernate.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="pedido")
public class Pedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "código")
    private String código;
    @Column(name = "fecha")
    private Date fecha;
    //@Column(name = "usuario")
    //private int usuario;

    @ManyToOne
    @JoinColumn(name = "usuario", referencedColumnName = "id")
    private Usuario usuario;

 /*   @OneToMany(mappedBy = "código", fetch = FetchType.EAGER)
    private List<Item> items = new ArrayList<>(0);*/
    @Column(name = "total")
    private int total;
}
