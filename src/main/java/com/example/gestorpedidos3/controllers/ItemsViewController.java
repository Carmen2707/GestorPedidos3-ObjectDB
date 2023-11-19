package com.example.gestorpedidoshibernate.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ItemsViewController {


    @javafx.fxml.FXML
    private TableView tablaItems;
    @javafx.fxml.FXML
    private TableColumn cIDItem;
    @javafx.fxml.FXML
    private TableColumn cCodigoItem;
    @javafx.fxml.FXML
    private TableColumn cCantidadItem;
    @javafx.fxml.FXML
    private TableColumn cProductoItem;
    @javafx.fxml.FXML
    private Button btnVolver;
    @javafx.fxml.FXML
    private Button infoPedido;
    @javafx.fxml.FXML
    private Button logout;

    @javafx.fxml.FXML
    public void clickProducto(Event event) {
    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void infoPedido(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
    }
}