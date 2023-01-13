import lombok.Data;
import org.junit.Test;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.invoke.MethodType;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

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
    static class Parent {
        private String name;
    }

    @Data
    static class Child extends Parent {
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
        Field[] fields = clzChild.getDeclaredFields();
        for (Field f : fields) {
            System.out.println(f.getName());
            f.setAccessible(true);
            f.get(child);
        }
        f1.setAccessible(true);//去掉限制
        System.out.println(f1.get(child));
        f1.set(child, f1.get(child));
    }

    @Test
    public void testMethod2Function() throws IntrospectionException {
        PropertyDescriptor propertyDescriptor = new PropertyDescriptor("t1", TestObj.class);
        Method getMethod = propertyDescriptor.getReadMethod();
        System.out.println(getMethod);

//        System.out.println(TypeFunction.getLambdaColumnName(TestObj::getT1));
    }



}
@Data
class TestObj{
    public String t1;
    public Integer t2;
}



@FunctionalInterface
interface TypeFunction<T, R> extends Serializable, Function<T, R> {

    /**
     * 获取列名称
     * @param lambda
     * @return String
     */
    static String getLambdaColumnName(Serializable lambda) {
        try {
            Method method = lambda.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(lambda);
            String getter = serializedLambda.getImplMethodName();
            String fieldName = Introspector.decapitalize(getter.replace("get", ""));
            return fieldName;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
