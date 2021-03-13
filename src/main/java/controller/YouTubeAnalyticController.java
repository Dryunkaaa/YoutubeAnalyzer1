package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class YouTubeAnalyticController extends AbstractController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private Button backToMainScreen;

    @FXML
    private MenuItem globalInfoChannel;

    @FXML
    private MenuItem compareGlobalInfo;

    @FXML
    private MenuItem sortByInfo;

    @FXML
    private MenuItem mediaResonance;

    @FXML
    private MenuItem compareMediaResonanse;

    @FXML
    private MenuItem sortByMediaResonanse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(() -> rotatePicture(imageView)).start();

        backToMainScreen.setOnAction(event -> new MainMenuController().show());
        globalInfoChannel.setOnAction(event -> new GlobalInfoController().show());
        compareGlobalInfo.setOnAction(event -> new CompareGlobalInfoController().show());
        sortByInfo.setOnAction(event -> new InfoSortingController().show());
        mediaResonance.setOnAction(event -> new MediaResonanceController().show());
        compareMediaResonanse.setOnAction(event -> new CompareMediaResonanceController().show());
        sortByMediaResonanse.setOnAction(event -> new MediaResonanceSortingController().show());
    }

    @Override
    public void show() {
        loadControllerWindow(this, "fxmls/YouTubeAnalytic.fxml");
    }
}
