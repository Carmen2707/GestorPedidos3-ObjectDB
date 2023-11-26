package com.example.gestorpedidos3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * La clase App es la clase principal de la aplicación que extiende Application de JavaFX.
 * Controla la creación de la interfaz gráfica y el cambio de escenas en la aplicación.
 */
public class App extends Application {

    private static Stage stage;

    /**
     * Método principal que inicia la aplicación.
     *
     * @param stage el Stage principal de la aplicación.
     * @throws IOException si ocurre un error de entrada/salida al cargar el archivo FXML.
     */
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Cambia la escena actual por una nueva especificada por un archivo FXML y un título.
     *
     * @param fxml  el nombre del archivo FXML de la nueva escena.
     * @param title el título de la nueva escena.
     * @throws IOException si ocurre un error de entrada/salida al cargar el archivo FXML.
     */
    public static void changeScene(String fxml, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/" + fxml));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 700);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Método principal que inicia la aplicación.
     *
     * @param args argumentos pasados a la aplicación.
     */
    public static void main(String[] args) {
        launch();
    }
}
