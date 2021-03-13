package service;

import entity.Channel;
import entity.provider.ExecutorProvider;
import entity.provider.PropertiesProvider;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ChannelInfoService {

    private Channel channel;

    public Channel getChannel(String channelId) {
        boolean useCache = Boolean.parseBoolean(PropertiesProvider.getProps().getProperty("cache.use"));
        CacheService cacheService = new CacheService();

        if (useCache && cacheService.channelExists(channelId)) {
            return cacheService.getChannelFromCache(channelId);
        }

        channel = new RequestService().getChannelWithInfo(channelId);

        if (useCache) {
            ExecutorProvider.executorService.submit(() -> cacheService.saveChannel(channel));
        }

        return channel;
    }

    public ObservableList<Channel> getChannelsList(String channelIds) {
        ObservableList<Channel> channels = FXCollections.observableArrayList();
        String[] ids = channelIds.split("\\s+");
        List<Future> tasks = new ArrayList<>(ids.length);

        for (int i = 0; i < ids.length; i++) {
            int index = i;
            tasks.add(ExecutorProvider.executorService.submit(() -> channels.add(getChannel(ids[index]))));
        }

        for (Future future : tasks) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return channels;
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
}
