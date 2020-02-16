package service.sorting;

import entity.Channel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.Comparator;

public class VideoSorting extends AbstractSorting {

    public VideoSorting(TextField channelIdField, TableView<Channel> tableView, TableColumn<Channel, String> nameColumn, TableColumn<Channel, String> dateColumn, TableColumn<Channel, String> subsColumn, TableColumn<Channel, String> videoColumn, TableColumn<Channel, String> viewsColumn, Text showTime) {
        super(channelIdField, tableView, nameColumn, dateColumn, subsColumn, videoColumn, viewsColumn, showTime);
    }

    @Override
    public Comparator<Channel> getComparator() {
        Comparator<Channel> videoComparator = (c1, c2) -> {
            long videoCount1 = c1.getVideoCount();
            long videoCount2 = c2.getVideoCount();

            return Long.compare(videoCount2, videoCount1);
        };

        return videoComparator;
    }
}
