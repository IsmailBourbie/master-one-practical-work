package com.houarizegai.gestioncommercial.java.controllers.forms.reglement;

import com.houarizegai.gestioncommercial.java.controllers.ReglementController;
import com.houarizegai.gestioncommercial.java.database.DBConnection;
import com.houarizegai.gestioncommercial.java.database.dao.*;
import com.houarizegai.gestioncommercial.java.database.models.Client;
import com.houarizegai.gestioncommercial.java.database.models.Facture;
import com.houarizegai.gestioncommercial.java.database.models.ModeReglement;
import com.houarizegai.gestioncommercial.java.database.models.Reglement;
import com.houarizegai.gestioncommercial.java.database.models.designpatterns.builder.ReglementBuilder;
import com.houarizegai.gestioncommercial.java.utils.UsefulMethods;
import com.houarizegai.gestioncommercial.java.utils.regex.ProduitRegex;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class EditReglementController implements Initializable {
    @FXML
    private VBox root;

    @FXML
    JFXTextField fieldIdReg;

    @FXML
    private JFXDatePicker pickerDateReg;

    @FXML
    private JFXComboBox<String> comboModeReg;

    @FXML
    private JFXTextField fieldMontant, fieldNumClient;

    @FXML
    private FontAwesomeIconView iconMontant;

    @FXML
    private JFXTextArea areaObs;

    @FXML
    private JFXTextField fieldSearchClient;

    @FXML
    private JFXComboBox<String> comboSearchBy;

    @FXML
    private JFXTreeTableView<TableClient> tableClient;

    // Columns of table client
    private JFXTreeTableColumn<TableClient, String>  colNumClient, colNom, colPrenom, colTelephone;

    private JFXSnackbar toastMsg;

    // Reglement selected infos
    public static Reglement reglementInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toastMsg = new JFXSnackbar(root);

        /* Get mode reglement from BDD and inserted in combo box */
        List<ModeReglement> modeReglements = ReglementDao.getModeReglements();
        if(modeReglements != null)
            for(ModeReglement modeReglement : modeReglements)
                comboModeReg.getItems().addAll(modeReglement.getLibModeReglement());

        // Init list listener
        fieldMontant.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldMontant, iconMontant, newValue, ProduitRegex.PRIX_HT));

        // Initialize combo Client (Search by)
        comboSearchBy.getItems().addAll("Tout", "N° Client", "Nom", "Prenom", "Telephone");
        comboSearchBy.getSelectionModel().selectFirst();

        /* Table client used to select N° Client */
        initClientTable();
        loadClientTable();
        filterClientTableOnSearch();

        /* Add Filter if i change the value of search field */
        fieldSearchClient.setOnKeyReleased(e -> filterClientTableOnSearch());
        comboSearchBy.setOnAction(e -> filterClientTableOnSearch());

        // on Change Select in facture table
        tableClient.setOnMouseClicked((e -> {
            if (tableClient.getSelectionModel().getSelectedItem() == null)
                return;

            fieldNumClient.setText(colNumClient.getCellData(tableClient.getSelectionModel().getSelectedIndex()));
        }));

        onReset();
    }

    private void setValidFont(JFXTextField field, FontAwesomeIconView errorIcon, String newValue, String regex) { // Change the font if not valid or reset color
        if(newValue != null && !newValue.trim().isEmpty() && !newValue.trim().matches(regex)) {
            field.setStyle("-jfx-un-focus-color: #E00; -jfx-focus-color: #D00;");
            errorIcon.setVisible(true);
        } else {
            field.setStyle("-jfx-un-focus-color: #777; -jfx-focus-color: #0f9d58;");
            errorIcon.setVisible(false);
        }
    }

    /* Start Table */

    class TableClient extends RecursiveTreeObject<TableClient> {
        StringProperty numClient;
        StringProperty nom;
        StringProperty prenom;
        StringProperty telephone;

        public TableClient() {

        }

        TableClient(int numClient, String nom, String prenom, String telephone) {
            this.numClient = new SimpleStringProperty(String.valueOf(numClient));
            this.nom = new SimpleStringProperty(nom);
            this.prenom = new SimpleStringProperty(prenom);
            this.telephone = new SimpleStringProperty(telephone);
        }
    }

    private void initClientTable() {
        colNumClient = new JFXTreeTableColumn<>("N°");
        colNumClient.setPrefWidth(100);
        colNumClient.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().numClient);

        colNom = new JFXTreeTableColumn<>("Nom");
        colNom.setPrefWidth(100);
        colNom.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().nom);

        colPrenom = new JFXTreeTableColumn<>("Prenom");
        colPrenom .setPrefWidth(140);
        colPrenom .setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().prenom);

        colTelephone = new JFXTreeTableColumn<>("Telephone");
        colTelephone.setPrefWidth(120);
        colTelephone.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().telephone);

        tableClient.getColumns().addAll(colNumClient, colNom, colPrenom, colTelephone);
        tableClient.setShowRoot(false);
    }

    private void loadClientTable() {
        List<Client> clients = ClientDao.getClients();

        ObservableList<TableClient> listClients = FXCollections.observableArrayList();
        if(clients != null) {
            for(Client f : clients) {
                listClients.add(new TableClient(f.getNumClient(), f.getNomClient(), f.getPrenom(), f.getTelephone()));
            }
        }

        final TreeItem<TableClient> treeItem = new RecursiveTreeItem<>(listClients, RecursiveTreeObject::getChildren);

        try {
            tableClient.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }
    }

    private void filterClientTableOnSearch() {
        tableClient.setPredicate((TreeItem<TableClient> client) -> {
            String numClient = client.getValue().numClient.getValue();
            String nom = client.getValue().nom.getValue().toLowerCase();
            String prenom = client.getValue().prenom.getValue().toLowerCase();
            String telephone = (client.getValue().telephone.getValue() == null) ? "" : client.getValue().telephone.getValue().toLowerCase();

            String fieldSearch = fieldSearchClient.getText() == null ? "" : fieldSearchClient.getText().toLowerCase();

            switch (comboSearchBy.getSelectionModel().getSelectedIndex()) {
                case 1:
                    return numClient.contains(fieldSearch);
                case 2:
                    return nom.contains(fieldSearch);
                case 3:
                    return prenom.contains(fieldSearch);
                case 4:
                    return telephone.contains(fieldSearch);
                default:
                    return numClient.contains(fieldSearch)
                            || nom.contains(fieldSearch)
                            || prenom.contains(fieldSearch)
                            || telephone.contains(fieldSearch);
            }

        });
    }

    /* End Table */

    @FXML
    private void onSave() { // Add reglement to database
        if(iconMontant.isVisible() ) {
            toastMsg.show("Svp, il ya des champs n'est pas bien formé", 2000);
            return;
        }

        if(fieldNumClient.getText().isEmpty()) {
            toastMsg.show("Svp, il ya des champs n'est pas bien formé", 2000);
            return;
        }

        // Using builder design pattern to make reglement object
        Reglement reglement = new ReglementBuilder()
                .setIdReglement(Integer.parseInt(fieldIdReg.getText()))
                .setDateReglement(java.sql.Date.valueOf(pickerDateReg.getValue()))
                .setIdModeReglement(ReglementDao.getIdModeReglement(comboModeReg.getSelectionModel().getSelectedItem()))
                .setNumClient(Integer.parseInt(fieldNumClient.getText()))
                .setSaisiPar(DBConnection.user)
                .setSaisiLe(new Date())
                .setObservations(areaObs.getText())
                .setMontant(Double.parseDouble(fieldMontant.getText()))
                .build();

        int status = ReglementDao.setReglement(reglement);

        switch (status) {
            case -1:
                toastMsg.show("Erreur de connexion !", 1500);
                break;
            case 0:
                toastMsg.show("Erreur dans la modification de Reglement !", 1500);
                break;
            default : {
                toastMsg.show("Vous avez modifier le Reglement ", 1500);
                onClose();
            }
        }
    }

    @FXML
    private void onReset() { // Add clear screen (remove the content of all nodes in this field)
        // Get auto increment
        fieldIdReg.setText(String.valueOf(reglementInfo.getIdReglement()));
        pickerDateReg.setValue(UsefulMethods.getSQLDate(reglementInfo.getDateReglement()).toLocalDate());
        comboModeReg.getSelectionModel().select(ReglementDao.getLibModeReglement(reglementInfo.getIdModeReglement()));
        fieldMontant.setText(String.valueOf(reglementInfo.getMontant()));
        fieldMontant.setStyle("-jfx-un-focus-color: #777; -jfx-focus-color: #0f9d58;");
        iconMontant.setVisible(false);

        areaObs.setText(reglementInfo.getObservations());

        fieldNumClient.setText(String.valueOf(reglementInfo.getNumClient()));
        fieldSearchClient.setText(null);
        tableClient.getSelectionModel().clearSelection();
    }

    @FXML
    private void onClose() {
        ReglementController.dialogReglementEdit.close();
    }

}
