package com.example.gestorpedidos3.controllers;

import com.example.gestorpedidos3.App;
import com.example.gestorpedidos3.Session;
import com.example.gestorpedidos3.domain.item.Item;
import com.example.gestorpedidos3.domain.item.ItemDAO;
import com.example.gestorpedidos3.domain.pedido.Pedido;
import com.example.gestorpedidos3.domain.pedido.PedidoDAO;
import com.example.gestorpedidos3.domain.producto.Producto;
import com.example.gestorpedidos3.domain.producto.ProductoDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * La clase EditPedidoController es el controllador para editar los pedidos.
 */
public class EditPedidoController implements Initializable {

    /**
     * Declaración de elementos para la interfaz.
     */
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
    private TableColumn<Item, String> cProducto;
    @javafx.fxml.FXML
    private TableColumn<Item, String> cCantidad;
    @javafx.fxml.FXML
    private ImageView infoEdit;

    /**
     * El método initialize inicia la ventana.
     *
     * @param url            URL de la ubicación del archivo FXML.
     * @param resourceBundle El ResourceBundle que se puede utilizar para localizar objetos de la interfaz de usuario.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //iniciamos los elementos
        lblTituloPedidoNum.setText("Pedido número " + Session.getCurrentPedido().getCódigo());

        tablaEditItem.getItems().addAll(Session.getCurrentPedido().getItems());
        cCantidad.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getCantidad() + "");
        });
        cProducto.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getProducto() + "");
        });

        spinnerCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 0, 1));
        ProductoDAO productoDAO = new ProductoDAO();
        comboProducto.getItems().addAll(productoDAO.getAll());
    }


    /**
     * @param actionEvent El método añadirItem agrea un nuevo item al pedido.
     */
    @javafx.fxml.FXML
    public void añadirItem(ActionEvent actionEvent) {
        Item itemNuevo = new Item();
        itemNuevo.setCantidad(spinnerCantidad.getValue());

        itemNuevo.setProducto(comboProducto.getValue());
        itemNuevo.setCodigo(Session.getCurrentPedido());
        //añadimos el item a la tabla
        tablaEditItem.getItems().add(itemNuevo);

        //guardamos el item en la base de datos
        Session.setCurrentItem((new ItemDAO()).save(itemNuevo));


    }

    /**
     * @param actionEvent El método borrarItem borra el item seleccionado del pedido.
     */
    @javafx.fxml.FXML
    public void borrarItem(ActionEvent actionEvent) {
        // Obtenemos el item seleccionado
        Item itemSeleccionado = tablaEditItem.getSelectionModel().getSelectedItem();
        Session.setCurrentItem(itemSeleccionado);
        if (itemSeleccionado != null) {
            //creamos el alert de confirmación
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("¿Seguro que quieres borrar el producto " + "'" + Session.getCurrentItem().getProducto().getNombre() + "' ?");
            var result = alert.showAndWait().get();

            if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
                // Eliminar de la tabla
                tablaEditItem.getItems().remove(itemSeleccionado);

                //borramos de la base de datos
                ItemDAO itemDAO = new ItemDAO();
                itemDAO.delete(itemSeleccionado);

                //creamos el alert para confirmar que se ha borrado
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setContentText("Item borrado correctamente");
                alert3.show();
            }
        } else {
            // Alert si no se ha seleccionado ningún item
            Alert alert2 = new Alert(Alert.AlertType.ERROR);
            alert2.setTitle("Error");
            alert2.setHeaderText("No se ha seleccionado ningun item.");
            alert2.setContentText("Selecciona sobre un item para borrarlo.");
            alert2.showAndWait();
        }
    }


    /**
     * @param actionEvent El método cancelar cancela la edición y vuelve a la ventana principal.
     */
    @javafx.fxml.FXML
    public void cancelar(ActionEvent actionEvent) {
        try {
            App.changeScene("main-view.fxml", "Ventana de pedidos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param actionEvent El método guardarItems guarda los cambios de los items del pedido.
     */
    @javafx.fxml.FXML
    public void guardarItems(ActionEvent actionEvent) {
        ItemDAO itemDAO = new ItemDAO();
        // Obtener los elementos de la tabla
        List<Item> itemsTabla = tablaEditItem.getItems();

        // Actualizar la base de datos con los elementos de la tabla
        if (itemsTabla != null) {
            for (Item item : itemsTabla) {
                itemDAO.update(item);
                actualizarTotalPedido();
            }

        }

        //Alert para confirmar que se han guardado los items.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Productos guardados correctamente");
        alert.show();

        try {
            App.changeScene("main-view.fxml", "Ventana de pedidos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * El método actualizarTotalPedido actualiza el coste total tras modificar los items.
     */
    public void actualizarTotalPedido() {
        PedidoDAO pedidoDAO = new PedidoDAO();
        double totalPedido = pedidoDAO.calcularTotalPedido(tablaEditItem.getItems());
        Pedido pedidoActual = Session.getCurrentPedido();
        pedidoActual.setTotal(totalPedido);

        pedidoDAO.update(pedidoActual);
    }

    /**
     * @param event El evento infoEdit se activa al pulsar sobre el botón de información. Esta información ayuda al usuario a entender como utilizar esta ventana.
     */
    @javafx.fxml.FXML
    public void infoEdit(Event event) {
        //Creación del alert.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información de la ventana de editar");
        alert.setHeaderText("¡Hola " + Session.getCurrentUser().getNombre() + "!");
        alert.setContentText("En esta ventana puedes editar los items de tu pedido." + "\n" +
                "Haz click sobre un item y cuando este en azul pulsa sobre el botón borrar para eliminarlo." + "\n" +
                "Para añadir un item nuevo selecciona el producto y la cantidad, y pulsa en añadir." + "\n" +
                "Cuando en la tabla esten todos los productos que deseas en tu pedido, pulsa en guardar.");
        alert.getDialogPane().setPrefSize(400, 250);
        alert.showAndWait();
    }
}
