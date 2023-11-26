package com.example.gestorpedidos3.domain.pedido;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.HibernateUtil;
import com.example.gestorpedidos3.domain.item.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase PedidoDAO implementa la interfaz DAO para realizar varias operaciones.
 */
public class PedidoDAO implements DAO<Pedido> {

    @Override
    public ArrayList<Pedido> getAll() {
        return null;
    }

    /**
     * @param id el ID del pedido a recuperar.
     * @return devuelve el pedido correspondiente al ID proporcionado.
     */
    @Override
    public Pedido get(Long id) {
        var salida = new Pedido();

        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            salida = s.get(Pedido.class, id);
        }
        return salida;
    }

    /**
     * @param data es el pedido que se va a guardar.
     * @return devuelve el pedido guardado.
     */
    @Override
    public Pedido save(Pedido data) {
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
                throw new RuntimeException("Error al guardar el pedido: " + e.getMessage(), e);
            } finally {
                if (session != null) {
                    session.close();
                }

                return data;
            }
        }
    }


    /**
     * @return devuelve el último código de pedido registrado en la base de datos
     */
    public String getUltimoCodigo() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery("select max(p.código) from Pedido p", String.class);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el último código", e);
        }
    }

    /**
     * @param items es la lista donde están todos los items del pedido.
     * @return devuelve el coste total del pedido ya que ha sumado el precio de todos los items.
     */
    public double calcularTotalPedido(List<Item> items) {
        double totalPedido = 0.0;
        for (Item item : items) {
            if (item != null && item.getProducto() != null) {
                String precioConEuro = item.getProducto().getPrecio();
                String precioSinEuro = precioConEuro.replace("€", "");
                double precio = Double.parseDouble(precioSinEuro);
                totalPedido += item.getCantidad() * precio;
            }
        }
        return totalPedido;
    }

    /**
     * Actualiza un pedido de la base de datos.
     *
     * @param data el pedido con los nuevos datos.
     */
    @Override
    public void update(Pedido data) {
        try (org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();

            Pedido p = s.get(Pedido.class, data.getId());
            Pedido.merge(data, p);
            t.commit();

        }
    }

    /**
     * Elimina el pedido seleccionado de la base de datos.
     *
     * @param data el pedido a eliminar.
     */
    @Override
    public void delete(Pedido data) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = null;

            try {
                transaction = session.beginTransaction();
                session.remove(data);
                transaction.commit();
                System.out.println("Pedido eliminado correctamente de la base de datos.");
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
                throw new RuntimeException("Error al borrar el pedido: " + e.getMessage(), e);
            }
        }
    }
}
