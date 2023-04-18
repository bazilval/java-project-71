package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Parser {
    public static Map<String, Optional> parse(String content, String extension) throws Exception {
        ObjectMapper objectMapper;

        if (extension.equalsIgnoreCase("yml")) {
            objectMapper = new YAMLMapper();
        } else {
            objectMapper = new JsonMapper();
        }
        var data = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {
        });

        if (data == null) {
            return new HashMap<>();
        } else {
            return data.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            e -> Optional.ofNullable(e.getValue()),
                            (oldValue, newValue) -> oldValue,
                            HashMap::new));
        }
    }
}
