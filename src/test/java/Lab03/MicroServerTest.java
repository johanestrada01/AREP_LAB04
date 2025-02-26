package Lab03;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MicroServerTest {

    private MicroServer microServer;

    @BeforeEach
    void setUp() {
        microServer = new MicroServer();
    }

    @Test
    void testGetType() {
        assertEquals("image/png", MicroServer.getType("png"));
        assertEquals("text/html", MicroServer.getType("html"));
        assertEquals("application/javascript", MicroServer.getType("js"));
        assertEquals("text/css", MicroServer.getType("css"));
        assertEquals("application/json", MicroServer.getType("app"));
        assertNull(MicroServer.getType("unknown"));
    }

    @Test
    void testReadInput() throws IOException {
        String simulatedRequest = "GET /index.html HTTP/1.1\r\nHost: localhost\r\n\r\n";
        BufferedReader reader = new BufferedReader(new StringReader(simulatedRequest));
        String result = MicroServer.readInput(reader);
        assertEquals("text/html /index.html", result);
    }

    @Test
    void testCreateOutputWithFile(@TempDir Path tempDir) throws Exception {
        String content = "<html><body>Hello World</body></html>";
        Path testFile = tempDir.resolve("index.html");
        Files.writeString(testFile, content);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String response = MicroServer.createOutput("text/html", "index.html", outputStream);

        assertNotNull(response);
        assertTrue(response.contains("HTTP/1.1 200 OK"));
        assertTrue(response.contains("Hello World"));
    }

    @Test
    void testSendImage(@TempDir Path tempDir) throws IOException {
        Path imageFile = tempDir.resolve("test.png");
        byte[] imageData = {0x1, 0x2, 0x3};
        Files.write(imageFile, imageData);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        MicroServer.sendImage("HTTP/1.1 200 OK\r\n", outputStream, imageFile.toFile());

        byte[] result = outputStream.toByteArray();
        assertTrue(result.length > 0);
    }
}
