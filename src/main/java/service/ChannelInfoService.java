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
import javafx.scene.text.Text;

import java.util.Properties;

public class ChannelInfoService {

    private TextField channelIdField;
    private TableView<Channel> tableView;
    private TableColumn<Channel, String> nameColumn;
    private TableColumn<Channel, String> dateColumn;
    private TableColumn<Channel, String> subsColumn;
    private TableColumn<Channel, String> videoColumn;
    private TableColumn<Channel, String> viewsColumn;
    private Text showTime;

    private long startTime;
    private ExecutorProvider executorProvider = ExecutorProvider.getInstance();

    public ChannelInfoService(){}

    public ChannelInfoService(TextField channelIdField, TableView<Channel> tableView,
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

    public void show() {
        startTime = System.currentTimeMillis();
        ObservableList<Channel> channelsList = FXCollections.observableArrayList();

        executorProvider.getExecutorService().submit(() -> {

            checkIfCacheUsed(channelsList, channelIdField.getText());

            new ViewService().showData(tableView, nameColumn, dateColumn, subsColumn,
                    videoColumn, viewsColumn, channelsList);

            showOperationTime(showTime, startTime);

        });
    }

    public void compare() {
        startTime = System.currentTimeMillis();
        ObservableList<Channel> channelsList = FXCollections.observableArrayList();

        executorProvider.getExecutorService().submit(() -> {
            String[] channelIds = channelIdField.getText().split("\\s+");

            if (channelIds.length == 2) {

                for (int i = 0; i < channelIds.length; i++) {
                    int index = i;

                    executorProvider.getExecutorService().submit(() -> {

                        checkIfCacheUsed(channelsList, channelIds[index]);

                        if (channelsList.size() == 2) {
                            new ViewService().showData(tableView, nameColumn, dateColumn, subsColumn,
                                    videoColumn, viewsColumn, channelsList);

                            showOperationTime(showTime, startTime);
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

    public void checkIfCacheUsed(ObservableList<Channel> channelsList, String channelId) {
        Properties properties = new PropertiesProvider().get();
        boolean useCache = Boolean.parseBoolean(properties.getProperty("cache.use"));
        CacheService cacheService = new CacheService();

        if (useCache && cacheService.channelExists(channelId)) {
            channelsList.add(cacheService.getChannelFromCache(channelId));
        } else {
            channelsList.add(new RequestService().getChannelWithInfo(channelId));

            ExecutorProvider.getInstance().getExecutorService().submit(() -> {
                cacheService.saveChannel(channelsList.get(0));
            });

        }
    }

    public void showOperationTime(Text showTime, long startTime) {
        Platform.runLater(() -> {
            Properties properties = new PropertiesProvider().get();
            boolean showOperationTime = Boolean.parseBoolean(properties.getProperty("showOperationTime"));

            if (showOperationTime) {
                long time = System.currentTimeMillis() - startTime;
                showTime.setText("Запрос отработан за " + time + " ms.");
            }
        });
    }
}
