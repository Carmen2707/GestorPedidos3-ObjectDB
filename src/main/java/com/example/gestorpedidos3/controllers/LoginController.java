package com.example.gestorpedidos3.controllers;


import com.example.gestorpedidos3.App;
import com.example.gestorpedidos3.Session;
import com.example.gestorpedidos3.domain.usuario.Usuario;
import com.example.gestorpedidos3.domain.usuario.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * La clase LoginController es el controlador para iniciar sesión.
 */
public class LoginController implements Initializable {
    /**
     * Declaración de elementos
     */
    @javafx.fxml.FXML
    private TextField txtUsuario;
    @javafx.fxml.FXML
    private PasswordField txtContraseña;
    @javafx.fxml.FXML
    private Button btnLogin;
    @javafx.fxml.FXML
    private Label info;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * @param actionEvent El método login comprueba que el usuario y la contraseña que se han introducido esten en la base de datos y accede a su ventana de pedidos.
     */
    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) {
        String nombre = txtUsuario.getText();
        String contraseña = txtContraseña.getText();

        if (nombre.length() < 2 || contraseña.length() < 4) {
            info.setText("Introduce tus datos");

        } else {
            //comprobamos si el usuario esta en la base de datos
            Usuario u = (new UsuarioDAO()).validateUser(nombre, contraseña);

            if (u == null) {
                info.setText("Usuario o contraseña incorrecto");

            } else {
                //Guardamos el usuario en sesión e iremos a su ventana principal
                info.setText("Usuario correcto. ¡Bienvenid@ " + nombre + " !");
                Session.setCurrentUser(u);
                try {
                    App.changeScene("main-view.fxml", "Ventana de pedidos");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}

