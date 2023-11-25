package com.example.gestorpedidos3.domain.usuario;


import com.example.gestorpedidos3.domain.pedido.Pedido;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios")
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
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<>(0);


    /**
     * @return
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", email='" + email + '\'' +
                //   ", pedidosQuantity=" + pedidosQuantity +
                ", pedidos=" + pedidos +
                '}';
    }
}
