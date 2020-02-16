package entity.request.playlist;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import entity.provider.ApiKeyProvider;

public class ResponseComment {
    private String nextPageToken;
    private ItemPlaylist<ContentDetails>[] items;

    private ResponseComment(){}

    public static ResponseComment getInstance(String playlistId, String page) throws UnirestException {
        HttpResponse<ResponseComment> response = Unirest.get("https://www.googleapis.com/youtube/v3/playlistItems")
                .queryString("key", new ApiKeyProvider().getApi())
                .queryString("playlistId", playlistId)
                .queryString("maxResults", 50)
                .queryString("pageToken", page)
                .queryString("part", "contentDetails")
                .asObject(ResponseComment.class);
        return response.getBody();
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public ItemPlaylist<ContentDetails>[] getItems() {
        return items;
    }

    public void setItems(ItemPlaylist<ContentDetails>[] items) {
        this.items = items;
    }
}
