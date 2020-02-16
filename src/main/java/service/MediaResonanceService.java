package service;

import entity.Channel;
import entity.provider.ExecutorProvider;
import entity.provider.PropertiesProvider;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import service.sorting.CommentsSorting;

import java.util.Collections;
import java.util.Properties;

public class MediaResonanceService {

    private static final int CHANNELS_ID_NUMBER = 2;

    private TextField channelIdField;
    private TableView<Channel> tableView;
    private TableColumn<Channel, String> nameColumn;
    private TableColumn<Channel, String> dateColumn;
    private TableColumn<Channel, String> subsColumn;
    private TableColumn<Channel, String> videoColumn;
    private TableColumn<Channel, String> viewsColumn;
    private TableColumn<Channel, String> commentsColumn;
    private Text showTime;

    private long startTime;
    private ExecutorProvider executorProvider = ExecutorProvider.getInstance();

    public MediaResonanceService(TextField channelIdField, TableView<Channel> tableView,
                                 TableColumn<Channel, String> nameColumn, TableColumn<Channel, String> dateColumn,
                                 TableColumn<Channel, String> subsColumn, TableColumn<Channel, String> videoColumn,
                                 TableColumn<Channel, String> viewsColumn, TableColumn<Channel, String> commentsColumn,
                                 Text showTime) {

        this.channelIdField = channelIdField;
        this.tableView = tableView;
        this.nameColumn = nameColumn;
        this.subsColumn = subsColumn;
        this.dateColumn = dateColumn;
        this.videoColumn = videoColumn;
        this.viewsColumn = viewsColumn;
        this.commentsColumn = commentsColumn;
        this.showTime = showTime;
    }

    public void show() {
        startTime = System.currentTimeMillis();
        ObservableList<Channel> channelsList = FXCollections.observableArrayList();

        executorProvider.getExecutorService().submit(() -> {
            checkIfCacheUsed(channelsList, channelIdField.getText());

            commentsColumn.setCellValueFactory(new PropertyValueFactory<>("commentsCount"));
            new ViewService().showData(tableView, nameColumn, dateColumn, subsColumn,
                    videoColumn, viewsColumn, channelsList);

            new ChannelInfoService().showOperationTime(showTime, startTime);
        });
    }

    public void compare() {
        startTime = System.currentTimeMillis();
        ObservableList<Channel> channelsList = FXCollections.observableArrayList();

        executorProvider.getExecutorService().submit(() -> {
            String[] channelIds = channelIdField.getText().split("\\s+");

            if (channelIds.length == CHANNELS_ID_NUMBER) {
                for (int i = 0; i < channelIds.length; i++) {
                    int index = i;

                    executorProvider.getExecutorService().submit(() -> {
                        checkIfCacheUsed(channelsList, channelIds[index]);

                        if (channelsList.size() == CHANNELS_ID_NUMBER) {

                            commentsColumn.setCellValueFactory(new PropertyValueFactory<>("commentsCount"));
                            new ViewService().showData(tableView, nameColumn, dateColumn, subsColumn,
                                    videoColumn, viewsColumn, channelsList);

                            new ChannelInfoService().showOperationTime(showTime, startTime);
                        }
                    });
                }

            } else {
                Platform.runLater(() -> {
                    new AlertService().showMessage("Исправьте кол-во каналов");
                });
            }
        });
    }

    public void sort() {
        startTime = System.currentTimeMillis();
        ObservableList<Channel> channelsList = FXCollections.observableArrayList();

        executorProvider.getExecutorService().submit(() -> {
            String[] channelIds = channelIdField.getText().split("\\s+");

            for (int i = 0; i < channelIds.length; i++) {
                int index = i;

                executorProvider.getExecutorService().submit(() -> {
                    checkIfCacheUsed(channelsList, channelIds[index]);

                    if (channelsList.size() == channelIds.length) {
                        Collections.sort(channelsList, new CommentsSorting().getComparator());

                        commentsColumn.setCellValueFactory(new PropertyValueFactory<>("commentsCount"));
                        new ViewService().showData(tableView, nameColumn, dateColumn, subsColumn,
                                videoColumn, viewsColumn, channelsList);

                        new ChannelInfoService().showOperationTime(showTime, startTime);
                    }
                });
            }
        });
    }

    private void checkIfCacheUsed(ObservableList<Channel> channelsList, String channelId) {
        Properties properties = new PropertiesProvider().get();
        boolean useCache = Boolean.parseBoolean(properties.getProperty("cache.use"));
        CacheService cacheService = new CacheService();

        if (useCache && cacheService.channelContainsComments(channelId)) {
            channelsList.add(cacheService.getChannelFromCache(channelId));
        } else {
            channelsList.add(new RequestService().getChannelWithComments(channelId));
            cacheService.saveChannel(channelsList.get(0));
        }
    }
}