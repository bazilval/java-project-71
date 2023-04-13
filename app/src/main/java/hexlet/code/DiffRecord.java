package hexlet.code;

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

    public static DiffRecord create(String key, Object oldValue, Object newValue) {
        if (oldValue.equals(newValue)) {
            return createSame(key, oldValue);
        } else {
            return createUpdated(key, oldValue, newValue);
        }
    }
    public static DiffRecord createRemoved(String key, Object oldValue) {
        return new DiffRecord(key, oldValue, null, State.REMOVED);
    }
    public static DiffRecord createUpdated(String key, Object oldValue, Object newValue) {
        return new DiffRecord(key, oldValue, newValue, State.UPDATED);
    }
    public static DiffRecord createAdded(String key, Object newValue) {
        return new DiffRecord(key, null, newValue, State.ADDED);
    }
    public static DiffRecord createSame(String key, Object oldValue) {
        return new DiffRecord(key, oldValue, null, State.SAME);
    }
    public DiffRecord(String key, Object oldValue, Object newValue, State state) {
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
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
