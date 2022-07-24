import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Test;

import java.beans.PropertyDescriptor;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class NumberTest {
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface DigitHandleCol {
        String value();
    }
    @Target({ElementType.TYPE, ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface DigitHandleModule{
        String value();
    }

    @Data
    public static class DigitScheme{
        private List<Method> getMethodList = new ArrayList<>();
        private List<Method> setMethodList = new ArrayList<>();
        private List<Field> fieldList = new ArrayList<>();
        private List<Integer> digitList = new ArrayList<>();
        //是否收集完成
        private Boolean flag = false;
    }


    public void handleDigit(Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, DigitScheme> digitSchemeMap = new HashMap<>();

        Class<?> clazz = obj.getClass();
        if(clazz.isAnnotationPresent(DigitHandleModule.class)){
            DigitHandleModule digitHandleModule = clazz.getAnnotation(DigitHandleModule.class);
            DigitScheme digitScheme = new DigitScheme();
            digitSchemeMap.put(clazz.getName(), digitScheme);
            List<Field> listFields = new ArrayList<>();

            List<Field> fieldList = new ArrayList<>();
            Set<String> fieldNameSet = new HashSet<>();

            while (clazz != null){
                Field[] fields = clazz.getDeclaredFields();
                for(Field f : fields){
                    if(!fieldNameSet.contains(f.getName())){
                        //字类优先级比父级高所以以子级为准
                        fieldNameSet.add(f.getName());
                        if(Collection.class.isAssignableFrom(f.getType())){
                            listFields.add(f);
                        }

                        if (f.isAnnotationPresent(DigitHandleCol.class) && f.getType().isAssignableFrom(BigDecimal.class)){
                            digitScheme.getFieldList().add(f);
                            String fieldUpName = Character.toUpperCase(f.getName().charAt(0)) + f.getName().substring(1);
                            digitScheme.getGetMethodList().add(clazz.getMethod("get" + fieldUpName));
                            digitScheme.getSetMethodList().add(clazz.getMethod("set" + fieldUpName, BigDecimal.class));
                            DigitHandleCol digitHandleCol = f.getAnnotation(DigitHandleCol.class);
                            if(f.isAnnotationPresent(DigitHandleModule.class)){
                                DigitHandleModule digitHandleModuleField = f.getAnnotation(DigitHandleModule.class);
                            }

                            //处理获取小数点位数，我这先模拟
                            digitScheme.getDigitList().add(Integer.valueOf(digitHandleCol.value()));

                        }
                    }
                }
                clazz = clazz.getSuperclass();
            }
            digitScheme.setFlag(true);


            for(int i = 0; i < digitScheme.getFieldList().size(); i++){
                BigDecimal val = (BigDecimal) digitScheme.getMethodList.get(i).invoke(obj);
                val = val.setScale(digitScheme.getDigitList().get(i), RoundingMode.HALF_UP);
                digitScheme.getSetMethodList().get(i).invoke(obj, val);
            }
        }




    }
    @Data
    public static class N {
        @DigitHandleCol("1")
        private BigDecimal n1;
        @DigitHandleCol("1")
        private BigDecimal n2;

        public void aa(){
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @DigitHandleModule("1")
    @Data
    @ToString
    public static class NC extends N{
        @DigitHandleCol("2")
        private BigDecimal n2;
        @DigitHandleCol("3")
        @DigitHandleModule("2")
        private BigDecimal n3;
    }
    @Test
    public void testHandleDigit() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        NC n = new NC();
        BigDecimal initVal = new BigDecimal("1.11111111111");
        n.setN1(initVal);
        n.setN2(initVal);
        n.setN3(initVal);
        this.handleDigit(n);
        System.out.println(n.getN1());
        System.out.println(n.getN2());
        System.out.println(n.getN3());
    }


}
