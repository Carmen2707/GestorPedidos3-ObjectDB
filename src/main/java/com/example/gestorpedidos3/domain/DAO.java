package com.example.gestorpedidos3.domain;

import java.util.List;

/**
 * La interfaz DAO define las operaciones básicas para acceder y manipular entidades en la base de datos.
 *
 * @param <T> el tipo de entidad con el que opera la interfaz
 */
public interface DAO<T> {
    /**
     * @return devuelve una lista que contiene todos los elementos del tipo especificado en la base de datos.
     */
    List<T> getAll();

    /**
     * @param id el ID del elemento que se quiere obtener.
     * @return devuelve el elemento del tipo especificado con el ID especificado.
     */
    T get(Long id);

    /**
     * Guarda un elemento del tipo especificado en la base de datos.
     *
     * @param data es el elemento que se quiere guardar.
     * @return devuelve el elemento guardado.
     */
    T save(T data);

    /**
     * Actualiza un elemento del tipo especificado en la base de datos.
     *
     * @param data es el elemento que se quiere actualizar
     * @return
     */


    T update(T data);


    /**
     * Elimina un elemento del tipo especificado de la base de datos.
     *
     * @param data es el elemento que se quiere eliminar
     * @return
     */
    Boolean delete(T data);

}
