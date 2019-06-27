import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

import java.io.IOException

class App : Application() {

    @Throws(IOException::class)
    override fun start(stage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("/views/Main.fxml"))
        val scene = Scene(root)
        stage.scene = scene
        stage.title = "Chat FX"
        stage.icons.add(Image("/images/icon-app.png"))
        stage.show()
    }
}

fun main() {
    Application.launch(App::class.java)
}
