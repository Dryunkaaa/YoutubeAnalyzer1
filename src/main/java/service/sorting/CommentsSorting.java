package service.sorting;

import entity.Channel;

import java.util.Comparator;

public class CommentsSorting extends AbstractSorting {

    @Override
    public Comparator<Channel> getComparator() {
        return (c1, c2) -> {
            long commentsCount1 = c1.getCommentsCount();
            long commentsCount2 = c2.getCommentsCount();

            return Long.compare(commentsCount2, commentsCount1);
        };
    }
}
