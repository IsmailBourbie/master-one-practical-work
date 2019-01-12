package inventory.controllers.forms.produit;

import inventory.controllers.ProduitController;
import inventory.database.DBConnection;
import inventory.database.dao.*;
import inventory.database.models.Fournisseur;
import inventory.database.models.FraisPort;
import inventory.database.models.Produit;
import inventory.database.models.designpatterns.builder.ProduitBuilder;
import inventory.utils.regex.ProduitRegex;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddProduitController implements Initializable {


    @FXML
    private TextField fieldReference, fieldCodeBarre, fieldLibProd, fieldPrixHT, fieldGenCode, fieldQteMin, fieldQteReappro;


    @FXML
    private TextArea areaDesc;

    @FXML
    private ComboBox<String> comboTauxTva, comboFamille, comboPort, comboFournisseur;

    @FXML
    private CheckBox tglPlusAuCatalogue;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCombos();
        initFieldListener();
    }

    private void initCombos() {
        for (double taux : Objects.requireNonNull(TVADao.getTauxTva())) // get Taux TVA from db
            comboTauxTva.getItems().add(String.valueOf(taux));

        for (FraisPort port : Objects.requireNonNull(FraisPortDao.getFraisPorts())) // get Frais port from db
            comboPort.getItems().add(port.getLibFraisPort());

        for (String[] f : Objects.requireNonNull(FamilleDao.getFamilles())) // get Familles from db
            comboFamille.getItems().add(String.valueOf(f[1]));

        for (Fournisseur f : Objects.requireNonNull(FournisseurDao.getFournisseur()))
            comboFournisseur.getItems().add(f.getNumFournisseur() + " " + f.getNom() + " " + f.getPrenom());
    }

    private void initFieldListener() {
        fieldReference.textProperty().addListener((observable, oldValue, newValue) -> setValidFontRequired(fieldReference, newValue, ProduitRegex.REFERENCE));
        fieldGenCode.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldGenCode, newValue, ProduitRegex.GEN_CODE));
        fieldCodeBarre.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldCodeBarre, newValue, ProduitRegex.CODE_BARRE));
        fieldLibProd.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldLibProd, newValue, ProduitRegex.LIB_PROD));
        fieldPrixHT.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldPrixHT, newValue, ProduitRegex.PRIX_HT));
        fieldQteReappro.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldQteReappro, newValue, ProduitRegex.QTE_REAPPRO));
        fieldQteMin.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldQteMin, newValue, ProduitRegex.QTE_MIN));
    }

    private void setValidFont(TextField field, String newValue, String regex) { // Change the font if not valid or reset color
        if (newValue != null && !newValue.trim().isEmpty() && !newValue.trim().matches(regex)) {
            field.setStyle("--un-focus-color: #E00; --focus-color: #D00;");
        } else {
            field.setStyle("--un-focus-color: #777; --focus-color: #0f9d58;");
        }
    }

    private void setValidFontRequired(TextField field, String newValue, String regex) {
        if (newValue == null || !newValue.trim().matches(regex)) {
            field.setStyle("--un-focus-color: #E00; --focus-color: #D00;");
        } else {
            field.setStyle("--un-focus-color: #777; --focus-color: #0f9d58;");
        }
    }

    @FXML
    private void onAdd() { // Add produit to database

        String fournisseurNomPrenom = comboFournisseur.getSelectionModel().getSelectedItem();
        int numFournisseur = fournisseurNomPrenom == null ? 1 : Integer.parseInt(fournisseurNomPrenom.split(" ")[0]);
        String codeFamille = FamilleDao.getCodeFamille(comboFamille.getSelectionModel().getSelectedItem());
        String codePort = comboPort.getSelectionModel().getSelectedItem() == null ? null : FraisPortDao.getCodePort(comboPort.getSelectionModel().getSelectedItem());

        // Using builder design pattern to make produit object
        Produit produit = new ProduitBuilder()
                .setReference(fieldReference.getText())
                .setGenCode(fieldGenCode.getText())
                .setCodeBarre(fieldCodeBarre.getText())
                .setLibProd(fieldLibProd.getText())
                .setDescription(areaDesc.getText())
                .setPrixHt((fieldPrixHT.getText() == null || fieldPrixHT.getText().trim().isEmpty()) ? 0d : Double.parseDouble(fieldPrixHT.getText()))
                .setQteReappro((fieldQteReappro.getText() == null || fieldQteReappro.getText().trim().isEmpty()) ? 0 : Integer.parseInt(fieldQteReappro.getText()))
                .setQteMini((fieldQteMin.getText() == null || fieldQteMin.getText().trim().isEmpty()) ? 0 : Integer.parseInt(fieldQteMin.getText()))
                .setTauxTva((comboTauxTva.getSelectionModel().getSelectedItem() == null) ? 17d : Double.parseDouble(comboTauxTva.getSelectionModel().getSelectedItem()))
                //.setPhoto(imageProduit.getImage())
                .setNumFournisseur(numFournisseur)
                .setPlusAuCatalogue(tglPlusAuCatalogue.isSelected())
                .setSaisiPar(DBConnection.user)
                .setSaisiLe(new Date())
                .setCodeFamille(codeFamille)
                .setCodePort(codePort)
                .build();


        int status = ProduitDao.addProduit(produit);

        switch (status) {
            case -1:
                System.out.println("Error of connection");
                break;
            case 0:
                System.out.println("Error in adding the product");
                break;
            default: {
                System.out.println("Was added !");
                onClear();
                break;
            }
        }
    }

    @FXML
    private void onClear() { // Add clear screen (remove the content of all nodes in this field)
        fieldReference.setText(null);
        fieldCodeBarre.setText(null);
        fieldLibProd.setText(null);
        fieldPrixHT.setText(null);
        fieldGenCode.setText(null);
        fieldQteMin.setText(null);
        fieldQteReappro.setText(null);

        areaDesc.setText(null);

        comboTauxTva.getSelectionModel().clearSelection();
        comboFournisseur.getSelectionModel().clearSelection();
        comboFamille.getSelectionModel().clearSelection();
        comboPort.getSelectionModel().clearSelection();

        tglPlusAuCatalogue.setSelected(false);

        fieldReference.setStyle("--un-focus-color: #777; --focus-color: #0f9d58;");
    }

    @FXML
    private void onClose() {
        ProduitController.dialogProduitAdd.close();
    }

}
