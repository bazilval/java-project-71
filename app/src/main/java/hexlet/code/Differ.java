package hexlet.code;

import hexlet.code.formatters.Formater;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class Differ {
    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }
    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        Formater formater = Formater.create(format);

        var data1 = Parser.getData(filepath1);
        var data2 = Parser.getData(filepath2);
        var diff = getDiff(data1, data2);

        return formater.format(diff);
    }
    private static List<DiffRecord> getDiff(Map<String, Object> data1, Map<String, Object> data2) {
        var records = new LinkedList<DiffRecord>();
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
            DiffRecord record = null;
            int compareIndex = compareKeys(key1, key2);

            if (compareIndex == 0) {
                record = DiffRecord.create(key1, value1, value2);
                entry1 = iterator1.hasNext() ? iterator1.next() : entry1;
                entry2 = iterator2.hasNext() ? iterator2.next() : entry2;
            } else if (key1 != null && compareIndex < 0) {
                record = DiffRecord.createRemoved(key1, value1);
                entry1 = iterator1.hasNext() ? iterator1.next() : entry1;
            } else if (key2 != null) {
                record = DiffRecord.createAdded(key2, value2);
                entry2 = iterator2.hasNext() ? iterator2.next() : entry2;
            }
            if (record != null) {
                records.add(record);
            }
        }
        return records;
    }
    private static int compareKeys(String key1, String key2) {
        int compareIndex;
        if (key1 == null) {
            compareIndex = 1;
        } else if (key2 == null) {
            compareIndex = -1;
        } else {
            compareIndex = key1.compareTo(key2);
        }
        return compareIndex;
    }
}
