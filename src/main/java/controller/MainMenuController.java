package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController extends AbstractController implements Initializable {

    @FXML
    private Button analytic;

    @FXML
    private Button settings;

    @FXML
    private ImageView imageView;

    @FXML
    private Button exit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(()-> rotatePicture(imageView)).start();

        analytic.setOnAction(event1 -> new YouTubeAnalyticController().show());
        settings.setOnAction(event -> new SettingsController().show());
        exit.setOnAction(event -> System.exit(0));
    }

    @Override
    public void show() {
        loadControllerWindow(this, "fxmls/MainMenu.fxml");
    }
}

