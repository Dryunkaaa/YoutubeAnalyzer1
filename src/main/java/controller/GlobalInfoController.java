package controller;

import entity.Channel;
import entity.provider.ExecutorProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import service.ChannelInfoService;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GlobalInfoController extends AbstractController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(() -> rotatePicture(imageView)).start();

        mainMenu.setOnAction(event -> new MainMenuController().show());
        analyticMenu.setOnAction(event -> new YouTubeAnalyticController().show());
        searchButton.setOnAction(event -> {
            long start = System.currentTimeMillis();
            ObservableList<Channel> channelsList = FXCollections.observableArrayList();

            try {
                ((Future) ExecutorProvider.executorService.submit(() -> {
                    Channel channel = new ChannelInfoService().getChannel(channelIdField.getText());
                    channelsList.add(channel);
                })).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            showChannelsDataIntoTable(tableView, nameColumn, dateColumn, subsColumn, videoColumn, viewsColumn, channelsList);
            showOperationTime(timeText, start);
        });
    }


    @Override
    public void show() {
        loadControllerWindow(this, "fxmls/GlobalInfo.fxml");
    }
}
