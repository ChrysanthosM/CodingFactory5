package cf5.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Converters {
    public static String getFullName(String firstName, String lastName) {
        return StringUtils.trimToEmpty(firstName).concat(StringUtils.SPACE).concat(StringUtils.trimToEmpty(lastName)).trim();
    }
}
