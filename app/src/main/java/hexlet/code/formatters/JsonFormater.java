package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import hexlet.code.DiffRecord;
import hexlet.code.RecordsListWrapper;

import java.io.IOException;
import java.util.List;

public final class JsonFormater implements Formater {
    @Override
    public String format(List<DiffRecord> diff) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        SimpleModule module =
                new SimpleModule("CustomCDiffSerializer", new Version(1, 0, 0, null, null, null));
        module.addSerializer(DiffRecord.class, new CustomDiffSerializer());
        module.addSerializer(RecordsListWrapper.class, new CustomListSerializer());
        mapper.registerModule(module);
        var records = new RecordsListWrapper(diff);
        return mapper.writeValueAsString(records);
    }
}

class CustomDiffSerializer extends StdSerializer<DiffRecord> {
    CustomDiffSerializer() {
        this(null);
    }

    CustomDiffSerializer(Class<DiffRecord> t) {
        super(t);
    }
    @Override
    public void serialize(DiffRecord value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        var stateName = value.getState().name();
        Object oldValue = value.getOldValue() == "null" || value.getOldValue() == null ? null : value.getOldValue();
        Object newValue = value.getNewValue() == "null" || value.getNewValue() == null ? null : value.getNewValue();

        gen.writeStartObject();
        gen.writeStringField("state", stateName);
        if (stateName.equals("ADDED")) {
            gen.writeObjectField("newValue", newValue);
        } else if (stateName.equals("UPDATED")) {
            gen.writeObjectField("oldValue", oldValue);
            gen.writeObjectField("newValue", newValue);
        } else {
            gen.writeObjectField("oldValue", oldValue);
        }
        gen.writeEndObject();
    }
}

class CustomListSerializer extends StdSerializer<RecordsListWrapper> {
    CustomListSerializer() {
        this(null);
    }
    CustomListSerializer(Class<RecordsListWrapper> t) {
        super(t);
    }
    @Override
    public void serialize(RecordsListWrapper value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        for (var record : value.getRecords()) {
            gen.writeObjectField(record.getKey(), record);
        }
        gen.writeEndObject();
    }
}
