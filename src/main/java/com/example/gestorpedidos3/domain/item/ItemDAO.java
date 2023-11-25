package com.example.gestorpedidos3.domain.item;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class ItemDAO implements DAO<Item> {
    /**
     * @return
     */
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            try {
                session.save(data);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Error al guardar el item: " + e.getMessage(), e);
            } finally {
                if (session != null) {
                    session.close();
                }

                return data;
            }
        }
    }

    @Override
    public void update(Item data) {
        try (org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();

            Item i = s.get(Item.class, data.getId());
            Item.merge(data, i);
            t.commit();

        }
    }


    @Override
    public void delete(Item data) {

    }
}
