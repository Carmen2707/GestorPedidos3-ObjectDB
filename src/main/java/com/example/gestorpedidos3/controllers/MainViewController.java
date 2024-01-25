package com.example.gestorpedidos3.controllers;

import com.example.gestorpedidos3.App;
import com.example.gestorpedidos3.Session;
import com.example.gestorpedidos3.domain.item.ItemDAO;
import com.example.gestorpedidos3.domain.pedido.Pedido;
import com.example.gestorpedidos3.domain.pedido.PedidoDAO;
import com.example.gestorpedidos3.domain.usuario.UsuarioDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * La clase MainViewController es el controlador de la ventana principal.
 */
public class MainViewController implements Initializable {

    /**
     * Declaración de los elementos.
     */
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
    private Button btnB;
    @javafx.fxml.FXML
    private Button btnVer;
    @javafx.fxml.FXML
    private Button btnEditar;

    /**
     * @param actionEvent El método infoUsuario se activa al pulsar sobre el botón de información. Esta información ayuda al usuario a entender como utilizar esta ventana.
     */
    @javafx.fxml.FXML
    public void infoUsuario(ActionEvent actionEvent) {
        //Creación del alert.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información de la ventana de pedidos");
        alert.setHeaderText("¡Hola " + Session.getCurrentUser().getNombre() + "!" + "\n" + "El correo con el que te registraste es: " + Session.getCurrentUser().getEmail());
        alert.setContentText("En esta ventana puedes ver tus pedidos realizados." + "\n" + "¡Haz click sobre un pedido y cuando este en azul pulsa sobre el botón de la acción que quieras realizar!");
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
     *
     * @param url            URL de la ubicación del archivo FXML.
     * @param resourceBundle El ResourceBundle que se puede utilizar para localizar objetos de la interfaz de usuario.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //iniciamos los elementos.
        Session.setCurrentUser((new UsuarioDAO()).get(Session.getCurrentUser().getId()));
        lblUsuario.setText("Bienvenid@ " + Session.getCurrentUser().getNombre());

        idColumnID.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getId() + "");
        });
        idColumnCodigo.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getCódigo());
        });
        idColumnFecha.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getFecha());
        });
        idColumnUsuario.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getUsuario().getNombre());
        });
        idColumnTotal.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getTotal() + "€");
        });

        //llenamos la tabla
        tabla.getItems().addAll(Session.getCurrentUser().getPedidos());
    }

    /**
     * @param actionEvent El método agregarPedido nos agrega a la tabla un pedido automáticamente.
     */
    @javafx.fxml.FXML
    public void agregarPedido(ActionEvent actionEvent) {
        Pedido pedidoNuevo = new Pedido();
        //añadimos la fecha de hoy al pedido
        pedidoNuevo.setFecha(LocalDate.now() + "");

        PedidoDAO pedidoDAO = new PedidoDAO();
        String ultimoCodigo = pedidoDAO.getUltimoCodigo();
        if (ultimoCodigo != null){
            int ultimoNum = Integer.parseInt(ultimoCodigo.substring(4));
            int nuevoNum = ultimoNum + 1;
            String nuevoCodigo = "PED-" + String.format("%03d", nuevoNum);
            //añadimos su codigo de pedido incrementado
            pedidoNuevo.setCódigo(nuevoCodigo);
        }else{
            pedidoNuevo.setCódigo("PED-001");
            System.out.println("codigo null");
        }



        //añadimos el usuario que ha creado ese pedido
        pedidoNuevo.setUsuario(Session.getCurrentUser());

        //añadimos el total a la tabla
        Double total = pedidoDAO.calcularTotalPedido(pedidoNuevo.getItems());
        pedidoNuevo.setTotal(total);

        //añadimos el pedido a la tabla
        tabla.getItems().add(pedidoNuevo);
        //guardamos el pedido en la base de datos
        Session.setCurrentPedido((new PedidoDAO()).save(pedidoNuevo));
    }


    /**
     * @param actionEvent El método borrarPedido borra el pedido que hayamos seleccionado.
     */
    @javafx.fxml.FXML
    public void borrarPedido(ActionEvent actionEvent) {
        // Obtener el pedido seleccionado
        Pedido pedidoSeleccionado = tabla.getSelectionModel().getSelectedItem();
        Session.setCurrentPedido(pedidoSeleccionado);
        if (pedidoSeleccionado != null) {
            PedidoDAO pedidoDAO = new PedidoDAO();
            //creamos el alert de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("¿Seguro que quieres borrar el pedido " + Session.getCurrentPedido().getCódigo() + " ?");
            var result = alert.showAndWait().get();

            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                // Eliminar de la tabla
                tabla.getItems().remove(pedidoSeleccionado);

                // Eliminar de la base de datos
                pedidoDAO.delete(pedidoSeleccionado);
                //creamos el alert para confirmar que se ha borrado
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setContentText("Pedido borrado correctamente");
                alert3.show();

            }
        } else {
            // Alert si no se ha seleccionado ningún pedido
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error");
            alert2.setHeaderText("No se ha seleccionado ningun pedido.");
            alert2.setContentText("Selecciona sobre un pedido para borrarlo.");
            alert2.showAndWait();
        }
    }

    /**
     * @param actionEvent El método verDetalles nos lleva a la ventana de los detalles del pedido que hemos seleccionado.
     */
    @javafx.fxml.FXML
    public void verDetalles(ActionEvent actionEvent) {
        Pedido pedidoSeleccionado = tabla.getSelectionModel().getSelectedItem();
        Session.setCurrentPedido(pedidoSeleccionado);

        if (pedidoSeleccionado != null) {
            try {
                App.changeScene("items-view.fxml", "Detalles del pedido");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ItemDAO itemDAO = new ItemDAO();
            System.out.println(itemDAO.getAll());
        } else {
            // Alert si no se ha seleccionado ningún pedido
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error");
            alert2.setHeaderText("No se ha seleccionado ningun pedido.");
            alert2.setContentText("Selecciona sobre un pedido para ver sus detalles.");
            alert2.showAndWait();
        }

    }

    /**
     * @param actionEvent El método editarPedido nos lleva a la ventana de editar.
     */
    @javafx.fxml.FXML
    public void editarPedido(ActionEvent actionEvent) {
        Pedido pedidoSeleccionado = tabla.getSelectionModel().getSelectedItem();
        Session.setCurrentPedido(pedidoSeleccionado);
        if (pedidoSeleccionado != null) {
            try {
                App.changeScene("edit-pedido.fxml", "Editar el pedido");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            // Alert si no se ha seleccionado ningún pedido
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error");
            alert2.setHeaderText("No se ha seleccionado ningun pedido.");
            alert2.setContentText("Selecciona sobre un pedido para editarlo.");
            alert2.showAndWait();
        }
    }
}
