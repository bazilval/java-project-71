package hexlet.code;


import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.stream.Collectors;

public class DiffBuilder {
    public static List<DiffRecord> getDiff(Map<String, Optional> data1, Map<String, Optional> data2) {
        var records = new LinkedList<DiffRecord>();
        LinkedHashSet<String> keySet = new LinkedHashSet<>();
        keySet.addAll(data1.keySet());
        keySet.addAll(data2.keySet());
        var sortedKeySet = keySet.stream()
                .sorted(String::compareTo)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        for (var key : sortedKeySet) {
            DiffRecord record;
            if (data1.containsKey(key) && data2.containsKey(key)) {
                record = DiffRecord.create(key, data1.get(key), data2.get(key));
            } else if (data1.containsKey(key)) {
                record = DiffRecord.createRemoved(key, data1.get(key));
            } else {
                record = DiffRecord.createAdded(key, data2.get(key));
            }
            records.add(record);
        }
        return records;
    }
}
