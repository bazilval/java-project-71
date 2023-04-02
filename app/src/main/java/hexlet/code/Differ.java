package hexlet.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Differ {
    public static String generate(String filepath1, String filepath2) throws Exception {
        var data1 = getData(filepath1);
        var data2 = getData(filepath2);
        String diff = getDiff(data1, data2);
        return diff;
    }
    private static Map<String, Object> getData(String filepath) throws Exception {
        Path path = Paths.get(filepath);
        String content = Files.readString(path);

        return parse(content);
    }
    private static Map<String, Object> parse(String content) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        var data = objectMapper.readValue(content, new TypeReference<Map<String, Object>>(){});
        var sortedDate = data.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        sortedDate.put(null, "endPoint");
        return sortedDate;
    }
    private static String getDiff(Map<String, Object> data1, Map<String, Object> data2) {
        StringBuilder builder = new StringBuilder("{\n");
        var iter1 = data1.entrySet().iterator();
        var iter2 = data2.entrySet().iterator();
        boolean hasStep = iter1.hasNext() || iter2.hasNext();
//        Map.Entry<String, Object> entry1 = iter1.hasNext() ? iter1.next() : new AbstractMap.SimpleEntry<String, Object>(null, "");
//        Map.Entry<String, Object> entry2 = iter2.hasNext() ? iter2.next() : new AbstractMap.SimpleEntry<String, Object>(null, "");
        Map.Entry<String, Object> entry1 = iter1.next();
        Map.Entry<String, Object> entry2 = iter2.next();

        while (hasStep){
            hasStep = iter1.hasNext() || iter2.hasNext();
            String key1 = entry1.getKey();
            String key2 = entry2.getKey();
            Object value1 = entry1.getValue();
            Object value2 = entry2.getValue();
            String line = "";
            int compareIndex;

            if (key1 == null) {
                compareIndex = 1;
            } else if (key2 == null) {
                compareIndex = -1;
            } else {
                compareIndex = key1.compareTo(key2);
            }

            if (compareIndex == 0) {
                if (value1.equals(value2)) {
                    line = String.format("    %s: %s\n", key1, value1);
                } else {
                    line = String.format("  - %s: %s\n  + %s: %s\n", key1, value1, key2, value2);
                }
                entry1 = iter1.hasNext() ? iter1.next() : entry1;
                entry2 = iter2.hasNext() ? iter2.next() : entry2;
            } else if (key1 != null && compareIndex < 0) {
                line = String.format("  - %s: %s\n", key1, value1);
                entry1 = iter1.hasNext() ? iter1.next() : entry1;
            } else if (key2 != null) {
                line = String.format("  + %s: %s\n", key2, value2);
                entry2 = iter2.hasNext() ? iter2.next() : entry2;
            }
            builder.append(line);
        }
        return builder.append("}").toString();
    }
}
