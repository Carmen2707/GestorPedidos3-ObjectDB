package com.example.gestorpedidoshibernate;


import com.example.gestorpedidoshibernate.domain.pedido.Pedido;

import com.example.gestorpedidoshibernate.domain.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

public class Session {
   /* @Getter
    @Setter
    //private static Item currentItem;*/

    @Getter
    @Setter
    private static Pedido currentPedido;

  /*  @Getter
    @Setter
  //  private static Producto currentProducto;*/

    @Getter
    @Setter
    private static Usuario currentUser;
}
