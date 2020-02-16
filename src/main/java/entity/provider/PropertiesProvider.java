package entity.provider;

import entity.PropertiesSaver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesProvider {

    public Properties get(){
        try(InputStream inputStream = new FileInputStream("application.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties;

        } catch (FileNotFoundException e) {
            e.printStackTrace();

            Properties properties = new Properties();
            properties.setProperty("cache.use", String.valueOf(false));
            properties.setProperty("cache.path", "src/main/cache");
            properties.setProperty("showOperationTime", String.valueOf(false));
            new PropertiesSaver().save(properties);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Properties();
    }

}
