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

    @Data
    static class Parent{
        private String name;
    }

    @Data
    static class Child extends Parent{
        private Integer age;
    }

    @Test
    public void testChild() throws NoSuchFieldException, IllegalAccessException {
        Child child = new Child();
        child.setName("aaa");
        child.setAge(10);

        Class<?> clzChild = child.getClass();
        Field f1 = clzChild.getDeclaredField("age");//对应字段
        System.out.println(clzChild.getDeclaredField("name"));
        Field[] fields=clzChild.getDeclaredFields();
        for(Field f : fields){
            System.out.println(f.getName());
            f.setAccessible(true);
            f.get(child);
        }
        f1.setAccessible(true);//去掉限制
        System.out.println(f1.get(child));
        f1.set(child, f1.get(child));
    }


}

class TestObj{
    public String t1;
    public Integer t2;
}
