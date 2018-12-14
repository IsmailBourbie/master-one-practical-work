package windowAgents

import jade.core.Agent
import javafx.application.Application
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.stage.Stage


public class WindowAgentFX : Agent() {


    override fun setup() {
        Application.launch(Window::class.java , localName)
    }

    inner class Window : Application() {

        override fun start(stage: Stage?) {
            val label = Label("Hi There , I'm Agent ${parameters.raw[0]}")
            val button = Button("Close The Agent")
            val mRoot = VBox(10.0, label, button)
            mRoot.alignment = Pos.CENTER
            val mainScene = Scene(mRoot)
            doDelete()
            stage?.title = "Window Agent"
            stage?.scene = mainScene
            stage?.isResizable = false
            stage?.show()
        }
    }
}

