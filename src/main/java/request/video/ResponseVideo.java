package request.video;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import entity.provider.ApiKeyProvider;

public class ResponseVideo {
    private ItemVideo<Statistic>[] items;

    private ResponseVideo() {}

    public static ResponseVideo getInstance(String id) throws UnirestException {
        HttpResponse<ResponseVideo> response = Unirest.get("https://www.googleapis.com/youtube/v3/videos")
                .queryString("key", ApiKeyProvider.getKey())
                .queryString("id", id)
                .queryString("part", "statistics")
                .asObject(ResponseVideo.class);
        return response.getBody();
    }

    public ItemVideo<Statistic>[] getItems() {
        return items;
    }

    public void setItems(ItemVideo<Statistic>[] items) {
        this.items = items;
    }
}
