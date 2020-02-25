package controller;

import entity.Channel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import service.ChannelInfoService;
import service.sorting.*;

import java.net.URL;
import java.util.Collections;
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
            showSortedData(new NameSorting());
        });

        dateItem.setOnAction(event -> {
            showSortedData(new DateSorting());
        });

        subsItem.setOnAction(event -> {
            showSortedData(new SubscribersSorting());
        });

        viewsItem.setOnAction(event -> {
            showSortedData(new ViewsSorting());
        });

        videoItem.setOnAction(event -> {
            showSortedData(new VideoSorting());
        });

    }

    private void showSortedData(AbstractSorting abstractSorting){
        long start = System.currentTimeMillis();

        ObservableList<Channel> channelsList = new ChannelInfoService().getChannelsList(channelIdField.getText());
        Collections.sort(channelsList, abstractSorting.getComparator());
        showChannelsDataIntoTable(tableView, nameColumn, dateColumn, subsColumn, videoColumn, viewsColumn, channelsList);
        showOperationTime(timeText, start);
    }

    @Override
    public void show() {
        loadControllerWindow(this, "fxmls/SortByInfo.fxml");
    }
}
