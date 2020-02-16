package service.sorting;

import entity.Channel;

import java.util.Comparator;

public class NameSorting extends AbstractSorting {

    @Override
    public Comparator<Channel> getComparator() {
        Comparator<Channel> nameComparator = (c1, c2) -> {
            String ChannelName1 = c1.getName().toUpperCase();
            String ChannelName2 = c2.getName().toUpperCase();

            return ChannelName1.compareTo(ChannelName2);
        };

        return nameComparator;
    }
}
