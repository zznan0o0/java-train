import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class StaticTest {
    @Test
    public void testStatic1(){
        StaticMap s1 = new StaticMap();
        StaticMap s2 = new StaticMap();
        s1.m.put("1", "1");
        System.out.println(s2.m);

    }
}
