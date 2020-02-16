package service.sorting;

import entity.Channel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Comparator;

public class ViewsSorting extends AbstractSorting {

    public ViewsSorting(TextField channelIdField, TableView<Channel> tableView, TableColumn<Channel, String> nameColumn, TableColumn<Channel, String> dateColumn, TableColumn<Channel, String> subsColumn, TableColumn<Channel, String> videoColumn, TableColumn<Channel, String> viewsColumn, Text showTime) {
        super(channelIdField, tableView, nameColumn, dateColumn, subsColumn, videoColumn, viewsColumn, showTime);
    }

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
