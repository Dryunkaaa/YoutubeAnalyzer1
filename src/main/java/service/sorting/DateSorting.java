package service.sorting;

import entity.Channel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Comparator;

public class DateSorting extends AbstractSorting {

    public DateSorting(TextField channelIdField, TableView<Channel> tableView, TableColumn<Channel, String> nameColumn, TableColumn<Channel, String> dateColumn, TableColumn<Channel, String> subsColumn, TableColumn<Channel, String> videoColumn, TableColumn<Channel, String> viewsColumn, Text showTime) {
        super(channelIdField, tableView, nameColumn, dateColumn, subsColumn, videoColumn, viewsColumn, showTime);
    }

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
