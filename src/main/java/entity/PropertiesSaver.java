package entity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesSaver {

    public static void save(Properties properties) {
        try (OutputStream output = new FileOutputStream("application.properties")) {
            properties.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
