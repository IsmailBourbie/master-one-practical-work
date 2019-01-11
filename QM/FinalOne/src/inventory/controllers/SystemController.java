package inventory.controllers;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SystemController implements Initializable {

    // For show Settings/About Dialog
    public static JFXDialog dialogSettings, dialogAbout;
    // Root node (parent of all nodes)
    @FXML
    private StackPane root;

    @FXML // icon show/hide menu
    private JFXHamburger hamburgerMenu;
    // For make animation to hamburgerMenu
    private HamburgerSlideCloseTransition burgerTask;
    @FXML
    private StackPane holderPane;
    // Drawer (Left Menu)
    @FXML
    private JFXDrawer drawerMenu;
    // content of drawer (view)
    private VBox menuDrawerPane;
    // [Client, Fournisseur, Produit] GUI (FXML)
    private StackPane clientView, fournisseurView, produitView, factureView;
    private VBox homeView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            homeView = FXMLLoader.load(getClass().getResource("/resources/views/Home.fxml"));
            clientView = FXMLLoader.load(getClass().getResource("/resources/views/Client.fxml"));
            fournisseurView = FXMLLoader.load(getClass().getResource("/resources/views/Fournisseur.fxml"));
            produitView = FXMLLoader.load(getClass().getResource("/resources/views/Produit.fxml"));
            factureView = FXMLLoader.load(getClass().getResource("/resources/views/Facture.fxml"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }


        initActionHomeBoxes();

        // Launch Home view
        setNode(homeView);

        // Init Dialog About
        try {
            AnchorPane aboutView = FXMLLoader.load(getClass().getResource("/resources/views/About.fxml"));
            dialogAbout = new JFXDialog(root, aboutView, JFXDialog.DialogTransition.TOP);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }


    private void initActionHomeBoxes() { // Add action to boxes
        // Load views

        ObservableList<Node> boxItems = ((HBox) ((HBox) homeView.getChildren().get(1))).getChildren();

        VBox boxClient = (VBox) boxItems.get(0);
        VBox boxProduit = (VBox) boxItems.get(1);
        VBox boxFacture = (VBox) boxItems.get(2);

        boxClient.setOnMouseClicked(e -> setNode(clientView));
        boxProduit.setOnMouseClicked(e -> setNode(produitView));
        boxFacture.setOnMouseClicked(e -> setNode(factureView));
    }

    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    @FXML
    private void onShowSettings() {
        try {
            VBox settingsView = FXMLLoader.load(getClass().getResource("/resources/views/Settings.fxml"));
            dialogSettings = new JFXDialog(root, settingsView, JFXDialog.DialogTransition.BOTTOM);
            dialogSettings.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @FXML
    private void onShowAbout() {
        dialogAbout.show();
    }
}
