package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    static String excepted1;
    static String excepted2;
    static String excepted3;
    static String excepted4;
    @BeforeAll
    public static void beforeAll() {
        excepted1 = "{\n"
                + "    chars1: [a, b, c]\n"
                + "  - chars2: [d, e, f]\n"
                + "  + chars2: false\n"
                + "  - checked: false\n"
                + "  + checked: true\n"
                + "  - default: null\n"
                + "  + default: [value1, value2]\n"
                + "  - id: 45\n"
                + "  + id: null\n"
                + "  - key1: value1\n"
                + "  + key2: value2\n"
                + "    numbers1: [1, 2, 3, 4]\n"
                + "  - numbers2: [2, 3, 4, 5]\n"
                + "  + numbers2: [22, 33, 44, 55]\n"
                + "  - numbers3: [3, 4, 5]\n"
                + "  + numbers4: [4, 5, 6]\n"
                + "  + obj1: {nestedKey=value, isNested=true}\n"
                + "  - setting1: Some value\n"
                + "  + setting1: Another value\n"
                + "  - setting2: 200\n"
                + "  + setting2: 300\n"
                + "  - setting3: true\n"
                + "  + setting3: none\n"
                + "}";

        excepted2 = "{\n"
                + "  - chars1: [a, b, c]\n"
                + "  - chars2: [d, e, f]\n"
                + "  - checked: false\n"
                + "  - default: null\n"
                + "  - id: 45\n"
                + "  - key1: value1\n"
                + "  - numbers1: [1, 2, 3, 4]\n"
                + "  - numbers2: [2, 3, 4, 5]\n"
                + "  - numbers3: [3, 4, 5]\n"
                + "  - setting1: Some value\n"
                + "  - setting2: 200\n"
                + "  - setting3: true\n"
                + "}";

        excepted3 = "{\n"
                + "  + chars1: [a, b, c]\n"
                + "  + chars2: [d, e, f]\n"
                + "  + checked: false\n"
                + "  + default: null\n"
                + "  + id: 45\n"
                + "  + key1: value1\n"
                + "  + numbers1: [1, 2, 3, 4]\n"
                + "  + numbers2: [2, 3, 4, 5]\n"
                + "  + numbers3: [3, 4, 5]\n"
                + "  + setting1: Some value\n"
                + "  + setting2: 200\n"
                + "  + setting3: true\n"
                + "}";

        excepted4 = "{\n}";
    }
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
        assertEquals(excepted1, actual);

        actual = Differ.generate(filepath1, filepath3);
        assertEquals(excepted2, actual);

        actual = Differ.generate(filepath3, filepath1);
        assertEquals(excepted3, actual);

        actual = Differ.generate(filepath3, filepath3);
        assertEquals(excepted4, actual);
    }
    @Test
    public void generateYamlTest() throws Exception {
        String filepath1 = "src/test/resources/file1.yml";
        String filepath2 = "src/test/resources/file2.yml";
        String filepath3 = "src/test/resources/file3.yml"; // empty file
        String actual = Differ.generate(filepath1, filepath2);
        assertEquals(excepted1, actual);

        actual = Differ.generate(filepath1, filepath3);
        assertEquals(excepted2, actual);

        actual = Differ.generate(filepath3, filepath1);
        assertEquals(excepted3, actual);

        actual = Differ.generate(filepath3, filepath3);
        assertEquals(excepted4, actual);
    }
    @Test
    public void generateJsonYamlTest() throws Exception {
        String filepath1 = "src/test/resources/file1.json";
        String filepath2 = "src/test/resources/file2.yml";
        String filepath3 = "src/test/resources/file3.json"; // empty file
        String actual = Differ.generate(filepath1, filepath2);
        assertEquals(excepted1, actual);

        actual = Differ.generate(filepath1, filepath3);
        assertEquals(excepted2, actual);

        actual = Differ.generate(filepath3, filepath1);
        assertEquals(excepted3, actual);

        actual = Differ.generate(filepath3, filepath3);
        assertEquals(excepted4, actual);
    }
}
