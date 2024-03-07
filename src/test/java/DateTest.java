import common.utils.DateUnit;
import org.joda.time.DateTime;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

public class DateTest {
    @Test
    public void test() throws ParseException {
        Date date = new Date();
        System.out.println(date);
        date = DateUnit.addDay(date, 1);
        System.out.println(date);

        System.out.println(date.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String s = sdf.format(date);
        Date date2 =  sdf.parse(s);
        System.out.println(date2);
    }

    @Test
    public void test2() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse("2005-12-15");
        System.out.println(date.toString());
        System.out.println(date);
    }
    @Test
    public void yymmdd(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
        Date date = new Date();
        System.out.println(simpleDateFormat.format(date));
    }
    @Test
    public void testLocalDate(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd");

        LocalDate now = LocalDate.now();
        System.out.println(now.format(dateTimeFormatter));
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        for(int i = 6; i >= 0; i--){
            map.put(now.minusDays(i).format(dateTimeFormatter), i);
        }
        System.out.println(map);
    }

    public static Date getEndOfDay(Date date) {
        if(date == null) return null;
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(date);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        calendarEnd.set(Calendar.SECOND, 59);

        //防止mysql自动加一秒,毫秒设为0
        calendarEnd.set(Calendar.MILLISECOND, 0);
        return calendarEnd.getTime();
    }
    @Test
    public void testEnd(){
        System.out.println(getEndOfDay(new Date()));
    }
    @Test
    public void test157(){
        Date d = new Date(157L);
        System.out.println(d.getYear());
        System.out.println(d.getTime());
        System.out.println(new Date(0, Calendar.JANUARY, 1).getTime() );

        System.out.println( new DateTime(0));
    }
}
