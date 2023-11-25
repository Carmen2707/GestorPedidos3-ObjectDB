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

public class LoginController implements Initializable {
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
     * @param actionEvent
     */
    @javafx.fxml.FXML
    public void login(ActionEvent actionEvent) {
        String nombre = txtUsuario.getText();
        String contraseña = txtContraseña.getText();

        if (nombre.length() < 2 || contraseña.length() < 4) {
            info.setText("Introduce tus datos");

        } else {
            /* ACCESO A BASE DE DATOS PARA LA VALIDACION */
            Usuario u = (new UsuarioDAO()).validateUser(nombre, contraseña);

            if (u == null) {
                info.setText("Usuario o contraseña incorrecto");

            } else {
                info.setText("Usuario correcto. ¡Bienvenid@ " + nombre + " !");
                Session.setCurrentUser(u);

                /* Guardar usuario en sesión e ir a la proxima ventana */
                try {
                    App.changeScene("main-view.fxml", "Ventana de pedidos");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }
}

