package net.vatri.inventory.controllers;

import database.dao.LoginDao;
import database.models.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.vatri.inventory.App;


public class LoginController {

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    protected void btnLoginPressed(ActionEvent event) {
        if (emailField.getText() == null || !emailField.getText().trim().toLowerCase().matches("[a-z0-9_]{4,}")) {
            System.out.println("an incorrect username");
            return;
        }
        if (passwordField.getText() == null || passwordField.getText().length() < 4) {
            System.out.println("an incorrect password");
            return;
        }

        Login login = new Login(emailField.getText().trim().toLowerCase(), passwordField.getText());

        int status = LoginDao.checkLogin(login);
        switch (status) {
            case -1:
                System.out.println("Connection failed !");
                break;
            case 0:
                errorLabel.setVisible(true);
                break;
            case 1: // Login to the system (show system gui)
                App.showPage("dashboard");
                break;
        }
    }
}