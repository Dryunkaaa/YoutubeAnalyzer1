package service;

import javafx.scene.control.Alert;

public class AlertService {

    public void showMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
