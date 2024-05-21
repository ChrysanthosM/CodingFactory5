package cf5.db.loader;

import java.util.List;
import java.util.Map;

public abstract non-sealed class RowLoader<T> implements IRowLoader<T> {
    public static <T> T extractValue(List<Map.Entry<String, Object>> columnNamesValues, String key, Class<T> type) {
        return columnNamesValues.stream()
                .filter(f -> f.getKey().equals(key))
                .map(Map.Entry::getValue)
                .map(type::cast)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Key not found: " + key));
    }
}
