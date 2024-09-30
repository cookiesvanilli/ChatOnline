package ru.netology.Connection;

public interface TCPConnectionListener {
    void connectionReady(TCPConnection tcpConnection); // соединение готово, подключение нового клиента
    void receiveString(TCPConnection tcpConnection, String value);
    void disconnect(TCPConnection tcpConnection); // соединение прервано
    void exception(TCPConnection tcpConnection, Exception e); // произошло исключение
}
