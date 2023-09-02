package ru.clevertec.console.application.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
    public static Properties loadProperties() {
        Properties properties = new Properties();
        try (FileReader fileReader = new FileReader(Thread.currentThread()
                .getContextClassLoader()
                .getResource("config.properties")
                .getPath())) {
            properties.load(fileReader);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String getConfigProperties(String key) {
        Properties properties = loadProperties();
        return properties.getProperty(key);
    }
}
