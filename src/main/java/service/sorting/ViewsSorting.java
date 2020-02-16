package service.sorting;

import entity.Channel;

import java.util.Comparator;

public class ViewsSorting extends AbstractSorting {

    @Override
    public Comparator<Channel> getComparator() {
        Comparator<Channel> viewsComparator = (c1, c2) -> {
            long viewsCount1 = c1.getViewsCount();
            long viewsCount2 = c2.getViewsCount();

            return Long.compare(viewsCount2, viewsCount1);
        };

        return viewsComparator;
    }
}
