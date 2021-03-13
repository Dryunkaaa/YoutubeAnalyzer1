package service;

import entity.Channel;
import entity.provider.ExecutorProvider;
import entity.provider.PropertiesProvider;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MediaResonanceService {

    private Channel channel;

    public Channel getChannel(String channelId) {
        boolean useCache = Boolean.parseBoolean(PropertiesProvider.getProps().getProperty("cache.use"));
        CacheService cacheService = new CacheService();

        if (useCache && cacheService.channelContainsComments(channelId)) {
            return cacheService.getChannelFromCache(channelId);
        }

        channel = new RequestService().getChannelWithComments(channelId);

        if (useCache) {
            ExecutorProvider.executorService.submit(() -> cacheService.saveChannel(channel));
        }

        return channel;
    }

    public ObservableList<Channel> getChannelsList(String channelIds) {
        ObservableList<Channel> channelsList = FXCollections.observableArrayList();
        String[] ids = channelIds.split("\\s+");
        List<Future> tasks = new ArrayList<>(ids.length);

        for (int i = 0; i < ids.length; i++) {
            int index = i;
            tasks.add(ExecutorProvider.executorService.submit(() -> channelsList.add(getChannel(ids[index]))));
        }

        for (Future future : tasks) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return channelsList;
    }
}
