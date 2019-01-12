package inventory.controllers.forms.facture;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import inventory.controllers.FactureController;
import inventory.database.dao.ClientDao;
import inventory.database.models.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SelectClientController implements Initializable {



    // Table client
    @FXML
    private TreeTableView<TableClient> tableClient;

    // Columns of table client
    private TreeTableColumn<TableClient, String> colNumClient, colNom, colPrenom, colTelephone;

    private List<Client> clients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Init Toast Message

        initTableClient();
        loadClientTable();
    }

    /* Start Table */

    private void initTableClient() {
        colNumClient = new TreeTableColumn<>("N°");
        colNumClient.setPrefWidth(100);
        colNumClient.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().numClient);
        colNom = new TreeTableColumn<>("Name");
        colNom.setPrefWidth(100);
        colNom.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().nom);

        colPrenom = new TreeTableColumn<>("Last Name");
        colPrenom.setPrefWidth(140);
        colPrenom.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().prenom);

        colTelephone = new TreeTableColumn<>("Phone");
        colTelephone.setPrefWidth(120);
        colTelephone.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableClient, String> param) -> param.getValue().getValue().telephone);

        tableClient.getColumns().addAll(colNumClient, colNom, colPrenom, colTelephone);
        tableClient.setShowRoot(false);
    }

    private void loadClientTable() {
        clients = ClientDao.getClients();

        ObservableList<TableClient> listClients = FXCollections.observableArrayList();
        if (clients != null) {
            for (Client f : clients) {
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

    @FXML
    private void onAdd() {
    }



    /* End Table */

    @FXML
    private void onSelect() {
        String numClientSelected = colNumClient.getCellData(tableClient.getSelectionModel().getSelectedIndex());
        if (numClientSelected == null) {
            System.out.println("Please choose a client");
            return;
        }
        for (Client client : clients) {
            if (client.getNumClient() == Integer.parseInt(numClientSelected)) {
                FactureController.selectedClient = client;
                break;
            }
        }

        onClose();
    }

    @FXML
    private void onClose() {
        FactureController.dialogSelectClient.close();
    }

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
}
