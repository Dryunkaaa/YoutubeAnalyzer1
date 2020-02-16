package controller;

import entity.Channel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import service.sorting.DateSorting;
import service.sorting.NameSorting;
import service.sorting.SubscribersSorting;
import service.sorting.ViewsSorting;

import java.net.URL;
import java.util.ResourceBundle;

public class InfoSortingController extends AbstractController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private Button mainMenu;

    @FXML
    private Button analyticMenu;

    @FXML
    private TextField channelIdField;

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
    private MenuItem nameItem;

    @FXML
    private MenuItem dateItem;

    @FXML
    private MenuItem subsItem;

    @FXML
    private MenuItem videoItem;

    @FXML
    private MenuItem viewsItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(() -> rotatePicture(imageView)).start();

        mainMenu.setOnAction(event -> new MainMenuController().show());
        analyticMenu.setOnAction(event -> new YouTubeAnalyticController().show());

        nameItem.setOnAction(event -> {
            new NameSorting().sort(channelIdField, tableView, nameColumn, dateColumn, subsColumn,
                    videoColumn, viewsColumn, timeText);
        });

        dateItem.setOnAction(event -> {
            new DateSorting().sort(channelIdField, tableView, nameColumn, dateColumn, subsColumn,
                    videoColumn, viewsColumn, timeText);
        });

        subsItem.setOnAction(event -> {
            new SubscribersSorting().sort(channelIdField, tableView, nameColumn, dateColumn, subsColumn,
                    videoColumn, viewsColumn, timeText);
        });

        viewsItem.setOnAction(event -> {
            new ViewsSorting().sort(channelIdField, tableView, nameColumn, dateColumn, subsColumn,
                    videoColumn, viewsColumn, timeText);
        });

        videoItem.setOnAction(event -> {
            new ViewsSorting().sort(channelIdField, tableView, nameColumn, dateColumn, subsColumn,
                    videoColumn, viewsColumn, timeText);
        });

    }

    @Override
    public void show() {
        loadControllerWindow(this, "fxmls/SortByInfo.fxml");
    }
}
