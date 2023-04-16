package hexlet.code;

import hexlet.code.formatters.Formatter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Differ {
    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }
    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        var data1 = Parser.parse(getContent(filepath1), getExtension(filepath1));
        var data2 = Parser.parse(getContent(filepath2), getExtension(filepath2));
        var diffList = DiffBuilder.getDiff(data1, data2);

        return Formatter.format(diffList, format);
    }
    public static String getContent(String filepath) throws Exception {
        Path path = Paths.get(filepath);
        return Files.readString(path);
    }
    public static String getExtension(String filepath) {
        return filepath.endsWith(".yml") ? "YAML" : "JSON";
    }
}
