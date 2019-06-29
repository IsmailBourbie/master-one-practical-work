package controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage

import java.io.IOException

class MainController {

    @FXML
    private fun launchServer(e: MouseEvent) {
        (e.source as AnchorPane).isDisable = true

        var root: Parent? = null
        try {
            root = FXMLLoader.load<Parent>(javaClass.getResource("/views/Server.fxml"))
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }

        val scene = Scene(root!!)
        val stage = Stage()
        stage.scene = scene
        stage.icons.add(Image("/images/server.png"))
        stage.title = "Server"

        stage.setOnHiding { event ->

        }

        stage.show()
    }

    @FXML
    private fun launchClient() {
        var root: Parent? = null
        try {
            root = FXMLLoader.load<Parent>(javaClass.getResource("/views/Client.fxml"))
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        }

        val scene = Scene(root!!)
        val stage = Stage()
        stage.scene = scene
        stage.icons.add(Image("/images/client.png"))
        stage.title = "Client"

        stage.setOnHiding { event ->

        }

        stage.show()
    }
}
