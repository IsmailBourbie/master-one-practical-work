package inventory.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import inventory.Launcher;
import inventory.database.dao.LoginDao;
import inventory.database.models.Login;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML // Parent node of all child (root node)
    private VBox root;

    @FXML
    private TextField fieldUser;
    @FXML
    private PasswordField fieldPass;

    private JFXSnackbar toastMsg; // For showing msg (like toast in Android)

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toastMsg = new JFXSnackbar(root);

        root.setOnKeyPressed(e -> { // Execute login action if i click Enter button from Keyboard
            if (e.getCode().equals(KeyCode.ENTER)) {
                onLogin();
            }
        });
    }

    @FXML
    private void onLogin() {


    }
}
