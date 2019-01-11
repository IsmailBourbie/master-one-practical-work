package inventory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Launcher extends Application {
    public static Stage stage;

    public static void centerOnScreen() {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        Launcher.stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        Launcher.stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Parent root = null;
        try { // load the FXML file
            root = FXMLLoader.load(getClass().getResource("/resources/views/Login.fxml"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        stage.setScene(new Scene(Objects.requireNonNull(root))); // Add the GUI to scene and the scene to stage (GUI > Scene > Stage)

        // Adding icon of App
        stage.setTitle("Inventory Management"); // Change the title of app
        Launcher.stage = stage;
        stage.show(); // make stage visible
    }
}
