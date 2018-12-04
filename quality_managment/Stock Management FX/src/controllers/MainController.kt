package controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage
import java.net.URL
import java.util.*

class MainController : Initializable {

    @FXML
    lateinit var clientButton: Button
    lateinit var productButton: Button


    @FXML
    fun handleClient() {
        val stage = clientButton.scene.window as Stage
        val new = Stage()
        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("ressources/fxmls/client.fxml"))
        new.title = "Add Client"
        new.scene = Scene(root)
        stage.close()
        new.show()
    }

    private fun goToMainStage() {

    }

    @FXML
    fun handleProduct() {
        val stage = clientButton.scene.window as Stage
        val new = Stage()
        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("ressources/fxmls/product.fxml"))
        new.title = "Add Product"
        new.scene = Scene(root, 600.0, 400.0)
        new.isResizable = false
        stage.close()
        new.show()
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }

}
