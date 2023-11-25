package com.example.gestorpedidos3.domain;

import java.util.ArrayList;

public interface DAO<T> {
    /**
     * @return
     */
    public ArrayList<T> getAll();

    public T get(Long id);

    public T save(T data);

    public void update(T data);

    public void delete(T data);

}
