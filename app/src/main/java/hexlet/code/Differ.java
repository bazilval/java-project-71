package hexlet.code;

import java.util.Map;

public class Differ {
    public static String generate(String filepath1, String filepath2) throws Exception {
        var data1 = Parser.getData(filepath1);
        var data2 = Parser.getData(filepath2);
        return getDiff(data1, data2);
    }

    private static String getDiff(Map<String, Object> data1, Map<String, Object> data2) {
        StringBuilder builder = new StringBuilder("{\n");
        var iterator1 = data1.entrySet().iterator();
        var iterator2 = data2.entrySet().iterator();
        boolean hasStep = iterator1.hasNext() || iterator2.hasNext();
        Map.Entry<String, Object> entry1 = iterator1.next();
        Map.Entry<String, Object> entry2 = iterator2.next();

        while (hasStep) {
            hasStep = iterator1.hasNext() || iterator2.hasNext();
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
                entry1 = iterator1.hasNext() ? iterator1.next() : entry1;
                entry2 = iterator2.hasNext() ? iterator2.next() : entry2;
            } else if (key1 != null && compareIndex < 0) {
                line = String.format("  - %s: %s\n", key1, value1);
                entry1 = iterator1.hasNext() ? iterator1.next() : entry1;
            } else if (key2 != null) {
                line = String.format("  + %s: %s\n", key2, value2);
                entry2 = iterator2.hasNext() ? iterator2.next() : entry2;
            }
            builder.append(line);
        }
        return builder.append("}").toString();
    }
}
