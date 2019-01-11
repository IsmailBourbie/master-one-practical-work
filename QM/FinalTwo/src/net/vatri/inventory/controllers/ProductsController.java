package net.vatri.inventory.controllers;

import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;

import javafx.scene.control.Button;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;

import net.vatri.inventory.App;
import net.vatri.inventory.libs.BaseController;
import net.vatri.inventory.models.Product;

public class ProductsController extends BaseController implements Initializable {

    @FXML
    private TableView<Product> tblProducts;
    @FXML
    private TableColumn<Product, String> idCol;
    @FXML
    private TableColumn<Product, String> productCol;
    @FXML
    private TableColumn<Product, String> groupCol;
    @FXML
    private TableColumn<Product, String> priceCol;

    @FXML
    private Button btnAddProd;

    public void initialize(URL url, ResourceBundle rb) {


        ObservableList<Product> tblData = FXCollections.observableArrayList(
                inventoryService.getProducts()
        );


        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        groupCol.setCellValueFactory(new PropertyValueFactory<>("group"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblProducts.setItems(tblData);
    }// all()

    @FXML
    protected void handleAddProd() {
        // Since we are adding product, set selected ID to null
        App.getInstance().repository.put("selectedProductId", null);
        App.showPage("newProduct");
    }

    @FXML
    protected void openProduct() {
        Product prod = tblProducts.getSelectionModel().getSelectedItem();
        if (prod != null) {
            App.getInstance().repository.put("selectedProductId", prod.getId().toString());
            App.showPage("newProduct");
        }
    }

}
