package hexlet.code;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    @Test
    public void consoleCommandTest() {
        String filepath1 = "src/test/resources/file1.json";
        String filepath2 = "src/test/resources/file2.json";
        String[] args = {filepath1, filepath2};
        int exitCode = new CommandLine(new App()).execute(args);
        assertEquals(0, exitCode);
    }
    @Test
    public void generateJsonTest() throws Exception {
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
    @Test
    public void generateYamlTest() throws Exception {
        String filepath1 = "src/test/resources/file1.yml";
        String filepath2 = "src/test/resources/file2.yml";
        String filepath3 = "src/test/resources/file3.yml"; // empty file
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
    @Test
    public void generateJsonYamlTest() throws Exception {
        String filepath1 = "src/test/resources/file1.json";
        String filepath2 = "src/test/resources/file2.yml";
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
