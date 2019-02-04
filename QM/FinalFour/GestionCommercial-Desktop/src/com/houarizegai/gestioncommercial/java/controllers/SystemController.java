package com.houarizegai.gestioncommercial.java.controllers;

import com.houarizegai.gestioncommercial.java.Launcher;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class SystemController implements Initializable {

    // Root node (parent of all nodes)
    @FXML
    private StackPane root;

    @FXML // This label show date and time (dynamic clock)
    private Label lblDate;

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
    private StackPane clientView, ficheClientView, fournisseurView, produitView, factureView, reglementView;
    private HBox homeView;

    // For show Settings/About Dialog
    public static JFXDialog dialogSettings, dialogAbout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            homeView = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/Home.fxml"));
            clientView = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/Client.fxml"));
            fournisseurView = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/Fournisseur.fxml"));
            produitView = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/Produit.fxml"));
            factureView = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/Facture.fxml"));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        // Initialize the image (to fill parent)

        initMenu();
        initActionHomeBoxes();

        // Launch Home view
        setNode(homeView);

        // Init Dialog About
        try {
            AnchorPane aboutView = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/About.fxml"));
            dialogAbout = new JFXDialog(root, aboutView, JFXDialog.DialogTransition.TOP);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private void initMenu() { // initalize menu (show / hide)
        try {
            menuDrawerPane = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/Menu.fxml"));
            drawerMenu.setSidePane(menuDrawerPane);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        // Add action to Menu Item
        for(Node node : menuDrawerPane.getChildren()) {
            if(node.getAccessibleText() != null) {
                if(node.getAccessibleText().equalsIgnoreCase("btnHome")) {
                    ((JFXButton) node).setOnAction(e -> {
                        setNode(homeView);
                        showHideMenu(); // Hide menu
                    });
                } else if(node.getAccessibleText().equalsIgnoreCase("btnClient")) {
                    ((JFXButton) node).setOnAction(e -> {
                        setNode(clientView);
                        showHideMenu(); // Hide menu
                    });
                } else if(node.getAccessibleText().equalsIgnoreCase("btnFicheClient")) {
                    ((JFXButton) node).setOnAction(e -> {
                        try {
                            ficheClientView = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/FicheClient.fxml"));
                            setNode(ficheClientView);
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        showHideMenu(); // Hide menu
                    });
                } else if(node.getAccessibleText().equalsIgnoreCase("btnFournisseur")) {
                    ((JFXButton) node).setOnAction(e -> {
                        setNode(fournisseurView);
                        showHideMenu(); // Hide menu
                    });
                } else if(node.getAccessibleText().equalsIgnoreCase("btnProduit")) {
                    ((JFXButton) node).setOnAction(e -> {
                        setNode(produitView);
                        showHideMenu(); // Hide menu
                    });
                } else if(node.getAccessibleText().equalsIgnoreCase("btnFacture")) {
                    ((JFXButton) node).setOnAction(e -> {
                        setNode(factureView);
                        showHideMenu(); // Hide menu
                    });
                } else if(node.getAccessibleText().equalsIgnoreCase("btnReglement")) {
                    ((JFXButton) node).setOnAction(e -> {
                        try {
                            reglementView = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/Reglement.fxml"));
                            setNode(reglementView);
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                        showHideMenu(); // Hide menu
                    });
                } else if(node.getAccessibleText().equalsIgnoreCase("onLogout")) {
                    ((JFXButton) node).setOnAction(e -> { // switch to login form
                        try {
                            Parent loginView = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/Login.fxml"));
                            ((Stage) holderPane.getScene().getWindow()).setScene(new Scene(loginView));
                            Launcher.centerOnScreen(); // make stage in the center
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    });
                } else if(node.getAccessibleText().equalsIgnoreCase("onExit")) {
                    // Exit application
                    ((JFXButton) node).setOnAction(e -> Platform.exit());
                }
                // close menu
                drawerMenu.close();
                drawerMenu.setStyle("-fx-pref-width: 0px");
            }
        }
    }

    private void showHideMenu() {
        burgerTask.setRate(burgerTask.getRate() * -1);
        burgerTask.play();

        if(drawerMenu.isShown()) {
            drawerMenu.close();
            drawerMenu.setStyle("-fx-pref-width: 0px");
        } else {
            drawerMenu.setStyle("-fx-pref-width: 270px");
            drawerMenu.open();
        }
    }


    private void initActionHomeBoxes() { // Add action to boxes
        // Load views

        ObservableList<Node> boxItems = ((VBox) ((VBox) homeView.getChildren().get(0))).getChildren();

        HBox boxClient = (HBox) boxItems.get(0);
        HBox boxFicheClient = (HBox) boxItems.get(1);
        HBox boxFournisseur = (HBox) boxItems.get(2);
        HBox boxProduit = (HBox) boxItems.get(3);
        HBox boxFacture = (HBox) boxItems.get(4);
        HBox boxReglement = (HBox) boxItems.get(5);

        boxClient.setOnMouseClicked(e -> setNode(clientView));
        boxFicheClient.setOnMouseClicked(e -> {
            try {
                ficheClientView = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/FicheClient.fxml"));
                setNode(ficheClientView);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });
        boxFournisseur.setOnMouseClicked(e -> setNode(fournisseurView));
        boxProduit.setOnMouseClicked(e -> setNode(produitView));
        boxFacture.setOnMouseClicked(e -> setNode(factureView));
        boxReglement.setOnMouseClicked(e -> {
            try {
                reglementView = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/Reglement.fxml"));
                setNode(reglementView);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });
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
        setNode(homeView);
    }

    @FXML
    private void onShowAbout() {
        try {
            Parent loginView = FXMLLoader.load(getClass().getResource("/com/houarizegai/gestioncommercial/resources/views/Login.fxml"));
            ((Stage) holderPane.getScene().getWindow()).setScene(new Scene(loginView));
            Launcher.centerOnScreen(); // make stage in the center
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
