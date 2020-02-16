package controller;

import application.App;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;

public abstract class AbstractController {

    public abstract void show();

    public void loadControllerWindow(Initializable controller, String pathToFxml) {
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource(pathToFxml));
        loader.setController(controller);

        try {
            Parent root = loader.load();
            App.getWindow().setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        protected void rotatePicture(ImageView imageView) {
        Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(3000), imageView);
        rotateTransition.setByAngle(180f);
        rotateTransition.setCycleCount(4);
        rotateTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(rotateTransition);
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();
    }
}
