package main

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class Start : Application() {

    override fun start(primaryStage: Stage) {

        val root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("ressources/fxmls/login.fxml"))
        primaryStage.title = "Stock Management"
        primaryStage.scene = Scene(root, 600.0, 400.0)
        primaryStage.isResizable = false;
        primaryStage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(Start::class.java)
}