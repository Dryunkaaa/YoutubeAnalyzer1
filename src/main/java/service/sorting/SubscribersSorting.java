package service.sorting;

import entity.Channel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Comparator;

public class SubscribersSorting extends AbstractSorting{

    public SubscribersSorting(TextField channelIdField, TableView<Channel> tableView, TableColumn<Channel, String> nameColumn, TableColumn<Channel, String> dateColumn, TableColumn<Channel, String> subsColumn, TableColumn<Channel, String> videoColumn, TableColumn<Channel, String> viewsColumn, Text showTime) {
        super(channelIdField, tableView, nameColumn, dateColumn, subsColumn, videoColumn, viewsColumn, showTime);
    }

    @Override
    public Comparator<Channel> getComparator() {
        Comparator<Channel> subscribersComparator = (c1, c2) -> {
            long subsCount1 = c1.getSubscribersCount();
            long subsCount2 = c2.getSubscribersCount();

            return Long.compare(subsCount2, subsCount1);
        };

        return subscribersComparator;
    }
}
