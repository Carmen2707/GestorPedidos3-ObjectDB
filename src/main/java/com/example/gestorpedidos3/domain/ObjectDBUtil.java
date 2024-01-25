package com.example.gestorpedidos3.domain;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
public class ObjectDBUtil {
    @Getter
    private final static EntityManagerFactory entityManagerFactory;

    static{
        entityManagerFactory = Persistence.createEntityManagerFactory("data.odb");
    }
}
