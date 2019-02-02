package inventory.controllers;

import inventory.controllers.forms.fournisseur.DeleteFournisseurController;
import inventory.controllers.forms.fournisseur.EditFournisseurController;
import inventory.database.dao.FournisseurDao;
import inventory.database.models.Fournisseur;
import inventory.database.models.designpatterns.builder.FournisseurBuilder;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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

public class FournisseurController implements Initializable {
    @FXML // Parent of all nodes (root node)
    private StackPane root;

    @FXML // Table fournisseur
    private TreeTableView<TableFournisseur> tableFournisseur;

    // Columns of table fournisseur
    private JFXTreeTableColumn<TableFournisseur, String>  colNumFournisseur, colSociete, colCivilite, colNom, colPrenom,
            colAdresse, colCodePostal, colVille, colPays, colTelephone, colMobile, colFax, colEmail, colObservations;

    // Dialog showing in (add/update) table
    public static JFXDialog dialogFournisseurAdd, dialogFournisseurEdit, dialogFournisseurDelete;

    // Fournisseurs data loading from database
    private List<Fournisseur> fournisseurs;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTableFournisseur();
        loadFournisseurTable();

    }

    /* Start Table */

    class TableFournisseur extends RecursiveTreeObject<TableFournisseur> {
        StringProperty numFournisseur;
        StringProperty societe;
        StringProperty civilite;
        StringProperty nom;
        StringProperty prenom;
        StringProperty adresse;
        StringProperty codePostal;
        StringProperty ville;
        StringProperty pays;
        StringProperty telephone;
        StringProperty mobile;
        StringProperty fax;
        StringProperty email;
        StringProperty observations;

        public TableFournisseur() {

        }

        public TableFournisseur(int numFournisseur, String societe, String civilite, String nom, String prenom,
                                String adresse, String codePostal, String ville, String pays, String telephone, String mobile,
                                String fax, String email, String observations
        ) {
            this.numFournisseur = new SimpleStringProperty(String.valueOf(numFournisseur));
            this.societe = new SimpleStringProperty(societe);
            this.civilite = new SimpleStringProperty(civilite);
            this.nom = new SimpleStringProperty(nom);
            this.prenom = new SimpleStringProperty(prenom);
            this.adresse = new SimpleStringProperty(adresse);
            this.codePostal = new SimpleStringProperty(codePostal);
            this.ville = new SimpleStringProperty(ville);
            this.pays = new SimpleStringProperty(pays);
            this.telephone = new SimpleStringProperty(telephone);
            this.mobile = new SimpleStringProperty(mobile);
            this.fax = new SimpleStringProperty(fax);
            this.email = new SimpleStringProperty(email);
            this.observations = new SimpleStringProperty(observations);
        }
    }
    
    private void initTableFournisseur() {
        colNumFournisseur = new JFXTreeTableColumn<>("N°");
        colNumFournisseur.setPrefWidth(80);
        colNumFournisseur.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().numFournisseur);

        colSociete = new JFXTreeTableColumn<>("Societe");
        colSociete.setPrefWidth(100);
        colSociete.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().societe);
        
        colCivilite = new JFXTreeTableColumn<>("Civilite");
        colCivilite.setPrefWidth(80);
        colCivilite.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().civilite);

        colNom = new JFXTreeTableColumn<>("Nom");
        colNom.setPrefWidth(100);
        colNom.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().nom);

        colPrenom = new JFXTreeTableColumn<>("Prenom");
        colPrenom .setPrefWidth(120);
        colPrenom .setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().prenom);

        colAdresse = new JFXTreeTableColumn<>("Adresse");
        colAdresse .setPrefWidth(150);
        colAdresse .setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().adresse);

        colCodePostal = new JFXTreeTableColumn<>("Code Postal");
        colCodePostal.setPrefWidth(100);
        colCodePostal.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().codePostal);

        colVille = new JFXTreeTableColumn<>("Ville");
        colVille.setPrefWidth(100);
        colVille.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().ville);

        colPays = new JFXTreeTableColumn<>("Pays");
        colPays.setPrefWidth(100);
        colPays.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().pays);

        colTelephone = new JFXTreeTableColumn<>("Telephone");
        colTelephone.setPrefWidth(120);
        colTelephone.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().telephone);

        colMobile = new JFXTreeTableColumn<>("Mobile");
        colMobile.setPrefWidth(120);
        colMobile.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().mobile);

        colFax = new JFXTreeTableColumn<>("Fax");
        colFax.setPrefWidth(120);
        colFax.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().fax);

        colEmail = new JFXTreeTableColumn<>("Email");
        colEmail.setPrefWidth(150);
        colEmail.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().email);

        colObservations = new JFXTreeTableColumn<>("Observations");
        colObservations.setPrefWidth(200);
        colObservations.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableFournisseur, String> param) -> param.getValue().getValue().observations);

        tableFournisseur.getColumns().addAll(colNumFournisseur, colSociete, colCivilite, colNom, colPrenom, colAdresse, colCodePostal, colVille, colPays, colTelephone, colMobile, colFax, colEmail, colObservations);
        tableFournisseur.setPrefWidth(1610d);
        tableFournisseur.setShowRoot(false);
    }

    private void loadFournisseurTable() {
        fournisseurs = FournisseurDao.getFournisseur();

        ObservableList<TableFournisseur> listFournisseurs = FXCollections.observableArrayList();
        if(fournisseurs != null) {
            for(Fournisseur f : fournisseurs) {
                TableFournisseur fournisseurT = new TableFournisseur(f.getNumFournisseur(), f.getSociete(), f.getCivilite(), f.getNom(),
                        f.getPrenom(), f.getAdresse(), f.getCodePostal(), f.getVille(), f.getPays(), f.getTelephone(),
                        f.getMobile(), f.getFax(), f.getEmail(), f.getObservations());

                listFournisseurs.add(fournisseurT);
            }
        }

        final TreeItem<TableFournisseur> treeItem = new RecursiveTreeItem<>(listFournisseurs, RecursiveTreeObject::getChildren);

        try {
            tableFournisseur.setRoot(treeItem);
        } catch (Exception ex) {
            System.out.println("Error catched !");
        }
    }



    /* End Table */

    @FXML
    private void onAdd() {
        try {
            VBox paneAddFournisseur = FXMLLoader.load(getClass().getResource("/resources/views/forms/fournisseur/AddFournisseur.fxml"));
            dialogFournisseurAdd = getSpecialDialog(paneAddFournisseur);
            dialogFournisseurAdd.show();

            JFXTextField fieldSociete = (JFXTextField) ((HBox) ((VBox) ((HBox) paneAddFournisseur.getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).getChildren().get(0);

            // Focus to the first field when i show the dialog
            dialogFournisseurAdd.setOnDialogOpened(e -> fieldSociete.requestFocus());

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void onDelete() {
        // get selected fournisseur from table
        String numFournisseurSelected = colNumFournisseur.getCellData(tableFournisseur.getSelectionModel().getSelectedIndex());
        if (numFournisseurSelected == null) {
            return;
        }

        for (Fournisseur f : fournisseurs) {
            if (f.getNumFournisseur() == Integer.parseInt(numFournisseurSelected)) {
                DeleteFournisseurController.fournisseur = new FournisseurBuilder()
                        .setNumFournisseur(f.getNumFournisseur())
                        .setSociete(f.getSociete())
                        .setCivilite(f.getCivilite())
                        .setNom(f.getNom())
                        .setPrenom(f.getPrenom())
                        .setTelephone(f.getTelephone())
                        .setMobile(f.getMobile())
                        .setFax(f.getFax())
                        .setEmail(f.getEmail())
                        .setAdresse(f.getAdresse())
                        .setCodePostal(f.getCodePostal())
                        .setVille(f.getVille())
                        .setPays(f.getPays())
                        .setObservations(f.getObservations())
                        .build();
                break;
            }
        }

        // Show confirm dialog
        try {
            VBox paneDeleteFournisseur = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/forms/fournisseur/DeleteFournisseur.fxml"));
            dialogFournisseurDelete = getSpecialDialog(paneDeleteFournisseur);
            dialogFournisseurDelete.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void onEdit() {
        String numFournisseurSelected = colNumFournisseur.getCellData(tableFournisseur.getSelectionModel().getSelectedIndex());
        if (numFournisseurSelected == null) {

            return;
        }
        for (Fournisseur fournisseur : fournisseurs) {
            if (fournisseur.getNumFournisseur() == Integer.parseInt(numFournisseurSelected)) {
                EditFournisseurController.fournisseurInfo = fournisseur;
                break;
            }
        }

        try {
            VBox paneEditClient = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/forms/fournisseur/EditFournisseur.fxml"));
            dialogFournisseurEdit = getSpecialDialog(paneEditClient);
            dialogFournisseurEdit.show();

            JFXTextField fieldSociete = (JFXTextField) ((HBox) ((VBox) ((HBox) paneEditClient.getChildren().get(1)).getChildren().get(0)).getChildren().get(1)).getChildren().get(0);

            // Focus to the first field when i show the dialog
            dialogFournisseurEdit.setOnDialogOpened(e -> fieldSociete.requestFocus());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private JFXDialog getSpecialDialog(Region content) { // This function create dialog
        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);
        dialog.setOnDialogClosed(e -> loadFournisseurTable()); // if i close dialog: reload data to table
        return dialog;
    }
}
