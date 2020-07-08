package com.voiceplayer.utils;

import org.springframework.http.HttpHeaders;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;

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

    public static String getFileContent(String filePath) {
        InputStream inputStream = ApplicationUtils.class.getClassLoader().getResourceAsStream(filePath);
        if (inputStream == null) {
            throw new IllegalArgumentException("Invalid file path");
        }
        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines().collect(Collectors.joining("\n"));
    }

    public static HttpHeaders buildHeaders(String... headers) {
        if (headers.length == 0 || headers.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of header name-value pairs!");
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        for (int i = 0; i < headers.length; i = i + 2) {
            httpHeaders.add(headers[i], headers[i+1]);
        }
        return httpHeaders;
    }
}
