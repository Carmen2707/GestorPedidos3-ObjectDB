package com.example.gestorpedidos3.domain.pedido;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.HibernateUtil;

import org.hibernate.Session;
import org.hibernate.Transaction;

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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                session.save(data);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Error al guardar el pedido: " + e.getMessage(), e);
            }

            return data;
        }
       }



public String getUltimoCodigo(){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery("select max(p.código) from Pedido p", String.class);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el último código", e);
        }
    }
    @Override
    public void update(Pedido data) {

    }

    @Override
    public void delete(Pedido data) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                session.remove(data);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Error al borrar el pedido: " + e.getMessage(), e);
            }
        }
    }

    public Long getUltimoID() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Long> query = session.createQuery("select max(p.id) from Pedido p", Long.class);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el último id", e);
        }
    }
}
