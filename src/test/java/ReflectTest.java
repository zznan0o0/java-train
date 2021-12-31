import lombok.Data;
import org.junit.Test;

import java.lang.reflect.Field;

public class ReflectTest {
    @Test
    public void changeType() throws NoSuchFieldException, IllegalAccessException {
        TestObj testObj = new TestObj();
        testObj.t1 = "1";
        testObj.t2 = 2;

        Class testObjCla = testObj.getClass();
        Field fT1 = testObjCla.getDeclaredField("t1");
        Field fT2 = testObjCla.getDeclaredField("t2");
        System.out.println(fT1.get(testObj));
        System.out.println(fT2.get(testObj));
        //无法修改类型
        fT1.set(testObj, 1);
//        fT2.set(testObj, "2");

        System.out.println(testObj);
    }

}

class TestObj{
    public String t1;
    public Integer t2;
}
