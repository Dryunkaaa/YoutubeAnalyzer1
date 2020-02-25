package controller;

import entity.PropertiesSaver;
import entity.provider.PropertiesProvider;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import service.AlertService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;

public class SettingsController extends AbstractController implements Initializable {

    @FXML
    private ImageView imageView;

    @FXML
    private Button mainMenu;

    @FXML
    private Button infoButton;

    @FXML
    public CheckBox useCache;

    @FXML
    private TextField pathToCache;

    @FXML
    private CheckBox showTimeRequest;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSettings();

        new Thread(() -> rotatePicture(imageView)).start();

        mainMenu.setOnAction(event -> new MainMenuController().show());
        infoButton.setOnAction(event -> {
            new AlertService().showMessage("Введите путь к папке с кэшем, например src/main/cache , и нажмите Enter");
        });

        saveCache();
        saveShowTime();
        savePath();

    }

    @FXML
    void saveCache() {
        useCache.setOnAction(event -> {
            new Thread(() -> {
                Properties properties = new PropertiesProvider().get();
                properties.setProperty("cache.use", String.valueOf(useCache.isSelected()));
                new PropertiesSaver().save(properties);
            }).start();
        });
    }

    @FXML
    void saveShowTime() {
        showTimeRequest.setOnAction(event -> {
            new Thread(() -> {
                Properties properties = new PropertiesProvider().get();
                properties.setProperty("showOperationTime", String.valueOf(showTimeRequest.isSelected()));
                new PropertiesSaver().save(properties);
            }).start();
        });
    }

    @FXML
    void savePath() {
        pathToCache.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {

                new Thread(() -> {
                    try {
                        String path = pathToCache.getText();

                        Properties properties = new PropertiesProvider().get();
                        properties.setProperty("cache.path", path);
                        new PropertiesSaver().save(properties);

                        File file = new File(path);
                        if (Files.notExists(file.toPath())) {
                            Files.createDirectory(Paths.get(path));
                        }

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        });
    }

    @FXML
    void loadSettings() {
        new Thread(()->{
            Properties properties = new PropertiesProvider().get();
            useCache.setSelected(Boolean.parseBoolean(properties.getProperty("cache.use", String.valueOf(false))));
            showTimeRequest.setSelected(Boolean.parseBoolean(properties.getProperty("showOperationTime", String.valueOf(false))));
            pathToCache.setText(properties.getProperty("cache.path", "src/main/cache"));
        }).start();
    }

    @Override
    public void show() {
        loadControllerWindow(this, "fxmls/Settings.fxml");
    }
}
