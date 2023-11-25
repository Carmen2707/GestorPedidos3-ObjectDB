package com.example.gestorpedidos3.domain.usuario;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class UsuarioDAO implements DAO<Usuario> {
    /**
     * @param nombre
     * @param contraseña
     * @return
     */
    public Usuario validateUser(String nombre, String contraseña) {
        Usuario result = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> query = session.createQuery("from Usuario where nombre=:u and contraseña=:p", Usuario.class);
            query.setParameter("u", nombre);
            query.setParameter("p", contraseña);

            try {
                result = query.getSingleResult();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        return result;
    }

    @Override
    public ArrayList<Usuario> getAll() {
        var salida = new ArrayList<Usuario>(0);

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            Query<Usuario> q = s.createQuery("from Usuario", Usuario.class);
            salida = (ArrayList<Usuario>) q.getResultList();
        }

        return salida;
    }

    @Override
    public Usuario get(Long id) {
        var salida = new Usuario();

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            salida = s.get(Usuario.class, id);
        }
        return salida;
    }


    @Override
    public Usuario save(Usuario data) {
        return null;
    }


    @Override
    public void update(Usuario data) {

    }

    @Override
    public void delete(Usuario data) {

    }
}
