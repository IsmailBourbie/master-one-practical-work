package inventory.controllers.forms.facture;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import inventory.controllers.FactureController;
import inventory.database.dao.ProduitDao;
import inventory.database.models.Produit;
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

public class SelectProduitController implements Initializable {

    @FXML
    private VBox root;
    @FXML
    private TextField fieldSearch;


    // Table produit
    @FXML
    private TreeTableView<TableProduit> tableProduit;

    // Columns of table produit
    private TreeTableColumn<TableProduit, String> colRef, colLibProd, colPrixHt, colTauxTva;

    private List<Produit> produits;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FactureController.selectedProduit = null;

        initTableProduit();
        loadProduitTable();

    }

    /* Start Table */

    private void initTableProduit() {
        colRef = new TreeTableColumn<>("Reference");
        colRef.setPrefWidth(100);
        colRef.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableProduit, String> param) -> param.getValue().getValue().ref);

        colLibProd = new TreeTableColumn<>("Designation");
        colLibProd.setPrefWidth(100);
        colLibProd.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableProduit, String> param) -> param.getValue().getValue().libProd);

        colPrixHt = new TreeTableColumn<>("Price HT");
        colPrixHt.setPrefWidth(140);
        colPrixHt.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableProduit, String> param) -> param.getValue().getValue().prixHt);

        colTauxTva = new TreeTableColumn<>("TVA");
        colTauxTva.setPrefWidth(120);
        colTauxTva.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableProduit, String> param) -> param.getValue().getValue().tauxTva);

        tableProduit.getColumns().addAll(colRef, colLibProd, colPrixHt, colTauxTva);
        tableProduit.setShowRoot(false);
    }

    private void loadProduitTable() {
        produits = ProduitDao.getProduits();

        ObservableList<TableProduit> listProduits = FXCollections.observableArrayList();
        if (produits != null) {
            //boolean isAleadyAdded;
            foreachProduct:
            for (Produit f : produits) {
                for (int i = 0; i < FactureController.listProduits.size(); i++) {
                    String ref = FactureController.listProduits.get(i).ref.getValue();
                    if (ref.equalsIgnoreCase(f.getReference())) {
                        continue foreachProduct;
                    }
                }
                listProduits.add(new TableProduit(f.getReference(), f.getLibProd(), f.getPrixHt(), f.getTauxTva()));
            }
        }

        final TreeItem<TableProduit> treeItem = new RecursiveTreeItem<>(listProduits, RecursiveTreeObject::getChildren);

        try {
            tableProduit.setRoot(treeItem);
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
        String refProduitSelected = colRef.getCellData(tableProduit.getSelectionModel().getSelectedIndex());
        if (refProduitSelected == null) {
            System.out.println("Please choose a product");
            return;
        }
        for (Produit produit : produits) {
            if (produit.getReference().equalsIgnoreCase(refProduitSelected)) {
                FactureController.selectedProduit = produit;
                break;
            }
        }

        FactureController.dialogSelectProduit.close();
    }

    @FXML
    private void onClose() {
        FactureController.dialogSelectProduit.close();
    }

    class TableProduit extends RecursiveTreeObject<TableProduit> {
        StringProperty ref;
        StringProperty libProd;
        StringProperty prixHt;
        StringProperty tauxTva;

        public TableProduit() {

        }

        TableProduit(String ref, String libProd, double prixHt, double tauxTva) {
            this.ref = new SimpleStringProperty(ref);
            this.libProd = new SimpleStringProperty(libProd);
            this.prixHt = new SimpleStringProperty(String.valueOf(prixHt));
            this.tauxTva = new SimpleStringProperty(String.valueOf(tauxTva));
        }
    }
}
