import org.junit.Test;
import ru.netology.Client.Client;
import ru.netology.Connection.TCPConnection;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TCPConnectionTest {

    @Test
    public void tcpConnectionTest() {
        String exception = "";

        try {
            new TCPConnection(new Client(), "localhost", 4000);
        } catch (IOException e) {
            exception = String.valueOf(e);
        }
        assertEquals("java.net.ConnectException: Connection refused: connect", exception);
    }


}
