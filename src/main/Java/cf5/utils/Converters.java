package cf5.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Converters {
    public static Date convertStringToDate(String dateString) throws ParseException {
        if (StringUtils.isBlank(dateString)) return null;

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = inputFormat.parse(dateString);
        String formattedDateString = outputFormat.format(date);
        return outputFormat.parse(formattedDateString);
    }
    public static String convertDateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(date);
        return formattedDate;
    }

    public static String getFullName(String firstName, String lastName) {
        return StringUtils.trimToEmpty(firstName).concat(StringUtils.SPACE).concat(StringUtils.trimToEmpty(lastName)).trim();
    }
}
