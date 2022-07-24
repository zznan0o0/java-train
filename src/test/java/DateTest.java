import common.utils.DateUnit;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
}
