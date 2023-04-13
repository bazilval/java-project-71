package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTest {
    private static String expected1;
    private static String expected2;
    private static String expected3;
    private static String expected4;
    private static String filepath1;
    private static String filepath2;
    private static String filepath3;
    private static String filepath4;
    private static String filepath5;
    private static String filepath6;

    @BeforeAll
    public static void beforeAll() {
        expected1 = """
                {
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
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
                  - checked: false
                  - default: null
                  - id: 45
                  - key1: value1
                  - numbers1: [1, 2, 3, 4]
                  - numbers3: [3, 4, 5]
                  - setting1: Some value
                  - setting2: 200
                  - setting3: true
                }""";

        expected3 = """
                {
                  + checked: false
                  + default: null
                  + id: 45
                  + key1: value1
                  + numbers1: [1, 2, 3, 4]
                  + numbers3: [3, 4, 5]
                  + setting1: Some value
                  + setting2: 200
                  + setting3: true
                }""";

        expected4 = "{\n}";
        filepath1 = "src/test/resources/file1.json";
        filepath2 = "src/test/resources/file2.json";
        filepath3 = "src/test/resources/file3.json"; // empty file
        filepath4 = "src/test/resources/file1.yml";
        filepath5 = "src/test/resources/file2.yml";
        filepath6 = "src/test/resources/file3.yml"; // empty file
    }

    @Test
    public void consoleCommandTest() {
        String[] args = {filepath1, filepath2};
        int exitCode = new CommandLine(new App()).execute(args);
        assertEquals(0, exitCode);
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
        String expected5 = """
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";

        String expected6 = """
                Property 'checked' was removed
                Property 'default' was removed
                Property 'id' was removed
                Property 'key1' was removed
                Property 'numbers1' was removed
                Property 'numbers3' was removed
                Property 'setting1' was removed
                Property 'setting2' was removed
                Property 'setting3' was removed""";

        String expected7 = """
                Property 'checked' was added with value: false
                Property 'default' was added with value: null
                Property 'id' was added with value: 45
                Property 'key1' was added with value: 'value1'
                Property 'numbers1' was added with value: [complex value]
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
    @Test
    public void generateJsonFormatTest() throws Exception {
        String expected8 = """
                {
                  "checked" : {
                    "state" : "UPDATED",
                    "oldValue" : false,
                    "newValue" : true
                  },
                  "default" : {
                    "state" : "UPDATED",
                    "oldValue" : null,
                    "newValue" : [ "value1", "value2" ]
                  },
                  "id" : {
                    "state" : "UPDATED",
                    "oldValue" : 45,
                    "newValue" : null
                  },
                  "key1" : {
                    "state" : "REMOVED",
                    "oldValue" : "value1"
                  },
                  "key2" : {
                    "state" : "ADDED",
                    "newValue" : "value2"
                  },
                  "numbers1" : {
                    "state" : "SAME",
                    "oldValue" : [ 1, 2, 3, 4 ]
                  },
                  "numbers3" : {
                    "state" : "REMOVED",
                    "oldValue" : [ 3, 4, 5 ]
                  },
                  "numbers4" : {
                    "state" : "ADDED",
                    "newValue" : [ 4, 5, 6 ]
                  },
                  "obj1" : {
                    "state" : "ADDED",
                    "newValue" : {
                      "nestedKey" : "value",
                      "isNested" : true
                    }
                  },
                  "setting1" : {
                    "state" : "UPDATED",
                    "oldValue" : "Some value",
                    "newValue" : "Another value"
                  },
                  "setting2" : {
                    "state" : "UPDATED",
                    "oldValue" : 200,
                    "newValue" : 300
                  },
                  "setting3" : {
                    "state" : "UPDATED",
                    "oldValue" : true,
                    "newValue" : "none"
                  }
                }""";
        String expected9 = """
                {
                  "checked" : {
                    "state" : "REMOVED",
                    "oldValue" : false
                  },
                  "default" : {
                    "state" : "REMOVED",
                    "oldValue" : null
                  },
                  "id" : {
                    "state" : "REMOVED",
                    "oldValue" : 45
                  },
                  "key1" : {
                    "state" : "REMOVED",
                    "oldValue" : "value1"
                  },
                  "numbers1" : {
                    "state" : "REMOVED",
                    "oldValue" : [ 1, 2, 3, 4 ]
                  },
                  "numbers3" : {
                    "state" : "REMOVED",
                    "oldValue" : [ 3, 4, 5 ]
                  },
                  "setting1" : {
                    "state" : "REMOVED",
                    "oldValue" : "Some value"
                  },
                  "setting2" : {
                    "state" : "REMOVED",
                    "oldValue" : 200
                  },
                  "setting3" : {
                    "state" : "REMOVED",
                    "oldValue" : true
                  }
                }""";
        String expected10 = """
                {
                  "checked" : {
                    "state" : "ADDED",
                    "newValue" : false
                  },
                  "default" : {
                    "state" : "ADDED",
                    "newValue" : null
                  },
                  "id" : {
                    "state" : "ADDED",
                    "newValue" : 45
                  },
                  "key1" : {
                    "state" : "ADDED",
                    "newValue" : "value1"
                  },
                  "numbers1" : {
                    "state" : "ADDED",
                    "newValue" : [ 1, 2, 3, 4 ]
                  },
                  "numbers3" : {
                    "state" : "ADDED",
                    "newValue" : [ 3, 4, 5 ]
                  },
                  "setting1" : {
                    "state" : "ADDED",
                    "newValue" : "Some value"
                  },
                  "setting2" : {
                    "state" : "ADDED",
                    "newValue" : 200
                  },
                  "setting3" : {
                    "state" : "ADDED",
                    "newValue" : true
                  }
                }""";

        String actual = Differ.generate(filepath1, filepath2, "json");
        assertEquals(expected8, actual);
        actual = Differ.generate(filepath1, filepath3, "json");
        assertEquals(expected9, actual);
        actual = Differ.generate(filepath3, filepath1, "json");
        assertEquals(expected10, actual);
        actual = Differ.generate(filepath3, filepath3, "json");
        assertEquals("{ }", actual);
    }
}
