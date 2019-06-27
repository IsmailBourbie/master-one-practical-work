package controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import crypto.CryptoManager;
import crypto.Keys;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXListView msgNodes;

    @FXML
    private JFXTextField msgField;

    /* Start Msg variables */

    private static Socket s;
    private static DataInputStream din;
    private static DataOutputStream dout;

    /* End Msg variables */


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        root.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                sendMsg();
            }
        });

        (new Thread(() -> {
            try {
                s = new Socket("127.0.0.1", 1201);
                din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                String msgin = "";
                while (!msgin.equals("exit")) {
                    msgin = CryptoManager.INSTANCE.decrypt(din.readUTF(), Keys.KEY);
                    addMsg(msgin, true);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        })).start();
    }

    @FXML
    private void sendMsg() {
        if (msgField.getText() == null || msgField.getText().trim().isEmpty()) {
            return;
        }

        try {
            dout.writeUTF(CryptoManager.INSTANCE.encrypt(msgField.getText().trim(), Keys.KEY));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        addMsg(msgField.getText().trim(), false);
        msgField.setText(null);
    }

    private void addMsg(String msg, boolean senderIsServer) {
        Label lbl = new Label(msg);
        lbl.setStyle("-fx-font-size: 16px;"
                + "-fx-background-color: #" + (senderIsServer ? "B00020" : "2196f3") + ";"
                + "-fx-text-fill: #FFF;"
                + "-fx-background-radius:25;"
                + "-fx-padding: 10px;");
        lbl.setWrapText(true);
        lbl.setMaxWidth(400);

        HBox container = new HBox();
        if (senderIsServer) {
            container.getChildren().add(new ImageView(new Image("/images/server-48px.png")));
            container.setAlignment(Pos.CENTER_LEFT);
            container.setSpacing(10);
            container.setPadding(new Insets(0, 10, 0, 0));
        } else {
            container.setAlignment(Pos.CENTER_RIGHT);
            container.setPadding(new Insets(0, 10, 0, 10));
        }
        container.getChildren().add(lbl);
        container.setPrefHeight(40);
        Platform.runLater(() -> msgNodes.getItems().add(container));

    }

}
