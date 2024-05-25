package cf5.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Converters {
    public static Date convertStringToDate(String dateString) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = inputFormat.parse(dateString);
        String formattedDateString = outputFormat.format(date);
        return outputFormat.parse(formattedDateString);
    }
}
