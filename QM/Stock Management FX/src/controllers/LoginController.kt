package controllers

import database.Database
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.stage.Stage
import java.net.URL
import java.util.*

class LoginController : Initializable {

    @FXML
    lateinit var loginButton: Button
    lateinit var usernameField: TextField
    lateinit var passwordField: PasswordField
    lateinit var errorLabel: Label

    @FXML
    fun handleLogin() {
        val username = usernameField.text.trim()
        val password = passwordField.text.trim()
        errorLabel.isVisible = false

        if (isLoginValid(username, password)) {
            Database.user = username
            goToMainStage()
        }
    }

    private fun goToMainStage() {
        val stage = errorLabel.scene.window as Stage
        val new = Stage()
        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("ressources/fxmls/main.fxml"))
        new.title = "Main Screen"
        new.scene = Scene(root, 600.0, 400.0)
        new.isResizable = false
        stage.close()
        new.show()
    }

    private fun isLoginValid(username: String, password: String): Boolean {
        var valid = true
        if (username.isEmpty() || password.isEmpty() || !Database.loginCheck(username, password)) {
            errorLabel.isVisible = true
            valid = false
        }
        return valid
    }

    @FXML
    fun handleReset() {
        passwordField.text = null
        usernameField.text = null
        errorLabel.isVisible = false
    }

    override fun initialize(location: URL?, resources: ResourceBundle?) {

    }

}
