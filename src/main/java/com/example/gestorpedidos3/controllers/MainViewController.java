package com.example.gestorpedidoshibernate.controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainViewController {
    @javafx.fxml.FXML
    private TableView tabla;
    @javafx.fxml.FXML
    private TableColumn idColumnID;
    @javafx.fxml.FXML
    private TableColumn idColumnCodigo;
    @javafx.fxml.FXML
    private TableColumn idColumnFecha;
    @javafx.fxml.FXML
    private TableColumn idColumnUsuario;
    @javafx.fxml.FXML
    private TableColumn idColumnTotal;
    @javafx.fxml.FXML
    private Label lblUsuario;
    @javafx.fxml.FXML
    private Button infoUsuario;
    @javafx.fxml.FXML
    private Button logout;

    @javafx.fxml.FXML
    public void click(Event event) {
    }

    @javafx.fxml.FXML
    public void infoUsuario(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
    }
}
