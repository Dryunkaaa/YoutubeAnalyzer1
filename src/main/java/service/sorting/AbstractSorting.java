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

    protected TextField channelIdField;
    protected TableView<Channel> tableView;
    protected TableColumn<Channel, String> nameColumn;
    protected TableColumn<Channel, String> dateColumn;
    protected TableColumn<Channel, String> subsColumn;
    protected TableColumn<Channel, String> videoColumn;
    protected TableColumn<Channel, String> viewsColumn;
    protected Text showTime;

    public AbstractSorting(TextField channelIdField, TableView<Channel> tableView,
                                 TableColumn<Channel, String> nameColumn, TableColumn<Channel, String> dateColumn,
                                 TableColumn<Channel, String> subsColumn, TableColumn<Channel, String> videoColumn,
                                 TableColumn<Channel, String> viewsColumn, Text showTime) {

        this.channelIdField = channelIdField;
        this.tableView = tableView;
        this.nameColumn = nameColumn;
        this.subsColumn = subsColumn;
        this.dateColumn = dateColumn;
        this.videoColumn = videoColumn;
        this.viewsColumn = viewsColumn;
        this.showTime = showTime;
    }

    public void sort() {
        long startTime = System.currentTimeMillis();
        ExecutorProvider executorProvider = ExecutorProvider.getInstance();
        ObservableList<Channel> channelsList = FXCollections.observableArrayList();


        executorProvider.getExecutorService().submit(() -> {

            String[] channelIds = channelIdField.getText().split("\\s+");

            for (int i = 0; i < channelIds.length; i++) {
                int index = i;

                executorProvider.getExecutorService().submit(() -> {

                    checkIfCacheUsed(channelsList, channelIds[index]);

                    if (channelsList.size() == channelIds.length) {
                        Collections.sort(channelsList, getComparator());

                        viewChannelsData(channelsList);

                        new ChannelInfoService().showOperationTime(showTime, startTime);
                    }
                });
            }
        });
    }

    public abstract Comparator<Channel> getComparator();

    protected void viewChannelsData(ObservableList<Channel> channelsList){
        new ViewService().showData(tableView, nameColumn, dateColumn, subsColumn,
                videoColumn, viewsColumn, channelsList);
    }

    protected void checkIfCacheUsed(ObservableList<Channel> channelsList, String channelId){
        new ChannelInfoService().checkIfCacheUsed(channelsList, channelId);
    }
}
