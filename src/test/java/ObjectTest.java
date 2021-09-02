import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ObjectTest {
    @Test
    public void test(){
        Map<String, Object> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", "abc");
        m.put("c", m);
        System.out.println(m);
        Map<String, Object> mm = (Map<String, Object>) m.get("c");
        Map<String, Object> mmm = (Map<String, Object>) mm.get("c");
        System.out.println(mm);
        System.out.println(mmm);
    }
}
