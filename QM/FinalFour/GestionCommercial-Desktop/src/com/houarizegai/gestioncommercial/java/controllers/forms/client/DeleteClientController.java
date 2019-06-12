package com.houarizegai.gestioncommercial.java.controllers.forms.client;

import com.houarizegai.gestioncommercial.java.controllers.ClientController;
import com.houarizegai.gestioncommercial.java.database.dao.ClientDao;
import com.houarizegai.gestioncommercial.java.database.models.Client;
import com.jfoenix.controls.JFXSnackbar;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteClientController implements Initializable {

    @FXML
    private Label lblNumero, lblNom, lblPrenom;
    @FXML

    private JFXSnackbar toastMsg;

    // client u want to delete it
    public static Client client;

    @FXML
    private VBox root;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* Initialize the view by information of client u want to delete it */
        toastMsg = new JFXSnackbar(root);
        lblNumero.setText(String.valueOf(client.getNumClient()));
        lblNom.setText(client.getNomClient());
        lblPrenom.setText(client.getPrenom());
    }

    @FXML
    private void onDelete() {
        int status = ClientDao.deleteClient(client.getNumClient());

        if (status == -1) {
            System.out.println("Connection error (cannot delete client)!");
        } else {
            toastMsg.show("Vous avez ajouter un client !", 1500);
        }

        ClientController.dialogClientDelete.close();
    }

    @FXML
    private void onCancel() {
        ClientController.dialogClientDelete.close();
    }
}
