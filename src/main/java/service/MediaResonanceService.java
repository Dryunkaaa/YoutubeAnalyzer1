package service;

import entity.Channel;
import entity.provider.ExecutorProvider;
import entity.provider.PropertiesProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MediaResonanceService {

    private ExecutorProvider executorProvider = ExecutorProvider.getInstance();

    public Channel getChannel(String channelId) {
        Properties properties = new PropertiesProvider().get();
        boolean useCache = Boolean.parseBoolean(properties.getProperty("cache.use"));
        CacheService cacheService = new CacheService();
        Channel channel = null;

        if (useCache) {

            if (cacheService.channelContainsComments(channelId)) {
                channel = cacheService.getChannelFromCache(channelId);
            } else {
                channel = new RequestService().getChannelWithComments(channelId);

                Channel finalChannel = channel;
                executorProvider.getExecutorService().submit(() -> {
                    cacheService.saveChannel(finalChannel);
                });
            }

        } else {
            channel = new RequestService().getChannelWithComments(channelId);
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
}
