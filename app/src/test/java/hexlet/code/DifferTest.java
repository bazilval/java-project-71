package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    @Test
    public void generateTest() throws Exception {
        String filepath1 = "src/test/resources/file1.json";
        String filepath2 = "src/test/resources/file2.json";
        String filepath3 = "src/test/resources/file3.json"; // empty file
        String actual = Differ.generate(filepath1, filepath2);
        String excepted = "{\n"
                          + "  - follow: false\n"
                          + "    host: hexlet.io\n"
                          + "  - proxy: 123.234.53.22\n"
                          + "  - timeout: 50\n"
                          + "  + timeout: 20\n"
                          + "  + verbose: true\n"
                          + "}";
        assertEquals(excepted, actual);

        actual = Differ.generate(filepath1, filepath3);
        excepted = "{\n"
                + "  - follow: false\n"
                + "  - host: hexlet.io\n"
                + "  - proxy: 123.234.53.22\n"
                + "  - timeout: 50\n"
                + "}";
        assertEquals(excepted, actual);

        actual = Differ.generate(filepath3, filepath1);
        excepted = "{\n"
                + "  + follow: false\n"
                + "  + host: hexlet.io\n"
                + "  + proxy: 123.234.53.22\n"
                + "  + timeout: 50\n"
                + "}";
        assertEquals(excepted, actual);

        actual = Differ.generate(filepath3, filepath3);
        excepted = "{\n}";
        assertEquals(excepted, actual);
    }
}
