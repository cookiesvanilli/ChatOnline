package ru.netology.Connection;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPConnection {

    private final Socket socket;
    private Thread thread = null;
    private final TCPConnectionListener tcpConnectionListener;

    private final BufferedReader in;
    private final BufferedWriter out;

    public TCPConnection(TCPConnectionListener tcpConnectionListener, String ipAddress, int port) throws IOException {
        this(tcpConnectionListener, new Socket(ipAddress, port));

    }

    public TCPConnection(TCPConnectionListener tcpConnectionListener, Socket socket) throws IOException {
        this.tcpConnectionListener = tcpConnectionListener;
        this.socket = socket;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));

        thread = new Thread(() -> {
            try {
                tcpConnectionListener.connectionReady(TCPConnection.this);
                while (!thread.isInterrupted()) { //пока поток не прерван
                    tcpConnectionListener.receiveString(TCPConnection.this, in.readLine());
                }
            } catch (IOException e) {
                tcpConnectionListener.exception(TCPConnection.this, e);
            } finally {
                tcpConnectionListener.disconnect(TCPConnection.this);
            }

        });
        thread.start();
    }

    public synchronized void sendString(String value) {
        try {
            out.write(value + "\r\n");
            out.flush(); //метод flush очищает буфер и отправляет данные

        } catch (IOException e) {
            tcpConnectionListener.exception(TCPConnection.this, e);
            disconnect();
        }
    }

    public synchronized void disconnect() {
        thread.interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            tcpConnectionListener.exception(TCPConnection.this, e);
        }
    }

    @Override
    public String toString() {
        return "TCPConnection: " + socket.getInetAddress() + ": " + socket.getPort();
    }
}
