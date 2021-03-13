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
import service.Alerter;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.ResourceBundle;

public class SettingsController extends AbstractController implements Initializable, Alerter {

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
    public void show() {
        loadControllerWindow(this, "fxmls/Settings.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Thread(this::loadSettings).start();
        new Thread(() -> rotatePicture(imageView)).start();

        addHandlerToControls();
    }

    private void addHandlerToControls() {
        mainMenu.setOnAction(event -> new MainMenuController().show());
        infoButton.setOnAction(event -> {
            alert("Введите путь к папке с кэшем, например src/main/cache , и нажмите Enter");
        });

        useCache.setOnAction(event -> new Thread(() -> {
            Properties properties = PropertiesProvider.getProps();
            properties.setProperty("cache.use", String.valueOf(useCache.isSelected()));

            PropertiesSaver.save(properties);
        }).start());

        showTimeRequest.setOnAction(event -> new Thread(() -> {
            Properties properties = PropertiesProvider.getProps();
            properties.setProperty("showOperationTime", String.valueOf(showTimeRequest.isSelected()));

            PropertiesSaver.save(properties);
        }).start());

        pathToCache.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                new Thread(this::savePathToCache).start();
            }
        });
    }

    private void savePathToCache() {
        try {
            String path = pathToCache.getText();

            Properties properties = PropertiesProvider.getProps();
            properties.setProperty("cache.path", path);

            PropertiesSaver.save(properties);

            if (Files.notExists(Path.of(path))) {
                Files.createDirectory(Paths.get(path));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadSettings() {
        Properties properties = PropertiesProvider.getProps();

        useCache.setSelected(Boolean.parseBoolean(properties.getProperty("cache.use", String.valueOf(false))));
        showTimeRequest.setSelected(Boolean.parseBoolean(properties.getProperty("showOperationTime", String.valueOf(false))));
        pathToCache.setText(properties.getProperty("cache.path", "src/main/cache"));
    }
}
