package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    private ObjectMapper objectMapper;
    private static int arrayLength;
    private static String expected1;
    private static String expected2;
    private static String expected3;
    private static String expected4;
    private static String expected5;
    private static String expected6;
    private static String expected7;
    private static String expected8;
    private static String expected9;
    private static String expected10;
    private static String filepath1;
    private static String filepath2;
    private static String filepath3;
    private static String filepath4;
    private static String filepath5;
    private static String filepath6;

    @BeforeAll
    public static void beforeAll() throws IOException {
        expected1 = getContent("src/test/resources/expected1");
        expected2 = getContent("src/test/resources/expected2");
        expected3 = getContent("src/test/resources/expected3");
        expected4 = getContent("src/test/resources/expected4");
        expected5 = getContent("src/test/resources/expected5");
        expected6 = getContent("src/test/resources/expected6");
        expected7 = getContent("src/test/resources/expected7");
        expected8 = getContent("src/test/resources/expected8");
        expected9 = getContent("src/test/resources/expected9");
        expected10 = getContent("src/test/resources/expected10");

        filepath1 = "src/test/resources/file1.json";
        filepath2 = "src/test/resources/file2.json";
        filepath3 = "src/test/resources/file3.json"; // empty file
        filepath4 = "src/test/resources/file1.yml";
        filepath5 = "src/test/resources/file2.yml";
        filepath6 = "src/test/resources/file3.yml"; // empty file
    }

    @Test
    public void generateJsonTest() throws Exception {
        String actual = Differ.generate(filepath1, filepath2);
        assertEquals(expected1, actual);

        actual = Differ.generate(filepath1, filepath3);
        assertEquals(expected2, actual);

        actual = Differ.generate(filepath3, filepath1);
        assertEquals(expected3, actual);

        actual = Differ.generate(filepath3, filepath3);
        assertEquals(expected4, actual);
    }

    @Test
    public void generateYamlTest() throws Exception {
        String actual = Differ.generate(filepath4, filepath5);
        assertEquals(expected1, actual);

        actual = Differ.generate(filepath4, filepath6);
        assertEquals(expected2, actual);

        actual = Differ.generate(filepath6, filepath4);
        assertEquals(expected3, actual);

        actual = Differ.generate(filepath6, filepath6);
        assertEquals(expected4, actual);
    }

    @Test
    public void generateJsonYamlTest() throws Exception {
        String actual = Differ.generate(filepath4, filepath2);
        assertEquals(expected1, actual);

        actual = Differ.generate(filepath4, filepath3);
        assertEquals(expected2, actual);

        actual = Differ.generate(filepath3, filepath4);
        assertEquals(expected3, actual);

        actual = Differ.generate(filepath3, filepath6);
        assertEquals(expected4, actual);
    }

    @Test
    public void generatePlainTest() throws Exception {
        String actual = Differ.generate(filepath1, filepath2, "plain");
        assertEquals(expected5, actual);

        actual = Differ.generate(filepath1, filepath3, "plain");
        assertEquals(expected6, actual);

        actual = Differ.generate(filepath3, filepath1, "plain");
        assertEquals(expected7, actual);

        actual = Differ.generate(filepath3, filepath3, "plain");
        assertEquals("", actual);
    }

    @Test
    public void generateJsonFormatTest() throws Exception {
        String actual = Differ.generate(filepath1, filepath2, "json");
        assertEquals(expected8, actual);

        actual = Differ.generate(filepath1, filepath3, "json");
        assertEquals(expected9, actual);

        actual = Differ.generate(filepath3, filepath1, "json");
        assertEquals(expected10, actual);

        actual = Differ.generate(filepath3, filepath3, "json");
        assertEquals("[ ]", actual);
    }

    public static String getContent(String filepath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filepath))).replace("\r\n", "\n");
    }
}

