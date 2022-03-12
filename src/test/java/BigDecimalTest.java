import org.junit.Test;

import java.math.BigDecimal;

public class BigDecimalTest {
    @Test
    public void testM(){
        BigDecimal w = new BigDecimal(1000), h = new BigDecimal(1000);

        BigDecimal area = w.multiply(h).multiply(BigDecimal.valueOf(10));
        System.out.println(area);
    }
    @Test
    public void testStr(){
        BigDecimal a = new BigDecimal("1.0000");
        BigDecimal b = new BigDecimal("1.000");
        System.out.println(String.valueOf(a));
        System.out.println(String.valueOf(b));
    }
}
