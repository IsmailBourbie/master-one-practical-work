package com.houarizegai.gestioncommercial.java.controllers.forms.fournisseur;

import com.houarizegai.gestioncommercial.java.controllers.FournisseurController;
import com.houarizegai.gestioncommercial.java.database.dao.FournisseurDao;
import com.houarizegai.gestioncommercial.java.database.models.Fournisseur;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

public class DeleteFournisseurController implements Initializable {

    @FXML
    private Label lblNumero, lblNom, lblPrenom;
    @FXML
    private Text txtObservations;

    // Fournisseur u want to delete it
    public static Fournisseur fournisseur;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /* Initialize the view by information of client u want to delete it */
        lblNumero.setText(String.valueOf(fournisseur.getNumFournisseur()));
        lblNom.setText(fournisseur.getNom());
        lblPrenom.setText(fournisseur.getPrenom());
    }

    @FXML
    private void onDelete() {
        int status = FournisseurDao.deleteFournisseur(fournisseur.getNumFournisseur());

        if (status == -1) {
            System.out.println("Connection error (cannot delete Fournisseur)!");
        } else {
            System.out.println("deleted");
        }

        FournisseurController.dialogFournisseurDelete.close();
    }

    @FXML
    private void onCancel() {
        FournisseurController.dialogFournisseurDelete.close();
    }
}
