import common.utils.DateUnit;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        System.out.println(date);
    }
}
