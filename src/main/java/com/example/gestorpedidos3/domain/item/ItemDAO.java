package com.example.gestorpedidos3.domain.item;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.HibernateUtil;
import com.example.gestorpedidos3.domain.pedido.Pedido;
import com.example.gestorpedidos3.domain.usuario.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class ItemDAO implements DAO<Item> {
    @Override
    public ArrayList<Item> getAll() {
        var salida = new ArrayList<Item>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Item> q = s.createQuery("from Item", Item.class);
            salida = (ArrayList<Item>) q.getResultList();
        }

        return salida;
    }

    @Override
    public Item get(Long id) {
        var salida = new Item();

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            salida = s.get(Item.class, id);
        }
        return salida;
    }

    @Override
    public Item save(Item data) {
        return null;
    }



    @Override
    public void update(Item data) {

    }

    @Override
    public void delete(Item data) {

    }
}
