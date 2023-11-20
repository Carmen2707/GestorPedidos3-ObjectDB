package com.example.gestorpedidos3.controllers;

import com.example.gestorpedidos3.App;
import com.example.gestorpedidos3.Session;
import com.example.gestorpedidos3.domain.item.Item;
import com.example.gestorpedidos3.domain.pedido.Pedido;
import com.example.gestorpedidos3.domain.pedido.PedidoDAO;
import com.example.gestorpedidos3.domain.usuario.Usuario;
import com.example.gestorpedidos3.domain.usuario.UsuarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {
    @javafx.fxml.FXML
    private TableView<Pedido> tabla;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> idColumnID;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> idColumnCodigo;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> idColumnFecha;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> idColumnUsuario;
    @javafx.fxml.FXML
    private TableColumn<Pedido, String> idColumnTotal;
    @javafx.fxml.FXML
    private Label lblUsuario;
    @javafx.fxml.FXML
    private Button infoUsuario;
    @javafx.fxml.FXML
    private Button logout;
    @javafx.fxml.FXML
    private Button btnAgregar;

    @javafx.fxml.FXML
    public void click(Event event) {
    }

    @javafx.fxml.FXML
    public void infoUsuario(ActionEvent actionEvent) {
        Usuario u = (new UsuarioDAO().get(Session.getCurrentUser().getId()));
        //Creación del alert.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información de la ventana de pedidos");
        alert.setHeaderText("¡Hola " + u.getNombre() + "!" + "\n" + "El correo con el que te registraste es: " + u.getEmail());
        alert.setContentText("En esta ventana puedes ver tus pedidos realizos." + "\n" + "¡Para ver los detalles de tu pedido pulsa sobre él!");
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
        Usuario u = (new UsuarioDAO().get(Session.getCurrentUser().getId()));
        if (u != null) {
            String nombre = u.getNombre();
            lblUsuario.setText("Bienvenid@ " + nombre);
        }

        idColumnID.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getId() + "");
        });
        idColumnCodigo.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getCódigo());
        });
        idColumnFecha.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getFecha());
        });
        idColumnUsuario.setCellValueFactory( (fila)->{
            return new SimpleStringProperty(fila.getValue().getUsuario().getId()+"");
        } );
        idColumnTotal.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getTotal() + "");
        });


        tabla.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Pedido>() {
            @Override
            public void changed(ObservableValue<? extends Pedido> observableValue, Pedido pedido, Pedido t1) {
                if (t1 != null){
                    Pedido pedido1=tabla.getSelectionModel().getSelectedItem();
                    Session.setCurrentPedido(pedido1);
                    try {
                        App.changeScene("items-view.fxml", "Detalles del pedido");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


            }
        });

        //Actualizo el usuario desde la bbdd
        Session.setCurrentUser((new UsuarioDAO()).get(Session.getCurrentUser().getId()));
        tabla.getItems().addAll(Session.getCurrentUser ().getPedidos());


    }

    @javafx.fxml.FXML
    public void agregarPedido(ActionEvent actionEvent) {
        Pedido pedidoNuevo = new Pedido();
        //añadimos la fecha de hoy al pedido
        pedidoNuevo.setFecha(LocalDate.now()+"");


        PedidoDAO pedidoDAO = new PedidoDAO();
        String ultimoCodigo = pedidoDAO.getUltimoCodigo();
        int ultimoNum = Integer.parseInt(ultimoCodigo.substring(4));
        int nuevoNum = ultimoNum + 1;
        String nuevoCodigo = "PED-" + String.format("%03d", nuevoNum);
        //añadimos su codigo de pedido incrementado
        pedidoNuevo.setCódigo(nuevoCodigo);
        //añadimos el usuario que ha creado ese pedido
        pedidoNuevo.setUsuario(Session.getCurrentUser());
        //añadimos el total a la tabla
        Double total = calcularTotalPedido(pedidoNuevo.getItems());
        pedidoNuevo.setTotal(total );
        //+ "€"
        tabla.getItems().add(pedidoNuevo);
        //guardamos el pedido en la base de datos
        Session.setCurrentPedido((new PedidoDAO()).save(pedidoNuevo));
    }


    private double calcularTotalPedido(List<Item> items) {
        double totalPedido = 0.0;
        for (Item item : items) {
            int cantidad = item.getCantidad();
            double precio = Double.parseDouble(item.getProducto().getPrecio());
            totalPedido += cantidad * precio;
        }
        return totalPedido;
    }
}
