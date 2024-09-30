package ru.netology.Server;

import ru.netology.Config.Config;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) throws IOException {
        // Из файла config.properties получаем PORT
        // "src/main/java/ru/netology/resources/config.properties"
        int port = new Config().getPORT();
        new Server(port);
    }
}
