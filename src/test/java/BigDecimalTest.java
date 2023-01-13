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
    @Test
    public void testPls(){
        BigDecimal w = new BigDecimal("1.00");
        BigDecimal h = new BigDecimal("2.000");
        System.out.println(w.toString() + "*" + h);
    }
    @Test
    public void testCN(){
        System.out.println(BigDecimal.ZERO.compareTo(null));
    }
    @Test
    public void testTen(){
    }
    @Test
    public void testNegate(){
        BigDecimal a = BigDecimal.ZERO;
        BigDecimal b = BigDecimal.ONE;
        System.out.println(a.negate());
        System.out.println(b.negate());
        System.out.println(b.negate().negate());
    }
}
