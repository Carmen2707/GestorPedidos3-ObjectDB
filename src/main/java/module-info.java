module com.example.gestorpedidos3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.gestorpedidos3 to javafx.fxml;
    exports com.example.gestorpedidos3;
}