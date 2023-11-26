package com.example.gestorpedidos3;


import com.example.gestorpedidos3.domain.item.Item;
import com.example.gestorpedidos3.domain.pedido.Pedido;
import com.example.gestorpedidos3.domain.producto.Producto;
import com.example.gestorpedidos3.domain.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

/**
 * La clase Session es una clase utilitaria que gestiona los objetos actuales en la sesión de la aplicación.
 * Proporciona métodos estáticos para acceder y establecer objetos específicos durante la ejecución de la aplicación.
 */
public class Session {
    /**
     * El item actual en la sesión.
     */
    @Getter
    @Setter
    private static Item currentItem;

    /**
     * El pedido actual en la sesión.
     */
    @Getter
    @Setter
    private static Pedido currentPedido;

    /**
     * El producto actual en la sesión.
     */
    @Getter
    @Setter
    private static Producto currentProducto;

    /**
     * El usuario actual en la sesión.
     */
    @Getter
    @Setter
    private static Usuario currentUser;
}
