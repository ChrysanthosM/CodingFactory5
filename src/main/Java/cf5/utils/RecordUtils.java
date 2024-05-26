package cf5.utils;

import com.google.common.collect.Lists;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.List;

public final class RecordUtils {
    public static <T> List<Object> getRecordValues(T record) throws InvocationTargetException, IllegalAccessException {
        List<Object> values = Lists.newArrayList();
        RecordComponent[] components = record.getClass().getRecordComponents();
        for (RecordComponent component : components) {
            values.add(component.getAccessor().invoke(record));
        }
        return values;
    }
}
