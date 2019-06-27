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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXListView<HBox> msgNodes;

    @FXML
    private JFXTextField msgField;

    /* Start Msg variables */

    private static ServerSocket serverSocket;
    private static Socket socket;
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;

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
                serverSocket = new ServerSocket(1201);
                socket = serverSocket.accept();

                inputStream = new DataInputStream(socket.getInputStream());
                outputStream = new DataOutputStream(socket.getOutputStream());

                String msgin = "";
                while (!msgin.equals("exit")) {
                    msgin = CryptoManager.INSTANCE.decrypt(inputStream.readUTF(), Keys.KEY);
                    addMsg(msgin, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        })).start();
    }

    @FXML
    private void sendMsg() {
        if (msgField.getText() == null || msgField.getText().trim().isEmpty())
            return;

        try {
            outputStream.writeUTF(CryptoManager.INSTANCE.encrypt(msgField.getText().trim(), Keys.KEY));
        } catch (IOException e) {
            e.printStackTrace();
        }

        addMsg(msgField.getText().trim(), true);
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
        if (!senderIsServer) {
            container.getChildren().add(new ImageView(new Image("/images/client-48px.png")));
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
