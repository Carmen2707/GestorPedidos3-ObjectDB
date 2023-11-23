package com.example.gestorpedidos3.domain.producto;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.HibernateUtil;
import com.example.gestorpedidos3.domain.pedido.Pedido;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class ProductoDAO implements DAO<Producto> {
    @Override
    public ArrayList<Producto> getAll() {
        var salida = new ArrayList<Producto>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Producto> q = s.createQuery("from Producto ", Producto.class);
            salida = (ArrayList<Producto>) q.getResultList();
        }

        return salida;
    }

    @Override
    public Producto get(Long id) {
        return null;
    }

    @Override
    public Producto save(Producto data) {
        return null;
    }



    @Override
    public void update(Producto data) {

    }

    @Override
    public void delete(Producto data) {

    }
}
