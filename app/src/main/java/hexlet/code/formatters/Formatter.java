package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.DiffRecord;

import java.util.List;

public interface Formatter {
    String format(List<DiffRecord> diff) throws JsonProcessingException;
    static String format(List<DiffRecord> diffList, String format) throws JsonProcessingException {
        if (format.equals("plain")) {
            return new PlainFormatter().format(diffList);
        } else if (format.equals("json")) {
            return new JsonFormatter().format(diffList);
        } else {
            return new StylishFormatter().format(diffList);
        }
    }
}
