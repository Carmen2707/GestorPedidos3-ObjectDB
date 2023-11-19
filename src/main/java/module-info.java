module com.example.gestorpedidos3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires lombok;

    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires java.sql;

    opens com.example.gestorpedidos3 to javafx.fxml;
    exports com.example.gestorpedidos3;
    exports com.example.gestorpedidos3.controllers;

       opens com.example.gestorpedidos3.domain.item;
    opens com.example.gestorpedidos3.domain.pedido;
   opens com.example.gestorpedidos3.domain.producto;
    opens com.example.gestorpedidos3.domain.usuario;

    opens com.example.gestorpedidos3.controllers to javafx.fxml;

}