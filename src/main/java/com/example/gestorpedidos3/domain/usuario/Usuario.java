package com.example.gestorpedidos3.domain.usuario;


import com.example.gestorpedidos3.domain.pedido.Pedido;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Usuario representa un usuario de la base de datos.
 */
@Data
@Entity
@Table(name = "usuarios")
public class Usuario {
    /**
     * Id del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nombre del usuario.
     */
    @Column(name = "nombre")
    private String nombre;
    /**
     * Contraseña del usuario.
     */
    @Column(name = "contraseña")
    private String contraseña;
    /**
     * Email del usuario.
     */
    @Column(name = "email")
    private String email;

    /**
     * Lista de pedidos que tiene ese usuario.
     */
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER)
    private List<Pedido> pedidos = new ArrayList<>(0);


    /**
     * @return devuelve una cadena con las caracteristicas del usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", email='" + email + '\'' +
                ", pedidos=" + pedidos +
                '}';
    }
}
