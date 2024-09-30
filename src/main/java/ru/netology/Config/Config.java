package ru.netology.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private final int PORT;
    private final String HOST;

    public int getPORT() {
        return PORT;
    }

    public String getHOST() {
        return HOST;
    }

    public Config() {
        FileInputStream fileInputStream;
        Properties properties = new Properties();
        try {
            fileInputStream = new FileInputStream("src/main/java/ru/netology/resources/config.properties");
            properties.load(fileInputStream);

            this.PORT = Integer.parseInt(properties.getProperty("server.port"));
            this.HOST = properties.getProperty("server.host");
        } catch (IOException e) {
            throw new RuntimeException("ERROR: The properties file is missing!");
        }
    }
}
