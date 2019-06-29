package controllers

import Constants.PORT
import com.jfoenix.controls.JFXListView
import com.jfoenix.controls.JFXTextField
import crypto.CryptoManager
import crypto.Keys
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import java.net.URL
import java.util.*

class ServerController : Initializable {

    @FXML
    private lateinit var root: VBox

    @FXML
    private lateinit var msgNodes: JFXListView<HBox>

    @FXML
    private lateinit var messageField: JFXTextField

    /* End Msg variables */

    override fun initialize(url: URL, rb: ResourceBundle?) {
        root.setOnKeyPressed { e ->
            if (e.code == KeyCode.ENTER) {
                sendMsg()
            }
        }

        Thread {
            try {
                serverSocket = ServerSocket(PORT)
                socket = serverSocket.accept()

                inputStream = DataInputStream(socket.getInputStream())
                outputStream = DataOutputStream(socket.getOutputStream())

                var messageReceived = ""
                while (messageReceived != "exit") {
                    messageReceived = CryptoManager.decrypt(inputStream.readUTF(), Keys.KEY)
                    addMsg(messageReceived, false)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                socket.close()
            }
        }.start()
    }

    @FXML
    private fun sendMsg() {
        if (messageField.text == null || messageField.text.trim { it <= ' ' }.isEmpty())
            return

        try {
            outputStream.writeUTF(CryptoManager.encrypt(messageField.text.trim { it <= ' ' }, Keys.KEY))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        addMsg(messageField.text.trim { it <= ' ' }, true)
        messageField.text = null
    }

    private fun addMsg(msg: String, senderIsServer: Boolean) {
        val label = Label(msg)
        label.style = ("-fx-font-size: 16px;"
                + "-fx-background-color: #" + (if (senderIsServer) "B00020" else "2196f3") + ";"
                + "-fx-text-fill: #FFF;"
                + "-fx-background-radius:25;"
                + "-fx-padding: 10px;")
        label.isWrapText = true
        label.maxWidth = 400.0

        val container = HBox()
        if (!senderIsServer) {
            container.children.add(ImageView(Image("/images/client-48px.png")))
            container.alignment = Pos.CENTER_LEFT
            container.spacing = 10.0
            container.padding = Insets(0.0, 10.0, 0.0, 0.0)
        } else {
            container.alignment = Pos.CENTER_RIGHT
            container.padding = Insets(0.0, 10.0, 0.0, 10.0)
        }
        container.children.add(label)
        container.prefHeight = 40.0

        Platform.runLater { msgNodes.items.add(container) }
    }

    private lateinit var serverSocket: ServerSocket
    private lateinit var socket: Socket
    private lateinit var inputStream: DataInputStream
    private lateinit var outputStream: DataOutputStream
}
