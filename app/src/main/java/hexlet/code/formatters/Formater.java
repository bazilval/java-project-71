package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.DiffRecord;

import java.util.List;

public interface Formater {
    String format(List<DiffRecord> diff) throws JsonProcessingException;

    static Formater create(String format) {
        if (format.equals("plain")) {
            return new PlainFormater();
        } else if (format.equals("json")) {
            return new JsonFormater();
        } else {
            return new StylishFormater();
        }
    }
}
