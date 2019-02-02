package inventory.controllers;

import inventory.controllers.forms.client.DeleteClientController;
import inventory.controllers.forms.client.EditClientController;
import inventory.database.dao.ClientDao;
import inventory.database.models.Client;
import inventory.database.models.designpatterns.builder.ClientBuilder;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private StackPane root;


    @FXML
    private TreeTableView<TableClient> tableClient;

    private JFXTreeTableColumn<TableClient, String> colNumClient, colSociete, colCivilite, colNomClient, colPrenom,
            colAdresse, colVille, colPays, colEmail;

    // Dialog showing in (add/update) table
    public static JFXDialog dialogClientAdd, dialogClientEdit, dialogClientDelete;

    private JFXSnackbar toastMsg;
    // data of table
    private List<Client> clients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        toastMsg = new JFXSnackbar(root);

        initClientTable();
        loadClientTableData();
    }

    class TableClient extends RecursiveTreeObject<TableClient> {
        StringProperty numClient;
        StringProperty societe;
        StringProperty civilite;
        StringProperty nomClient;
        StringProperty prenom;
        StringProperty adresse;
        StringProperty ville;
        StringProperty pays;
        StringProperty email;

        TableClient(int numClient, String societe, String civilite, String nomClient, String prenom,
                           String adresse, String ville, String pays, String email) {
            this.numClient = new SimpleStringProperty(String.valueOf(numClient));
            this.societe = new SimpleStringProperty(societe);
            this.civilite = new SimpleStringProperty(civilite);
            this.nomClient = new SimpleStringProperty(nomClient);
            this.prenom = new SimpleStringProperty(prenom);
            this.adresse = new SimpleStringProperty(adresse);
            this.ville = new SimpleStringProperty(ville);
            this.pays = new SimpleStringProperty(pays);
            this.email = new SimpleStringProperty(email);
        }
    }

    private void initClientTable() { // This function initialize the table by colunms
        colNumClient = new JFXTreeTableColumn<>("Number");
        colNumClient.setPrefWidth(100);
        colNumClient.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().numClient);

        colSociete = new JFXTreeTableColumn<>("Society");
        colSociete.setPrefWidth(100);
        colSociete.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().societe);

        colCivilite = new JFXTreeTableColumn<>("Civility");
        colCivilite.setPrefWidth(100);
        colCivilite.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().civilite);

        colNomClient = new JFXTreeTableColumn<>("Name");
        colNomClient.setPrefWidth(100);
        colNomClient.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().nomClient);

        colPrenom = new JFXTreeTableColumn<>("Last Name");
        colPrenom.setPrefWidth(150);
        colPrenom.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().prenom);

        colAdresse = new JFXTreeTableColumn<>("Address");
        colAdresse.setPrefWidth(250);
        colAdresse.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().adresse);

        colVille = new JFXTreeTableColumn<>("City");
        colVille.setPrefWidth(150);
        colVille.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().ville);

        colPays = new JFXTreeTableColumn<>("Country");
        colPays.setPrefWidth(120);
        colPays.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().pays);

        colEmail = new JFXTreeTableColumn<>("Email");
        colEmail.setPrefWidth(200);
        colEmail.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().email);

        // Add columns to table
        tableClient.getColumns().addAll(colNumClient, colSociete, colCivilite, colNomClient, colPrenom, colAdresse, colVille, colPays, colEmail);
        tableClient.setPrefWidth(1260d);
        tableClient.setShowRoot(false);
    }

    private void loadClientTableData() { // Fill table data from database

        ObservableList<TableClient> listClients = FXCollections.observableArrayList();

        this.clients = ClientDao.getClients(); // Get ClientRegex from database
        if (clients != null) {
            for (Client client : clients) {
                TableClient clientT = new TableClient(client.getNumClient(),
                        client.getSociete(),
                        client.getCivilite(),
                        client.getNomClient(),
                        client.getPrenom(),
                        client.getAdresse(),
                        client.getVille(),
                        client.getPays(),
                        client.getEmail());

                listClients.add(clientT);
            }
        }

        final TreeItem<TableClient> treeItem = new RecursiveTreeItem<>(listClients, RecursiveTreeObject::getChildren);
        try {
            tableClient.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }
    }

    @FXML
    private void onAdd() { // On Add ClientRegex
        try {
            VBox paneAddClient = FXMLLoader.load(getClass().getResource("/resources/views/forms/client/AddClient.fxml"));
            dialogClientAdd = getSpecialDialog(paneAddClient);
            dialogClientAdd.show();

            JFXTextField fieldSociete = (JFXTextField) ((HBox) ((VBox) ((HBox) paneAddClient.getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).getChildren().get(0);

            // Focus to the first field when i show the dialog
            dialogClientAdd.setOnDialogOpened(e -> fieldSociete.requestFocus());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onEdit() {
        String numClientSelected = colNumClient.getCellData(tableClient.getSelectionModel().getSelectedIndex());
        if (numClientSelected == null) {
            toastMsg.show("Svp, selectionné le client qui vous voulez Modifier !", 2000);
            return;
        }
        for (Client client : clients) {
            if (client.getNumClient() == Integer.parseInt(numClientSelected)) {
                EditClientController.clientInfo = client;
                break;
            }
        }

        try {
            VBox paneEditClient = FXMLLoader.load(getClass().getResource("/resources/views/forms/client/EditClient.fxml"));
            dialogClientEdit = getSpecialDialog(paneEditClient);
            dialogClientEdit.show();

            TextField fieldSociete = (TextField) ((HBox) ((VBox) ((HBox) paneEditClient.getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).getChildren().get(0);

            // Focus to the first field when i show the dialog
            dialogClientEdit.setOnDialogOpened(e -> fieldSociete.requestFocus());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onDelete() {
        // get selected client from table
        String numClientSelected = colNumClient.getCellData(tableClient.getSelectionModel().getSelectedIndex());
        if (numClientSelected == null) {
            System.out.println("Please choose a client!");
            return;
        }

        int status = ClientDao.deleteClient(Integer.parseInt(numClientSelected));

        if (status == -1) {
            System.out.println("Connection error (cannot delete client)!");
        } else {
            System.out.println("Connection error (cannot delete client)!");
        }
        loadClientTableData();
    }

    private JFXDialog getSpecialDialog(Region content) { // This function create dialog
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed(e -> loadClientTableData()); // if i close dialog: reload data to table
        return dialog;
    }

}
