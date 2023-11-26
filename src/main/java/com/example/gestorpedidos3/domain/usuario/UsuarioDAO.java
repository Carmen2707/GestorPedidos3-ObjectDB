package com.example.gestorpedidos3.domain.usuario;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;

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
        return null;
    }

    /**
     * Obtiene un usuario segun su id.
     *
     * @param id es el id del usuario que se quiere obtener.
     * @return devuelve el usuario con el id especificado.
     */
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
