package hexlet.code;

import java.util.List;
public class StylishFormater implements Formater {
    @Override
    public String format(List<DiffRecord> diff) {
        StringBuilder builder = new StringBuilder("{\n");
        for (var record : diff) {
            builder.append(getLine(record));
        }
        return builder.toString() + "}";
    }

    private String getLine(DiffRecord record) {
        var key = record.getKey();
        var oldValue = record.getOldValue();
        var newValue = record.getNewValue();

        switch (record.getState()) {
            case SAME -> {
                return String.format("    %s: %s\n", key, oldValue);
            }
            case NEW -> {
                return String.format("  + %s: %s\n", key, newValue);
            }
            case DELETED -> {
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
