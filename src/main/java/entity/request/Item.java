package entity.request;

public class Item<T, G, K> {
    private T snippet;
    private G statistics;
    private K contentDetails;

    public T getSnippet() {
        return snippet;
    }

    public void setSnippet(T snippet) {
        this.snippet = snippet;
    }

    public G getStatistics() {
        return statistics;
    }

    public void setStatistics(G statistics) {
        this.statistics = statistics;
    }

    public K getContentDetails() {
        return contentDetails;
    }

    public void setContentDetails(K contentDetails) {
        this.contentDetails = contentDetails;
    }
}
