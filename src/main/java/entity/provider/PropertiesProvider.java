package entity.provider;

import entity.PropertiesSaver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesProvider {

    public static Properties getProps() {
        try (InputStream inputStream = new FileInputStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);

            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties properties = getDefaultProps();
        PropertiesSaver.save(properties);

        return properties;
    }

    private static Properties getDefaultProps() {
        Properties defaultProps = new Properties();

        defaultProps.setProperty("cache.use", String.valueOf(false));
        defaultProps.setProperty("cache.path", "src/main/cache");
        defaultProps.setProperty("showOperationTime", String.valueOf(false));

        return defaultProps;
    }
}
