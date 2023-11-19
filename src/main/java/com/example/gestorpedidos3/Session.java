package com.example.gestorpedidos3;


import com.example.gestorpedidos3.domain.item.Item;
import com.example.gestorpedidos3.domain.pedido.Pedido;
import com.example.gestorpedidos3.domain.producto.Producto;
import com.example.gestorpedidos3.domain.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

public class Session {
    @Getter
    @Setter
    private static Item currentItem;

    @Getter
    @Setter
    private static Pedido currentPedido;

    @Getter
    @Setter
    private static Producto currentProducto;

    @Getter
    @Setter
    private static Usuario currentUser;
}
