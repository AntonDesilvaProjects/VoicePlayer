package com.voiceplayer.utils;

import java.io.InputStream;
import java.util.Properties;

public class ApplicationUtils {
    private ApplicationUtils() {}
    public static Properties loadPropertiesFile(String filePath) {
        Properties properties = new Properties();
        try {
            InputStream inputStream = ApplicationUtils.class.getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new IllegalArgumentException("Invalid properties file path");
            }
            properties.load(inputStream);
        } catch (Exception e) {
            throw new IllegalStateException("Unable to load properties file", e);
        }
        return properties;
    }
}
