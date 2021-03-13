package controller;

import application.App;
import entity.Channel;
import entity.provider.PropertiesProvider;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(3000), imageView);
        rotateTransition.setByAngle(180f);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.setAutoReverse(true);

        rotateTransition.play();
    }

    public void showOperationTime(Text showTime, long startTime) {
        Platform.runLater(() -> {
            boolean showOperationTime = Boolean.parseBoolean(PropertiesProvider.getProps().getProperty("showOperationTime"));

            if (showOperationTime) {
                long time = System.currentTimeMillis() - startTime;
                showTime.setText("Запрос отработан за " + time + " ms.");
            }
        });
    }

    public void showChannelsDataIntoTable(TableView<Channel> tableView,
                                          TableColumn<Channel, String> nameColumn, TableColumn<Channel, String> dateColumn,
                                          TableColumn<Channel, String> subsColumn, TableColumn<Channel, String> videoColumn,
                                          TableColumn<Channel, String> viewsColumn, ObservableList<Channel> channelsList) {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        subsColumn.setCellValueFactory(new PropertyValueFactory<>("subscribersCount"));
        videoColumn.setCellValueFactory(new PropertyValueFactory<>("videoCount"));
        viewsColumn.setCellValueFactory(new PropertyValueFactory<>("viewsCount"));
        tableView.setItems(channelsList);
    }
}
