package service.sorting;

import entity.Channel;

import java.util.Comparator;

public class SubscribersSorting extends AbstractSorting {

    @Override
    public Comparator<Channel> getComparator() {
        return (c1, c2) -> {
            long subsCount1 = c1.getSubscribersCount();
            long subsCount2 = c2.getSubscribersCount();

            return Long.compare(subsCount2, subsCount1);
        };
    }
}
