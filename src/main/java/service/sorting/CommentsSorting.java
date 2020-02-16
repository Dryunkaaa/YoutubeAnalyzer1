package service.sorting;

import entity.Channel;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import service.MediaResonanceService;
import service.ViewService;

import java.util.Comparator;

public class CommentsSorting extends AbstractSorting {

    private TableColumn<Channel, String> commentsColumn;

    public CommentsSorting(TextField channelIdField, TableView<Channel> tableView, TableColumn<Channel, String> nameColumn,
                           TableColumn<Channel, String> dateColumn, TableColumn<Channel, String> subsColumn,
                           TableColumn<Channel, String> videoColumn, TableColumn<Channel, String> viewsColumn,
                           TableColumn<Channel, String> commentsColumn, Text showTime) {
        super(channelIdField, tableView, nameColumn, dateColumn, subsColumn, videoColumn, viewsColumn, showTime);

        this.commentsColumn = commentsColumn;
    }

    @Override
    public Comparator<Channel> getComparator() {
        Comparator<Channel> commentsComparator = (c1, c2) -> {
            long commentsCount1 = c1.getCommentsCount();
            long commentsCount2 = c2.getCommentsCount();

            return Long.compare(commentsCount2, commentsCount1);
        };

        return commentsComparator;
    }

    @Override
    protected void viewChannelsData(ObservableList<Channel> channelsList) {
        commentsColumn.setCellValueFactory(new PropertyValueFactory<>("commentsCount"));
        new ViewService().showData(tableView, nameColumn, dateColumn, subsColumn,
                videoColumn, viewsColumn, channelsList);
    }

    @Override
    protected void checkIfCacheUsed(ObservableList<Channel> channelsList, String channelId) {
        new MediaResonanceService().checkIfCacheUsed(channelsList, channelId);
    }
}
