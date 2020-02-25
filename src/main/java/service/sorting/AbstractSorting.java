package service.sorting;

import entity.Channel;

import java.util.Comparator;

public abstract class AbstractSorting {

    public abstract Comparator<Channel> getComparator();
}
