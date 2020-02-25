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
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ChannelInfoService {

    private ExecutorProvider executorProvider = ExecutorProvider.getInstance();

    public Channel getChannel(String channelId) {
        Properties properties = new PropertiesProvider().get();
        boolean useCache = Boolean.parseBoolean(properties.getProperty("cache.use"));
        CacheService cacheService = new CacheService();
        Channel channel = null;

        if (useCache) {

            if (cacheService.channelExists(channelId)) {
                channel = cacheService.getChannelFromCache(channelId);
            } else {
                channel = new RequestService().getChannelWithInfo(channelId);

                Channel finalChannel = channel;
                ExecutorProvider.getInstance().getExecutorService().submit(() -> {
                    cacheService.saveChannel(finalChannel);
                });

            }

        } else {
            channel = new RequestService().getChannelWithInfo(channelId);
        }

        return channel;
    }

    public ObservableList<Channel> getChannelsList(String channelIds) {
        ObservableList<Channel> channelsList = FXCollections.observableArrayList();
        String[] ids = channelIds.split("\\s+");
        List<Future> tasks = new ArrayList<>(ids.length);

        for (int i = 0; i < ids.length; i++) {
            int index = i;

            Future future = executorProvider.getExecutorService().submit(() -> {
                channelsList.add(getChannel(ids[index]));
            });

            tasks.add(future);
        }

        for (Future future : tasks) {
            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return channelsList;
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
