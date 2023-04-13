package hexlet.code;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class RecordsListWrapper {
    @JsonProperty("diff")
    private final List<DiffRecord> records;
    public RecordsListWrapper(List<DiffRecord> records) {
        this.records = records;
    }
    public List<DiffRecord> getRecords() {
        return records;
    }
}
