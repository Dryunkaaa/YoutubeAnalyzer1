package service;

import entity.Channel;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewService {

    public void showData(TableView<Channel> tableView,
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
