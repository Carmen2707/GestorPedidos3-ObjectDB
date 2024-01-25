package com.example.gestorpedidos3.domain.usuario;


import com.example.gestorpedidos3.domain.pedido.Pedido;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase Usuario representa un usuario de la base de datos.
 */
@Data
@Entity
public class Usuario {

    public Usuario(String nombre, String contraseña, String email, List<Pedido> pedidos) {
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
        this.pedidos = pedidos;
    }

    /**
     * Id del usuario.
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * Nombre del usuario.
     */

    private String nombre;
    /**
     * Contraseña del usuario.
     */

    private String contraseña;
    /**
     * Email del usuario.
     */

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
