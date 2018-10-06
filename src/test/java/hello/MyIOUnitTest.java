package hello;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

@DisplayName("a")
public class MyIOUnitTest {

    String test1 = "5 3 1\n1 2 3\nA";
    String test2ans = "2";

    @Test
    @DisplayName("runs correctly given 1 cat to transport")
    public void testSingleInput() throws IOException {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        ByteArrayInputStream in = new ByteArrayInputStream(test1.getBytes());
        System.setIn(in);
        System.setIn(System.in);
        SDA18191T.main(new String[0]);
        assertEquals(test1ans, outContent.toString().trim());
    }
}
