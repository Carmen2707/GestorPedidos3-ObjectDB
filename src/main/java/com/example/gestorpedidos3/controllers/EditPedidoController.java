package com.example.gestorpedidos3.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class EditPedidoController implements Initializable {
    @javafx.fxml.FXML
    private TextField txtCodigo;
    @javafx.fxml.FXML
    private Label lblCodigo;
    @javafx.fxml.FXML
    private Label lblTotal;
    @javafx.fxml.FXML
    private ComboBox comboProducto;
    @javafx.fxml.FXML
    private Spinner spinnerCantidad;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    private Button btnBorrar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
