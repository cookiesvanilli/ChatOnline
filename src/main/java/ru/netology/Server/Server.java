package ru.netology.Server;

import ru.netology.Connection.TCPConnection;
import ru.netology.Connection.TCPConnectionListener;

import java.io.*;
import java.net.*;
import java.util.*;

import static ru.netology.Log.Logger.log;

public class Server implements TCPConnectionListener {

    public final ArrayList<TCPConnection> connections = new ArrayList<>(); // храним список соединений

    public Server(int port) {
        System.out.println("Server running...");

        try (ServerSocket serverSocket = new ServerSocket(port)) { //слушаем порт и принимаем входящее соединение
            while (true) {
                try {
                    new TCPConnection(this, serverSocket.accept());

                } catch (IOException e) {
                    System.out.println("TCPConnection exception " + e);
                    log("TCPConnection exception: " + e, "", " ~~~ (Server.java)");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public synchronized void connectionReady(TCPConnection tcpConnection) {
        connections.add(tcpConnection);
        sendToAllConnections("Client connected: " + tcpConnection, "", " ~~~ Server.java / method: connectionReady");
    }

    @Override
    public synchronized void receiveString(TCPConnection tcpConnection, String value) {
        sendToAllConnections(value, "Client: " + tcpConnection + " send string: ", " ~~~ Server.java / method: receiveString");
    }

    @Override
    public synchronized void disconnect(TCPConnection tcpConnection) {

        connections.remove(tcpConnection);
        sendToAllConnections("Client disconnected: " + tcpConnection, "", " ~~~ Server.java / method: disconnect");
    }

    @Override
    public synchronized void exception(TCPConnection tcpConnection, Exception e) {
        System.out.println("TCPConnection exception: " + e);
        log("TCPConnection exception: " + e, "", " ~~~ Server.java / method: exception");
    }

    private synchronized void sendToAllConnections(String value, String help1, String help2) {
        System.out.println(value);
        for (TCPConnection connection : connections) {
            connection.sendString(value);
        }
        log(value, help1, help2);

    }
}
