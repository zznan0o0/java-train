package common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUnit {
    static public Date addDay(Date date, Integer amount){
        Calendar calendar   =   new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,amount);
        return calendar.getTime();
    }
}
