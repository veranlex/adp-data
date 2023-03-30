package my.own.adp.data.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String convertTimestampToDate(long timeStamp) {
        Date date = new Date(timeStamp);
        DateFormat formatedDate = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return formatedDate.format(date);
    }

}
