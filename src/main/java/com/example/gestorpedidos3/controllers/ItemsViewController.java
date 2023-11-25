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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * La clase ItemsViewController es el controlador para ver los detalles del pedido.
 */
public class ItemsViewController implements Initializable {
    /**
     * Declaración de elementos para la interfaz.
     */
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
     * @param event Este evento se activa al hacer click sobre una fila de la tabla. Este alert nos muestra la información del producto seleccionado.
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

    /**
     * @param actionEvent El método volver nos lleva a la ventana principal.
     */
    @javafx.fxml.FXML
    public void volver(ActionEvent actionEvent) {
        try {
            App.changeScene("main-view.fxml", "Ventana de pedidos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param actionEvent El método infoPedido se activa al pulsar sobre el botón de información. Esta información ayuda al usuario a entender como utilizar esta ventana.
     */
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

    /**
     * @param actionEvent El método logout nos sirve para cerrar sesión. Nos lleva a la pantalla de login.
     */
    @javafx.fxml.FXML
    public void logout(ActionEvent actionEvent) {
        try {
            App.changeScene("login-view.fxml", "Login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * El método initialize inicia la ventana.
     * @param url URL de la ubicación del archivo FXML.
     * @param resourceBundle  El ResourceBundle que se puede utilizar para localizar objetos de la interfaz de usuario.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //iniciamos los elementos

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