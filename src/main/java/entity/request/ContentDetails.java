package entity.request;

public class ContentDetails<T> {
    private T relatedPlaylists;

    public T getRelatedPlaylists() {
        return relatedPlaylists;
    }

    public void setRelatedPlaylists(T relatedPlaylists) {
        this.relatedPlaylists = relatedPlaylists;
    }
}
