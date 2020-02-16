package service.sorting;

import entity.Channel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Comparator;

public class NameSorting extends AbstractSorting {

    public NameSorting(TextField channelIdField, TableView<Channel> tableView, TableColumn<Channel, String> nameColumn, TableColumn<Channel, String> dateColumn, TableColumn<Channel, String> subsColumn, TableColumn<Channel, String> videoColumn, TableColumn<Channel, String> viewsColumn, Text showTime) {
        super(channelIdField, tableView, nameColumn, dateColumn, subsColumn, videoColumn, viewsColumn, showTime);
    }

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
