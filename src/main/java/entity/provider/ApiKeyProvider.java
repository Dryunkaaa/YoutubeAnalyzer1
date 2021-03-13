package entity.provider;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ApiKeyProvider {

    private static String apiKey = "";

    public static String getKey() {
        if (apiKey.isEmpty()) {
            loadKey("api.txt");
        }

        return apiKey;
    }

    private static void loadKey(String fileName) {
        try {
            apiKey = new BufferedReader(new FileReader(fileName)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
