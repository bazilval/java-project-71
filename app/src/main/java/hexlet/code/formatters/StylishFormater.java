package hexlet.code.formatters;

import hexlet.code.DiffRecord;

import java.util.List;
public final class StylishFormater implements Formater {
    @Override
    public String format(List<DiffRecord> diff) {
        String beginning = "{\n";
        String ending = "}";
        StringBuilder builder = new StringBuilder(beginning);
        for (var record : diff) {
            builder.append(getLine(record));
        }
        return builder + ending;
    }

    private String getLine(DiffRecord record) {
        var key = record.getKey();
        var oldValue = record.getOldValue();
        var newValue = record.getNewValue();

        switch (record.getState()) {
            case SAME -> {
                return String.format("    %s: %s\n", key, oldValue);
            }
            case ADDED -> {
                return String.format("  + %s: %s\n", key, newValue);
            }
            case REMOVED -> {
                return String.format("  - %s: %s\n", key, oldValue);
            }
            case UPDATED -> {
                return String.format("  - %s: %s\n  + %s: %s\n", key, oldValue, key, newValue);
            }
            default -> { }
        }

        return "";
    }
}
