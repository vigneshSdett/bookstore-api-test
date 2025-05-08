package com.bookStore.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream input = new FileInputStream("src/test/resources/config.properties");
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file.", e);
        }
    }

    public static String getBaseUri() {
        return properties.getProperty("base.uri", "http://127.0.0.1:8000");
    }

    public static String getContentType() {
        return properties.getProperty("content.type", "application/json");
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
