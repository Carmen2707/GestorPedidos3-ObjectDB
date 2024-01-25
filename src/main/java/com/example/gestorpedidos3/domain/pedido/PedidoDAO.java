package com.example.gestorpedidos3.domain.pedido;


import com.example.gestorpedidos3.domain.DAO;
import com.example.gestorpedidos3.domain.ObjectDBUtil;
import com.example.gestorpedidos3.domain.item.Item;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        Pedido p=null;
        try{
            p = em.find(Pedido.class,id);
        } finally {
            em.close();
        }
        return p;
    }

    /**
     * @param data es el pedido que se va a guardar.
     * @return devuelve el pedido guardado.
     */
    @Override
    public Pedido save(Pedido data) {
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try{
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
     * @return devuelve el último código de pedido registrado en la base de datos
     */
    public String getUltimoCodigo() {
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();

            TypedQuery<String> query = em.createQuery("select max(p.código) from Pedido p", String.class);
            return query.getSingleResult();
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
     * @return
     */
    @Override
    public Pedido update(Pedido data) {
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            data = em.merge(data);
            em.getTransaction().commit();

        }catch (Exception ex){

            System.out.println(ex.getMessage());
        } finally {
            em.close();
        }
        return data;
    }

    /**
     * Elimina el pedido seleccionado de la base de datos.
     *
     * @param data el pedido a eliminar.
     * @return
     */
    @Override
    public Boolean delete(Pedido data) {
        Boolean salida= false;
        EntityManager em = ObjectDBUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Pedido p = em.find(Pedido.class,data.getId());
            salida = (p!=null);
            if(salida) {
                em.remove(p);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return salida;
    }
}
