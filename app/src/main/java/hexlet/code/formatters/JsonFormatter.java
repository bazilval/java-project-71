package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import hexlet.code.DiffRecord;

import java.util.List;

public final class JsonFormatter implements Formatter {
    @Override
    public String format(List<DiffRecord> diff) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        return mapper.writeValueAsString(diff);
    }
}
