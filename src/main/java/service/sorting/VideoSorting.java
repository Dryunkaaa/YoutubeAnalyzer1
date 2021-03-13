package service.sorting;

import entity.Channel;

import java.util.Comparator;

public class VideoSorting extends AbstractSorting {

    @Override
    public Comparator<Channel> getComparator() {
        return (c1, c2) -> {
            long videoCount1 = c1.getVideoCount();
            long videoCount2 = c2.getVideoCount();

            return Long.compare(videoCount2, videoCount1);
        };
    }
}
