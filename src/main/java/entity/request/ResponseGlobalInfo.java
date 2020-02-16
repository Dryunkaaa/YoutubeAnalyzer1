package entity.request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import entity.provider.ApiKeyProvider;

public class ResponseGlobalInfo {
    private Item<Snippet, Statistic, ContentDetails<RelatedPlaylists>>[] items;

    private ResponseGlobalInfo(){}

    public static ResponseGlobalInfo getInstance(String channelId) throws UnirestException {
        HttpResponse<ResponseGlobalInfo> response = Unirest.get("https://www.googleapis.com/youtube/v3/channels")
                .queryString("key", new ApiKeyProvider().getApi())
                .queryString("id", channelId)
                .queryString("part", "snippet,contentDetails,statistics")
                .asObject(ResponseGlobalInfo.class);

        return response.getBody();
    }

    public Item<Snippet, Statistic, ContentDetails<RelatedPlaylists>>[] getItems() {
        return items;
    }

    public void setItems(Item<Snippet, Statistic, ContentDetails<RelatedPlaylists>>[] items) {
        this.items = items;
    }
}
