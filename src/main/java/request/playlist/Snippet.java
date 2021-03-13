package request.playlist;

public class Snippet<T> {
    private T contentDetails;

    public T getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(T contentDetails) {
        this.contentDetails = contentDetails;
    }
}
