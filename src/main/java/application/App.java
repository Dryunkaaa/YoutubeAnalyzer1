package application;

import controller.MainMenuController;
import entity.JsonMapper;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage window;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new JsonMapper();

        window = primaryStage;
        new MainMenuController().show();

        primaryStage.setTitle("YouTube Analyzer");
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> System.exit(0));
    }

    public static Stage getWindow() {
        return window;
    }
}




