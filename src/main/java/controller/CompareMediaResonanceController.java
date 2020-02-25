package controller;

import entity.Channel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import service.AlertService;
import service.MediaResonanceService;

import java.net.URL;
import java.util.ResourceBundle;

public class CompareMediaResonanceController extends AbstractController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private Button mainMenu;

    @FXML
    private Button analyticMenu;

    @FXML
    private TextField channelIdField;

    @FXML
    private Button searchButton;

    @FXML
    private Text timeText;

    @FXML
    private TableView<Channel> tableView;

    @FXML
    private TableColumn<Channel, String> nameColumn;

    @FXML
    private TableColumn<Channel, String> dateColumn;

    @FXML
    private TableColumn<Channel, String> subsColumn;

    @FXML
    private TableColumn<Channel, String> videoColumn;

    @FXML
    private TableColumn<Channel, String> viewsColumn;

    @FXML
    private TableColumn<Channel, String> commentsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(() -> rotatePicture(imageView)).start();

        mainMenu.setOnAction(event -> new MainMenuController().show());
        analyticMenu.setOnAction(event -> new YouTubeAnalyticController().show());
        searchButton.setOnAction(event -> {
            if (channelIdField.getText().split("\\s+").length == 2){
                long start = System.currentTimeMillis();
                ObservableList<Channel> channelsList = new MediaResonanceService().getChannelsList(channelIdField.getText());

                commentsColumn.setCellValueFactory(new PropertyValueFactory<>("commentsCount"));
                showChannelsDataIntoTable(tableView, nameColumn, dateColumn, subsColumn, videoColumn, viewsColumn, channelsList);

                showOperationTime(timeText, start);
            }else{
                new AlertService().showMessage("Исправьте кол-во каналов");
            }
        });
    }

    @Override
    public void show() {
        loadControllerWindow(this, "fxmls/CompareMediaResonance.fxml");
    }
}
