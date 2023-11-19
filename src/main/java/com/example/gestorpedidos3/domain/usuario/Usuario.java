package com.example.gestorpedidoshibernate.domain.usuario;

import com.example.gestorpedidoshibernate.domain.pedido.Pedido;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "contraseña")
    private String contraseña;
    @Column(name = "email")
    private String email;
    @Transient
    private Long pedidosQuantity;
    @OneToMany(mappedBy = "usuarios", fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<>(0);
}
