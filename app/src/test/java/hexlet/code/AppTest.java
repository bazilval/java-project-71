package hexlet.code;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {
    @Test
    public void callTest() {
        String filepath1 = "src/test/resources/file1.json";
        String filepath2 = "src/test/resources/file2.json";

        String[] args = {filepath1, filepath2};
        int exitCode = new CommandLine(new App()).execute(args);
        assertEquals(0, exitCode);
    }
}
