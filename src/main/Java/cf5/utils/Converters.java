package cf5.utils;

import org.apache.commons.lang3.StringUtils;

public final class Converters {
    public static String getFullName(String firstName, String lastName) {
        return StringUtils.trimToEmpty(firstName).concat(StringUtils.SPACE).concat(StringUtils.trimToEmpty(lastName)).trim();
    }
}
