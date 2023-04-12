package hexlet.code.formatters;

import hexlet.code.DiffRecord;

import java.util.List;
import java.util.Map;

public class PlainFormater implements Formater {
    @Override
    public String format(List<DiffRecord> diff) {
        StringBuilder builder = new StringBuilder();
        for (var record : diff) {
            builder.append(getLine(record));
        }
        return builder.toString().trim();
    }
    private String getLine(DiffRecord record) {
        var key = record.getKey();
        var oldValue = formatValue(record.getOldValue());
        var newValue = formatValue(record.getNewValue());

        switch (record.getState()) {
            case SAME -> {
                return "";
            }
            case NEW -> {
                return String.format("Property '%s' was added with value: %s\n", key, newValue);
            }
            case DELETED -> {
                return String.format("Property '%s' was removed\n", key);
            }
            case UPDATED -> {
                return String.format("Property '%s' was updated. From %s to %s\n", key, oldValue, newValue);
            }
            default -> { }
        }

        return "";
    }
    private static String formatValue(Object value) {
        if (value == null) {
            return "null";
        }
        var isComplex = value instanceof List || value instanceof Map;
        if (isComplex) {
            return "[complex value]";
        }
        if (value instanceof String && value != "null") {
            return "'" + value.toString() + "'";
        }
        return value.toString();
    }

}
