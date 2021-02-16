package services;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DBDateHelper {
    private static String DB_DATE_FORMAT = "yyyy-MM-dd";

    public static Date stringToDate(String stringDate) throws ParseException {
        java.util.Date d =  new SimpleDateFormat(DB_DATE_FORMAT).parse(stringDate);
        return new java.sql.Date(d.getTime());
    }
}
