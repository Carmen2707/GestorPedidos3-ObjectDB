package com.example.gestorpedidos3.controllers;

import com.example.gestorpedidos3.App;
import com.example.gestorpedidos3.Session;
import com.example.gestorpedidos3.domain.item.Item;
import com.example.gestorpedidos3.domain.item.ItemDAO;
import com.example.gestorpedidos3.domain.pedido.Pedido;
import com.example.gestorpedidos3.domain.pedido.PedidoDAO;
import com.example.gestorpedidos3.domain.producto.Producto;
import com.example.gestorpedidos3.domain.producto.ProductoDAO;
import com.example.gestorpedidos3.domain.usuario.UsuarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPedidoController implements Initializable {
    @javafx.fxml.FXML
    private ComboBox<Producto> comboProducto;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerCantidad;
    @javafx.fxml.FXML
    private Button btnAñadir;
    @javafx.fxml.FXML
    private Button btnBorrar;
    @javafx.fxml.FXML
    private Button btnGuardar;
    @javafx.fxml.FXML
    private Label lblTituloPedidoNum;
    @javafx.fxml.FXML
    private TableView<Item> tablaEditItem;
    @javafx.fxml.FXML
    private Button btnCancelar;
    @javafx.fxml.FXML
    private TableColumn<Item,String> cProducto;
    @javafx.fxml.FXML
    private TableColumn<Item,String> cCantidad;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTituloPedidoNum.setText(Session.getCurrentPedido().getCódigo());


        tablaEditItem.getItems().addAll(Session.getCurrentPedido().getItems());
        cCantidad.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getCantidad() + "");
        });
        cProducto.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getProducto() + "");
        });


        spinnerCantidad.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1,100, 0,1));
        ProductoDAO productoDAO= new ProductoDAO();
        comboProducto.getItems().addAll( productoDAO.getAll() );


    }

    @Deprecated
    public void eliminarPedido(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void guardarPedido(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void añadirItem(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void borrarItem(ActionEvent actionEvent) {
        // Obtener el pedido seleccionado
        Item itemSeleccionado = tablaEditItem.getSelectionModel().getSelectedItem();

        if (itemSeleccionado != null) {
            // Eliminar de la tabla
            tablaEditItem.getItems().remove(itemSeleccionado);

            // Eliminar de la base de datos si es necesario
            ItemDAO itemDAO = new ItemDAO();
            itemDAO.delete(itemSeleccionado);
        } else {
            // Manejar si no se ha seleccionado ningún pedido para borrar
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No se ha seleccionado ningun item.");
            alert.setContentText("Selecciona sobre un item para borrarlo.");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        try {
            App.changeScene("main-view.fxml", "Ventana de pedidos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
