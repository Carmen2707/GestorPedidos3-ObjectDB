package com.example.gestorpedidos3.domain.producto;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.ObjectDBUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * La clase ProductoDAO implementa la interfaz DAO para realizar varias operaciones.
 */
public class ProductoDAO implements DAO<Producto> {
    /**
     * @return Devuelve una lista de todos los productos almacenados en la base de datos.
     */
    @Override
    public List<Producto> getAll() {
        List<Producto> salida;

        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Producto> query = em.createQuery("select p from Producto p", Producto.class);
            salida = query.getResultList();
        } finally {
            em.close();
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
    public Producto update(Producto data) {
        return null;
    }


    @Override
    public Boolean delete(Producto data) {

        return null;
    }

    public void saveAll(List<Producto> productos) {
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            for (Producto p : productos) {
                em.persist(p);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
