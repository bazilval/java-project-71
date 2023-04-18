package hexlet.code;

import java.util.Optional;

public final class DiffRecord {
    public enum State {
        REMOVED,
        ADDED,
        SAME,
        UPDATED
    }
    private final String key;
    private final Object oldValue;
    private final Object newValue;
    private final State state;

    public static DiffRecord createRemoved(String key, Optional oldValue) {
        return new DiffRecord(key, oldValue, Optional.ofNullable(null), State.REMOVED);
    }
    public static DiffRecord createUpdated(String key, Optional oldValue, Optional newValue) {
        return new DiffRecord(key, oldValue, newValue, State.UPDATED);
    }
    public static DiffRecord createAdded(String key, Optional newValue) {
        return new DiffRecord(key, Optional.ofNullable(null), newValue, State.ADDED);
    }
    public static DiffRecord createSame(String key, Optional oldValue) {
        return new DiffRecord(key, oldValue, Optional.ofNullable(null), State.SAME);
    }
    public DiffRecord(String key, Optional oldValue, Optional newValue, State state) {
        this.key = key;
        this.oldValue = oldValue.orElse(null);
        this.newValue = newValue.orElse(null);
        this.state = state;
    }
    public String getKey() {
        return key;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public State getState() {
        return state;
    }
}
