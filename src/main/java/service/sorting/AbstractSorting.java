package service.sorting;

import entity.Channel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import service.ChannelInfoService;
import service.ViewService;
import entity.provider.ExecutorProvider;

import java.util.Collections;
import java.util.Comparator;

public abstract class AbstractSorting {

    public void sort(TextField channelIdField, TableView<Channel> tableView,
                     TableColumn<Channel, String> nameColumn, TableColumn<Channel, String> dateColumn,
                     TableColumn<Channel, String> subsColumn, TableColumn<Channel, String> videoColumn,
                     TableColumn<Channel, String> viewsColumn, Text showTime) {

        long startTime = System.currentTimeMillis();
        ExecutorProvider executorProvider = ExecutorProvider.getInstance();
        ObservableList<Channel> channelsList = FXCollections.observableArrayList();


        executorProvider.getExecutorService().submit(() -> {

            String[] channelIds = channelIdField.getText().split("\\s+");

            for (int i = 0; i < channelIds.length; i++) {
                int index = i;

                executorProvider.getExecutorService().submit(() -> {

                    new ChannelInfoService().checkIfCacheUsed(channelsList, channelIds[index]);

                    if (channelsList.size() == channelIds.length) {
                        Collections.sort(channelsList, getComparator());

                        new ViewService().showData(tableView, nameColumn, dateColumn, subsColumn,
                                videoColumn, viewsColumn, channelsList);

                        new ChannelInfoService().showOperationTime(showTime, startTime);
                    }
                });
            }
        });
    }

    public abstract Comparator<Channel> getComparator();
}
