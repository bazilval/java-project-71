package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {
    enum Extension {
        JSON,
        YAML
    }
    public static Map<String, Object> getData(String filepath) throws Exception {
        Path path = Paths.get(filepath);
        String content = Files.readString(path);
        Extension extension = filepath.endsWith(".yml") ? Extension.YAML : Extension.JSON;

        return parse(content, extension);
    }
    private static Map<String, Object> parse(String content, Extension extension) throws Exception {
        ObjectMapper objectMapper;

        if (extension == Extension.YAML) {
            objectMapper = new YAMLMapper();
        } else {
            objectMapper = new JsonMapper();
        }
        var data = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() { });

        Map<String, Object> sortedData;
        if (data == null) {
            sortedData =  new LinkedHashMap<>() { };
        } else {
            sortedData = data.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            e -> e.getValue() != null ? e.getValue() : "null",
                            (oldValue, newValue) -> oldValue,
                            LinkedHashMap::new));
        }
        sortedData.put(null, "endPoint");
        return sortedData;
    }
}
