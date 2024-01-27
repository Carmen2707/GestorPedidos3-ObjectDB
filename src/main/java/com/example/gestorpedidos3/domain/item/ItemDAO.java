package com.example.gestorpedidos3.domain.item;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.ObjectDBUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase ItemDAO implementa la interfaz DAO para realizar varias operaciones.
 */
public class ItemDAO implements DAO<Item> {

    @Override
    public ArrayList<Item> getAll() {
        var salida = new ArrayList<Item>(0);
        EntityManager entityManager = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Item> query = entityManager.createQuery("select i from Item i", Item.class);
            salida = (ArrayList<Item>) query.getResultList();
        } finally {
            entityManager.close();
        }
        return salida;
    }

    /**
     * @param id el ID del ítem a recuperar.
     * @return devuelve el ítem correspondiente al ID proporcionado.
     */
    @Override
    public Item get(Long id) {
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        Item i = null;
        try {
            i = em.find(Item.class, id);
        } finally {
            em.close();
        }
        return i;
    }

    /**
     * @param data es el item que se va a guardar.
     * @return devuelve el item guardado.
     */
    @Override
    public Item save(Item data) {
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(data);
            em.flush();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return data;
    }

    /**
     * Actualiza un ítem en la base de datos.
     *
     * @param data el ítem con los nuevos datos.
     * @return
     */
    @Override
    public Item update(Item data) {
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(data);
            em.getTransaction().commit();

        } catch (Exception ex) {

            System.out.println(ex.getMessage());
        } finally {
            em.close();
        }
        return data;
    }


    /**
     * Elimina el ítem seleccionado de la base de datos.
     *
     * @param data el ítem a eliminar.
     * @return
     */
    @Override
    public Boolean delete(Item data) {
        Boolean salida = false;
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Item i = em.find(Item.class, data.getId());
            salida = (i != null);
            if (salida) {
                em.remove(i);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return salida;
    }
}
