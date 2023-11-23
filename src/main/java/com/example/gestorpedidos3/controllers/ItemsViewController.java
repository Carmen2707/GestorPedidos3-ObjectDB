package com.example.gestorpedidos3.controllers;

import com.example.gestorpedidos3.App;
import com.example.gestorpedidos3.Session;
import com.example.gestorpedidos3.domain.item.Item;
import com.example.gestorpedidos3.domain.pedido.PedidoDAO;
import com.example.gestorpedidos3.domain.usuario.Usuario;
import com.example.gestorpedidos3.domain.usuario.UsuarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

    @javafx.fxml.FXML
    public void clickProducto(Event event) {
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
        Usuario u = (new UsuarioDAO().get(Session.getCurrentUser().getId()));
        //Creación del alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información de la ventana de los detalles del pedido");
        alert.setHeaderText("¡Hola " + u.getNombre() + "!");
        alert.setContentText("En esta ventana puedes ver los detalles del pedido que has seleccionado. ¡Para ver los productos de tu pedido pulsa sobre él!");
        alert.getDialogPane().setPrefSize(400, 200);
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




        //Actualizo el usuario desde la bbdd
        //Session.setCurrentPedido((new PedidoDAO()).get(Session.getCurrentPedido().getId()));
        tablaItems.getItems().addAll(Session.getCurrentPedido().getItems());
        System.out.println(Session.getCurrentPedido().getItems());
    }
}