package com.example.gestorpedidos3.domain.pedido;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.HibernateUtil;
import com.example.gestorpedidos3.domain.item.Item;
import com.example.gestorpedidos3.domain.usuario.Usuario;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class PedidoDAO implements DAO<Pedido> {
    @Override
    public ArrayList<Pedido> getAll() {
        var salida = new ArrayList<Pedido>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Pedido> q = s.createQuery("from Pedido", Pedido.class);
            salida = (ArrayList<Pedido>) q.getResultList();
        }

        return salida;
    }

    @Override
    public Pedido get(Long id) {
        var salida = new Pedido();

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            salida = s.get(Pedido.class, id);
        }
        return salida;
    }

    @Override
    public Pedido save(Pedido data) {
        return null;
    }

    @Override
    public void update(Pedido data) {

    }

    @Override
    public void delete(Pedido data) {

    }
}
