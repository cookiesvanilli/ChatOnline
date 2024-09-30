package ru.netology.Client;

import ru.netology.Config.Config;
import ru.netology.Connection.TCPConnection;
import ru.netology.Connection.TCPConnectionListener;
import ru.netology.Log.Logger;

import java.io.*;
import java.util.*;

public class Client implements TCPConnectionListener {
    private int port;
    private String host;
    private TCPConnection connection;

    public Client() {
        port = new Config().getPORT();
        host = new Config().getHOST();
        try {
            connection = new TCPConnection((TCPConnectionListener) this, host, port);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Hello! Add your USERNAME: ");
            String userName = scanner.nextLine();
            if (userName.trim().isEmpty()) {
                userName = "UserName";

            }
            System.out.println("Enter a message or /exit to exit the chat");
            String message = "";
            while (!(message = scanner.nextLine()).equals("/exit")) {
                connection.sendString(userName + ": " + message);
            }
            connection.disconnect();
        } catch (IOException e) {
            System.out.println("MyException: " + e);
        }
    }

    @Override
    public void connectionReady(TCPConnection tcpConnection) {
        System.out.println("Connection started...");
        printMsg("Connection started...", "", " ~~~ Client.java / method: connectionReady");
    }

    @Override
    public void receiveString(TCPConnection tcpConnection, String value) {
        System.out.println(value);
        printMsg(value, "", " ~~~ Client.java / method: receiveString");
    }

    @Override
    public void disconnect(TCPConnection tcpConnection) {

        System.out.println("Disconnect...");
        printMsg("Disconnect...", "", " ~~~ Client.java / method: disconnect");
    }

    @Override
    public void exception(TCPConnection tcpConnection, Exception e) {
        System.out.println("MyException: " + e);
        printMsg("MyException: " + e, "", " ~~~ Client.java / method: exception ");
    }

    public synchronized void printMsg(String value, String help1, String help2) {
        Logger.log(value, help1, help2);
    }
}
