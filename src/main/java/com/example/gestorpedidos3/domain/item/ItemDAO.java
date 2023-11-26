package com.example.gestorpedidos3.domain.item;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;

/**
 * La clase ItemDAO implementa la interfaz DAO para realizar varias operaciones.
 */
public class ItemDAO implements DAO<Item> {

    @Override
    public ArrayList<Item> getAll() {
        return null;
    }

    /**
     * @param id el ID del ítem a recuperar.
     * @return devuelve el ítem correspondiente al ID proporcionado.
     */
    @Override
    public Item get(Long id) {
        var salida = new Item();

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            salida = s.get(Item.class, id);
        }
        return salida;
    }

    /**
     * @param data es el item que se va a guardar.
     * @return devuelve el item guardado.
     */
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

    /**
     * Actualiza un ítem en la base de datos.
     *
     * @param data el ítem con los nuevos datos.
     */
    @Override
    public void update(Item data) {
        try (org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();

            Item i = s.get(Item.class, data.getId());
            Item.merge(data, i);
            t.commit();

        }
    }


    /**
     * Elimina el ítem seleccionado de la base de datos.
     *
     * @param data el ítem a eliminar.
     */
    @Override
    public void delete(Item data) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                session.remove(data);
                transaction.commit();
                System.out.println("Item eliminado correctamente de la base de datos.");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Error al borrar el item: " + e.getMessage(), e);
            }
        }
    }
}
