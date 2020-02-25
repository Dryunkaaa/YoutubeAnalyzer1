package service;

import com.mashape.unirest.http.exceptions.UnirestException;
import entity.Channel;
import entity.provider.ExecutorProvider;
import entity.request.ResponseGlobalInfo;
import entity.request.playlist.ResponseComment;
import entity.request.video.ResponseVideo;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestService {

    public Channel getChannelWithInfo(String channelId) {
        try {
            ResponseGlobalInfo globalInfo = ResponseGlobalInfo.getInstance(channelId);

            if (globalInfo.getItems().length == 0) {
                Platform.runLater(() -> new AlertService().showMessage("Убедитесь в правильности запроса!"));
            } else {
                Channel channel = new Channel();
                channel.setId(channelId);
                initChannelFields(channel, globalInfo);

                return channel;
            }
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;

    }

    private void initChannelFields(Channel channel, ResponseGlobalInfo globalInfo){
        String publishedDate = globalInfo.getItems()[0].getSnippet().getPublishedAt();

        String name = globalInfo.getItems()[0].getSnippet().getTitle();
        String date = publishedDate.substring(0, 10);
        int subscriberCount = Integer.parseInt(globalInfo.getItems()[0].getStatistics().getSubscriberCount());
        int videoCount = Integer.parseInt(globalInfo.getItems()[0].getStatistics().getVideoCount());
        long viewCount = Long.parseLong(globalInfo.getItems()[0].getStatistics().getViewCount());

        channel.setName(name);
        channel.setDate(date);
        channel.setSubscribersCount(subscriberCount);
        channel.setVideoCount(videoCount);
        channel.setViewsCount(viewCount);
    }

    public Channel getChannelWithComments(String channelId) {
        try {
            ResponseGlobalInfo globalInfo = ResponseGlobalInfo.getInstance(channelId);

            Channel channel = new Channel();
            channel.setId(channelId);
            initChannelFields(channel, globalInfo);

            List<String> channelIdList = getChannelsIds(globalInfo);
            List<Long> commentsCountList = getCommentsCountList(channelIdList);

            long commentsCount = getCommentsCount(commentsCountList);
            channel.setCommentsCount(commentsCount);

            return channel;
        } catch (UnirestException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<String> getChannelsIds(ResponseGlobalInfo globalInfo) {
        List<String> channelIds = Collections.synchronizedList(new ArrayList<>());

        String playlistId = "";
        for (int i = 0; i < globalInfo.getItems().length; i++) {
            playlistId = globalInfo.getItems()[i].getContentDetails().getRelatedPlaylists().getUploads();
        }

        String page = "";
        while (page != null) {
            try {
                ResponseComment comment = ResponseComment.getInstance(playlistId, page);

                for (int i = 0; i < comment.getItems().length; i++) {
                    channelIds.add(comment.getItems()[i].getContentDetails().getVideoId());

                    for (int j = 0; j < comment.getItems().length; j++) {
                        page = comment.getNextPageToken();
                    }
                }
            } catch (UnirestException e) {
                e.printStackTrace();
            }

        }

        return channelIds;
    }

    private List<Long> getCommentsCountList(List<String> channelIdList) {
        List<Long> comments = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < channelIdList.size(); i++) {
            try {
                ResponseVideo video = ResponseVideo.getInstance(channelIdList.get(i));

                for (int j = 0; j < video.getItems().length; j++) {
                    int index = j;

                    ExecutorProvider.getInstance().getExecutorService().submit(() -> {
                        comments.add(Long.parseLong(video.getItems()[index].getStatistics().getCommentCount()));
                    });
                }

            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }

        return comments;
    }

    private long getCommentsCount(List<Long> commentsList) {
        long commentsCount = 0;

        for (int i = 0; i < commentsList.size(); i++) {
            commentsCount += commentsList.get(i);
        }
        return commentsCount;
    }
}
