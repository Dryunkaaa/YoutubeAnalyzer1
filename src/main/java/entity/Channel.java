package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Channel {

    private String id;
    private String name;
    private String date;
    private long subscribersCount;
    private long videoCount;
    private long viewsCount;
    private Date creationDate;
    private long commentsCount;

    public Channel(){}

    public Channel(String id, String name, String date, long subscribersCount, long videoCount,
                   long viewsCount, long commentsCount) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.subscribersCount = subscribersCount;
        this.videoCount = videoCount;
        this.viewsCount = viewsCount;
        this.commentsCount = commentsCount;
        this.setCreationDate(this.date);
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public long getSubscribersCount() {
        return subscribersCount;
    }

    public long getVideoCount() {
        return videoCount;
    }

    public long getViewsCount() {
        return viewsCount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCommentsCount() {
        return commentsCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
        try {
            this.creationDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setSubscribersCount(long subscribersCount) {
        this.subscribersCount = subscribersCount;
    }

    public void setVideoCount(long videoCount) {
        this.videoCount = videoCount;
    }

    public void setViewsCount(long viewsCount) {
        this.viewsCount = viewsCount;
    }

    public void setCreationDate(String date) {
        try {
            this.creationDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ;
    }

    public void setCommentsCount(long commentsCount) {
        this.commentsCount = commentsCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return Objects.equals(id, channel.id) &&
                Objects.equals(name, channel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
