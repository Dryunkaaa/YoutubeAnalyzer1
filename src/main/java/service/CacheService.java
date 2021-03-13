package service;

import com.alibaba.fastjson.JSON;
import entity.Channel;
import entity.provider.PropertiesProvider;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CacheService {

    private String pathToCacheFile;

    public CacheService() {
        this.pathToCacheFile = PropertiesProvider.getProps().getProperty("cache.path") + "/channels.txt";
    }

    public boolean channelExists(String channelId) {
        List<Channel> cachedChannels = getCachedChannels();

        for (Channel channel : cachedChannels) {
            if (channel.getId().equals(channelId)) {
                return true;
            }
        }

        return false;
    }

    public Channel getChannelFromCache(String channelId) {
        List<Channel> cachedChannels = getCachedChannels();

        for (Channel channel : cachedChannels) {
            if (channel.getId().equals(channelId)) {
                return channel;
            }
        }

        return new Channel();
    }

    private void removeFromCache(Channel channel) {
        List<Channel> cachedChannels = getCachedChannels();
        cachedChannels.remove(channel);
        clearCache();

        for (Channel cachedChannel : cachedChannels) {
            saveChannel(cachedChannel);
        }
    }

    public void saveChannel(Channel channel) {
        try {
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(pathToCacheFile, true), StandardCharsets.UTF_8));
            writer.write(JSON.toJSONString(channel) + "\n");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean channelContainsComments(String channelId) {
        if (!channelExists(channelId)) return false;
        Channel channel = getChannelFromCache(channelId);
        if (channel.getCommentsCount() != 0) {
            return true;
        }

        removeFromCache(channel);

        return false;
    }

    private List<Channel> getCachedChannels() {
        List<Channel> cachedChannels = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(pathToCacheFile))) {
            while (scanner.hasNextLine()) {
                Channel cachedChannel = parseChannel(scanner.nextLine());
                cachedChannels.add(cachedChannel);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return cachedChannels;
    }

    private void clearCache() {
        try {
            PrintWriter writer = new PrintWriter(pathToCacheFile);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Channel parseChannel(String json) {
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

            String id = String.valueOf(jsonObject.get("id"));
            String date = String.valueOf(jsonObject.get("date"));
            String name = String.valueOf(jsonObject.get("name"));
            long commentsCount = (long) jsonObject.get("commentsCount");
            long viewsCount = (long) jsonObject.get("viewsCount");
            long videoCount = (long) jsonObject.get("videoCount");
            long subscribersCount = (long) jsonObject.get("subscribersCount");

            return new Channel(id, name, date, subscribersCount, videoCount, viewsCount, commentsCount);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Channel();
    }
}
