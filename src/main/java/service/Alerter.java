package service;

import javafx.scene.control.Alert;

public interface Alerter {

    default void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
