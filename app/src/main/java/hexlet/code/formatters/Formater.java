package hexlet.code.formatters;

import hexlet.code.DiffRecord;

import java.util.List;

public interface Formater {
    String format(List<DiffRecord> diff);

    static Formater create(String format) {
        if (format.equals("plain")) {
            return new PlainFormater();
        } else {
            return new StylishFormater();
        }
    }
}
