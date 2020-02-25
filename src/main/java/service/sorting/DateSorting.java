package service.sorting;

import entity.Channel;

import java.util.Comparator;

public class DateSorting extends AbstractSorting {

    @Override
    public Comparator<Channel> getComparator() {
        Comparator<Channel> dateComparator = (c1, c2) -> {
            long firstDateTime = c1.getCreationDate().getTime();
            long secondDateTime = c2.getCreationDate().getTime();

            return Long.compare(firstDateTime, secondDateTime);
        };

        return dateComparator;
    }
}
