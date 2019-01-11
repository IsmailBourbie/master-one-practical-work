package inventory.controllers;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    // counter Number of image using in slider
    private final byte NUMBER_IMAGE_SLIDER = 3;
    private int counter = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sliderAutoChangePictures();
    }

    private void sliderAutoChangePictures() {
        // Make auto change the slider in duration

        Timeline sliderTimer = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            FadeTransition ft = new FadeTransition();
            ft.setDuration(new Duration(4000));
            ft.setFromValue(1.0);
            ft.setToValue(0.3);
            ft.setCycleCount(0);
            ft.setAutoReverse(true);
            ft.play();

        }),
                new KeyFrame(Duration.seconds(4))
        );
        sliderTimer.setCycleCount(Animation.INDEFINITE);
        sliderTimer.play();
    }

}
