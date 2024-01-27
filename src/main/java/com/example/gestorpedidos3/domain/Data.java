package com.example.gestorpedidos3.domain;

import com.example.gestorpedidos3.domain.producto.Producto;
import com.example.gestorpedidos3.domain.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public static List<Usuario> generarUsuarios() {
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(new Usuario("Julia", "12345", "julia@gmail.com", new ArrayList<>()));
        listaUsuarios.add(new Usuario("Lucas", "12345", "lucas@gmail.com", new ArrayList<>()));
        return listaUsuarios;
    }

    public static List<Producto> generarProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        listaProductos.add(new Producto("Smartphone", "299€", 50));
        listaProductos.add(new Producto("Portátil", "799€", 30));
        listaProductos.add(new Producto("Auriculares Inalámbricos", "79€", 100));
        listaProductos.add(new Producto("Televisor LED", "599€", 20));
        listaProductos.add(new Producto("Tableta", "199€", 40));
        listaProductos.add(new Producto("Altavoz", "19€", 50));

        return listaProductos;

    }

}
