package com.example.gestorpedidos3.domain.usuario;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.ObjectDBUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase UsuarioDAO implementa la interfaz DAO para realizar varias operaciones.
 */
public class UsuarioDAO implements DAO<Usuario> {
    /**
     * Valida el usuario utilizando el nombre de usuario y la contraseña proporcionados.
     *
     * @param nombre     el nombre de usuario
     * @param contraseña la contraseña del usuario
     * @return devuelve el usuario validado o null si la validación falla
     */
    public Usuario validateUser(String nombre, String contraseña) {
        Usuario result = null;
        List<Usuario> lista = new ArrayList<>();
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {

            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.nombre = :u AND u.contraseña = :p", Usuario.class);
            query.setParameter("u", nombre);
            query.setParameter("p", contraseña);
            lista = query.getResultList();
            try {
                result = lista.get(0);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    @Override
    public List<Usuario> getAll() {
        List<Usuario> salida;

        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            TypedQuery<Usuario> query = em.createQuery("select u from Usuario u", Usuario.class);
            salida = query.getResultList();
        } finally {
            em.close();
        }
        return salida;
    }

    /**
     * Obtiene un usuario segun su id.
     *
     * @param id es el id del usuario que se quiere obtener.
     * @return devuelve el usuario con el id especificado.
     */
    @Override
    public Usuario get(Long id) {
        Usuario salida = null;
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            salida = em.find(Usuario.class, id);
        } finally {
            em.close();
        }
        return salida;
    }


    @Override
    public Usuario save(Usuario data) {
        return null;
    }

    @Override
    public Usuario update(Usuario data) {
        return null;
    }


    public void saveAll(List<Usuario> usuarios) {
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            for (Usuario u : usuarios) {
                em.persist(u);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }


    @Override
    public Boolean delete(Usuario data) {

        return null;
    }
}
