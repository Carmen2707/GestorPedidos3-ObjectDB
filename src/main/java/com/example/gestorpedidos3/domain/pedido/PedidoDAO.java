package com.example.gestorpedidos3.domain.pedido;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.HibernateUtil;
import com.example.gestorpedidos3.domain.item.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements DAO<Pedido> {
    /**
     * @return
     */
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


    public String getUltimoCodigo() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery("select max(p.código) from Pedido p", String.class);
            return query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener el último código", e);
        }
    }

    public double calcularTotalPedido(List<Item> items) {
        double totalPedido = 0.0;
        for (Item item : items) {
            String precioConEuro = item.getProducto().getPrecio();
            String precioSinEuro = precioConEuro.replace("€", "").trim();
            double precio = Double.parseDouble(precioSinEuro);
            totalPedido += item.getCantidad() * precio;
        }
        return totalPedido;
    }

    @Override
    public void update(Pedido data) {
        try (org.hibernate.Session s = HibernateUtil.getSessionFactory().openSession()) {
            Transaction t = s.beginTransaction();

            Pedido p = s.get(Pedido.class, data.getId());
            Pedido.merge(data, p);
            t.commit();

        }
    }

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
