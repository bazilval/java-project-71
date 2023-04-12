package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    static String expected1;
    static String expected2;
    static String expected3;
    static String expected4;
    @BeforeAll
    public static void beforeAll() {
        expected1 = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";

        expected2 = """
                {
                  - chars1: [a, b, c]
                  - chars2: [d, e, f]
                  - checked: false
                  - default: null
                  - id: 45
                  - key1: value1
                  - numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  - numbers3: [3, 4, 5]
                  - setting1: Some value
                  - setting2: 200
                  - setting3: true
                }""";

        expected3 = """
                {
                  + chars1: [a, b, c]
                  + chars2: [d, e, f]
                  + checked: false
                  + default: null
                  + id: 45
                  + key1: value1
                  + numbers1: [1, 2, 3, 4]
                  + numbers2: [2, 3, 4, 5]
                  + numbers3: [3, 4, 5]
                  + setting1: Some value
                  + setting2: 200
                  + setting3: true
                }""";

        expected4 = "{\n}";
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
        String filepath1 = "src/test/resources/file1.yml";
        String filepath2 = "src/test/resources/file2.yml";
        String filepath3 = "src/test/resources/file3.yml"; // empty file
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
    public void generateJsonYamlTest() throws Exception {
        String filepath1 = "src/test/resources/file1.json";
        String filepath2 = "src/test/resources/file2.yml";
        String filepath3 = "src/test/resources/file3.json"; // empty file
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
    public void generatePlainJsonTest() throws Exception {
        String filepath1 = "src/test/resources/file1.json";
        String filepath2 = "src/test/resources/file2.json";
        String filepath3 = "src/test/resources/file3.json"; // empty file

        String expected5 = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";

        String expected6 = """
                Property 'chars1' was removed
                Property 'chars2' was removed
                Property 'checked' was removed
                Property 'default' was removed
                Property 'id' was removed
                Property 'key1' was removed
                Property 'numbers1' was removed
                Property 'numbers2' was removed
                Property 'numbers3' was removed
                Property 'setting1' was removed
                Property 'setting2' was removed
                Property 'setting3' was removed""";
        String expected7 = """
                Property 'chars1' was added with value: [complex value]
                Property 'chars2' was added with value: [complex value]
                Property 'checked' was added with value: false
                Property 'default' was added with value: null
                Property 'id' was added with value: 45
                Property 'key1' was added with value: 'value1'
                Property 'numbers1' was added with value: [complex value]
                Property 'numbers2' was added with value: [complex value]
                Property 'numbers3' was added with value: [complex value]
                Property 'setting1' was added with value: 'Some value'
                Property 'setting2' was added with value: 200
                Property 'setting3' was added with value: true""";

        String actual = Differ.generate(filepath1, filepath2, "plain");
        assertEquals(expected5, actual);

        actual = Differ.generate(filepath1, filepath3, "plain");
        assertEquals(expected6, actual);

        actual = Differ.generate(filepath3, filepath1, "plain");
        assertEquals(expected7, actual);

        actual = Differ.generate(filepath3, filepath3, "plain");
        assertEquals("", actual);
    }
}
