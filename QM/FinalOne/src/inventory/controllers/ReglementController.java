package inventory.controllers;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import inventory.database.dao.ReglementDao;
import inventory.database.models.Reglement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class ReglementController implements Initializable {

    // Dialog showing in (add/update) table
    public static JFXDialog dialogClientAdd, dialogClientEdit, dialogClientDelete;
    @FXML
    private StackPane root;
    @FXML
    private TreeTableView<TableReglement> tableClient;
    private JFXTreeTableColumn<TableReglement, String> colidReglement, colnumFacture, colsaisiPar;
    // data of table
    private List<Reglement> clients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        initClientTable();
        loadClientTableData();
    }

    private void initClientTable() { // This function initialize the table by colunms
        colidReglement = new JFXTreeTableColumn<>("Number");
        colidReglement.setPrefWidth(100);
        colidReglement.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableReglement, String> param) -> param.getValue().getValue().idReglement);

        colnumFacture = new JFXTreeTableColumn<>("NumFacture");
        colnumFacture.setPrefWidth(100);
        colnumFacture.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableReglement, String> param) -> param.getValue().getValue().numFacture);

        colsaisiPar = new JFXTreeTableColumn<>("SaisiPar");
        colsaisiPar.setPrefWidth(100);
        colsaisiPar.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableReglement, String> param) -> param.getValue().getValue().saisiPar);
        // Add columns to table
        tableClient.getColumns().addAll(colidReglement, colnumFacture, colsaisiPar);
        tableClient.setPrefWidth(1260d);
        tableClient.setShowRoot(false);
    }

    private void loadClientTableData() { // Fill table data from database

        ObservableList<TableReglement> listClients = FXCollections.observableArrayList();

        this.clients = ReglementDao.getReglements(); // Get ClientRegex from database
        if (clients != null) {
            for (Reglement client : clients) {
                TableReglement clientT = new TableReglement(client.getIdReglement(),
                        client.getNumFacture(),
                        client.getSaisiPar());

                listClients.add(clientT);
            }
        }

        final TreeItem<TableReglement> treeItem = new RecursiveTreeItem<>(listClients, RecursiveTreeObject::getChildren);
        try {
            tableClient.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }
    }

    @FXML
    private void onAdd() { // On Add ClientRegex
        /*try {
            VBox paneAddClient = FXMLLoader.load(getClass().getResource("/resources/views/forms/client/AddClient.fxml"));
            dialogClientAdd = getSpecialDialog(paneAddClient);
            dialogClientAdd.show();

            JFXTextField fieldSociete = (JFXTextField) ((HBox) ((VBox) ((HBox) paneAddClient.getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).getChildren().get(0);

            // Focus to the first field when i show the dialog
            dialogClientAdd.setOnDialogOpened(e -> fieldSociete.requestFocus());

        } catch (IOException ex) {
            ex.printStackTrace();
        }*/
    }

    private JFXDialog getSpecialDialog(Region content) { // This function create dialog
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed(e -> loadClientTableData()); // if i close dialog: reload data to table
        return dialog;
    }

    @FXML
    private void onEdit() {
       /* String numClientSelected = colNumClient.getCellData(tableClient.getSelectionModel().getSelectedIndex());
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
        }*/
    }

    @FXML
    private void onDelete() {
       /* // get selected client from table
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
        loadClientTableData();*/
    }

    class TableReglement extends RecursiveTreeObject<TableReglement> {
        StringProperty idReglement;
        StringProperty numFacture;
        StringProperty saisiPar;


        TableReglement(int idReglement, int numFacture, String saisiPar) {
            this.idReglement = new SimpleStringProperty(String.valueOf(idReglement));
            this.numFacture = new SimpleStringProperty(String.valueOf(numFacture));
            this.saisiPar = new SimpleStringProperty(saisiPar);
        }
    }

}
