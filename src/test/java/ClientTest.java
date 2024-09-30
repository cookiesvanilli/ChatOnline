import org.junit.Test;
import ru.netology.Client.Client;
import ru.netology.Connection.TCPConnection;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class ClientTest {
    @Test
    public void onConnectionReady() {

        String exception = "";
        Client chatClient = new Client();
        try {
            chatClient.connectionReady(new TCPConnection(chatClient, "localhost", 4000));
        } catch (IOException e) {
            exception = "Исключение: " + e;
        }
        assertEquals("Исключение: java.net.ConnectException: Connection refused: connect", exception);

    }
}
