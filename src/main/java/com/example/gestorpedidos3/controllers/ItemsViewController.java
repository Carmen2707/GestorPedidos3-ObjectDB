package com.example.gestorpedidos3.controllers;

import com.example.gestorpedidos3.App;
import com.example.gestorpedidos3.Session;
import com.example.gestorpedidos3.domain.item.Item;
import com.example.gestorpedidos3.domain.pedido.PedidoDAO;
import com.example.gestorpedidos3.domain.producto.Producto;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemsViewController implements Initializable {


    @javafx.fxml.FXML
    private TableView<Item> tablaItems;
    @javafx.fxml.FXML
    private TableColumn<Item, String> cIDItem;
    @javafx.fxml.FXML
    private TableColumn<Item, String> cCodigoItem;
    @javafx.fxml.FXML
    private TableColumn<Item, String> cCantidadItem;
    @javafx.fxml.FXML
    private TableColumn<Item, String> cProductoItem;
    @javafx.fxml.FXML
    private Button btnVolver;
    @javafx.fxml.FXML
    private Button infoPedido;
    @javafx.fxml.FXML
    private Button logout;

    /**
     * @param event
     */
    @javafx.fxml.FXML
    public void clickProducto(Event event) {
        Producto productoSeleccionado = tablaItems.getSelectionModel().getSelectedItem().getProducto();
        Session.setCurrentProducto(productoSeleccionado);
        //Creación del alert.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información del producto");
        alert.setHeaderText("Esta es la información de tu producto " + productoSeleccionado.getId());
        alert.setContentText("Nombre del producto: " + productoSeleccionado.getNombre() + "\n"
                + "Precio del producto: " + productoSeleccionado.getPrecio() + "\n"
                + "Cantidad disponible del producto: " + productoSeleccionado.getCantidad_disponible());

        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        try {
            App.changeScene("main-view.fxml", "Ventana de pedidos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void infoPedido(ActionEvent actionEvent) {
        //Creación del alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información de la ventana de los detalles del pedido");
        alert.setHeaderText("¡Hola " + Session.getCurrentUser().getNombre() + "!");
        alert.setContentText("En esta ventana puedes ver los detalles del pedido que has seleccionado. ¡Para ver los productos de tu pedido pulsa sobre él!");
        alert.getDialogPane().setPrefSize(400, 250);
        alert.showAndWait();
    }

    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        try {
            App.changeScene("login-view.fxml", "Login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cIDItem.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getId() + "");
        });
        cCodigoItem.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getCodigo() + "");
        });
        cCantidadItem.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getCantidad() + "");
        });
        cProductoItem.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getProducto() + "");
        });


//llena la tabla de items
        Session.setCurrentPedido((new PedidoDAO()).get(Session.getCurrentPedido().getId()));
        tablaItems.getItems().addAll(Session.getCurrentPedido().getItems());

    }
}