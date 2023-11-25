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
    private TableColumn<Item, String> cProducto;
    @javafx.fxml.FXML
    private TableColumn<Item, String> cCantidad;
    @javafx.fxml.FXML
    private ImageView infoEdit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTituloPedidoNum.setText("Pedido número "+Session.getCurrentPedido().getCódigo());


        tablaEditItem.getItems().addAll(Session.getCurrentPedido().getItems());
        cCantidad.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getCantidad() + "");
        });
        cProducto.setCellValueFactory((fila) -> {
            return new SimpleStringProperty(fila.getValue().getProducto().getNombre());
        });

        spinnerCantidad.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 0, 1));
        ProductoDAO productoDAO = new ProductoDAO();
        comboProducto.getItems().addAll(productoDAO.getAll());
    }


    /**
     * @param actionEvent El método añadirItem
     */
    @javafx.fxml.FXML
    public void añadirItem(ActionEvent actionEvent) {
        Item itemNuevo = new Item();
        itemNuevo.setCantidad(spinnerCantidad.getValue());

        itemNuevo.setProducto(comboProducto.getValue());
        itemNuevo.setCodigo(Session.getCurrentPedido());

        tablaEditItem.getItems().add(itemNuevo);
        actualizarTotalPedido();
        //guardamos el item en la base de datos
        Session.setCurrentItem((new ItemDAO()).save(itemNuevo));
    }

    @javafx.fxml.FXML
    public void borrarItem(ActionEvent actionEvent) {
        // Obtener el item seleccionado
        Item itemSeleccionado = tablaEditItem.getSelectionModel().getSelectedItem();
        Session.setCurrentItem(itemSeleccionado);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("¿Seguro que quieres borrar el producto " + "'"+ Session.getCurrentProducto().getNombre()+"'");
        var result = alert.showAndWait().get();

        if (result.getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            if (itemSeleccionado != null) {
                // Eliminar de la tabla
                tablaEditItem.getItems().remove(itemSeleccionado);
                actualizarTotalPedido();
                // Eliminar de la base de datos si es necesario
                ItemDAO itemDAO = new ItemDAO();
                itemDAO.delete(itemSeleccionado);
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                alert3.setContentText("Pedido borrado correctamente");
                alert3.show();
            } else {
                // Manejar si no se ha seleccionado ningún item para borrar
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("No se ha seleccionado ningun item.");
                alert2.setContentText("Selecciona sobre un item para borrarlo.");
                alert2.showAndWait();
            }
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

    @javafx.fxml.FXML
    public void guardarItems(ActionEvent actionEvent) {
        ItemDAO itemDAO = new ItemDAO();
        // Obtener los elementos de la tabla
        List<Item> itemsTabla = tablaEditItem.getItems();

        // Actualizar la base de datos con los elementos de la tabla
        if (itemsTabla!=null){
            for (Item item : itemsTabla) {
                itemDAO.update(item);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Productos guardados correctamente");
            alert.show();
        }
        try {
            App.changeScene("main-view.fxml", "Ventana de pedidos");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void actualizarTotalPedido() {
        PedidoDAO pedidoDAO = new PedidoDAO();
        double totalPedido = pedidoDAO.calcularTotalPedido(tablaEditItem.getItems());
        Pedido pedidoActual = Session.getCurrentPedido();
        pedidoActual.setTotal(totalPedido);

        pedidoDAO.update(pedidoActual);
    }

    @javafx.fxml.FXML
    public void infoEdit(Event event) {
        //Creación del alert.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información de la ventana de editar");
        alert.setHeaderText("¡Hola " +  Session.getCurrentUser().getNombre() + "!" );
        alert.setContentText("En esta ventana puedes editar los items de tu pedido." + "\n" +
                "Haz click sobre un item y cuando este en azul pulsa sobre el botón borrar para eliminarlo."+"\n"+
                "Para añadir un item nuevo selecciona el producto y la cantidad, y pulsa en añadir."+"\n"+
                "Cuando en la tabla esten todos los productos que deseas en tu pedido, pulsa en guardar.");
        alert.getDialogPane().setPrefSize(400, 250);
        alert.showAndWait();
    }
}
