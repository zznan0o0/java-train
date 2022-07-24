import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Test;

import java.beans.PropertyDescriptor;
import java.lang.annotation.*;
import java.lang.reflect.*;
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
        private List<Field> listFields = new ArrayList<>();
        private List<Method> getListFieldMethodList = new ArrayList<>();
        //是否收集完成
        private Boolean flag = false;
    }
    //处理class
    public DigitScheme handleClass(Class<?> clazz) throws NoSuchMethodException {
        DigitScheme digitScheme = new DigitScheme();
        DigitHandleModule digitHandleModule = clazz.getAnnotation(DigitHandleModule.class);


        Set<String> fieldNameSet = new HashSet<>();

        while (clazz != null){
            Field[] fields = clazz.getDeclaredFields();
            for(Field f : fields){
                if(!fieldNameSet.contains(f.getName())){
                    //字类优先级比父级高所以以子级为准
                    fieldNameSet.add(f.getName());
                    String fieldUpName = Character.toUpperCase(f.getName().charAt(0)) + f.getName().substring(1);
                    if(Collection.class.isAssignableFrom(f.getType())){
                        digitScheme.getListFields().add(f);
                        digitScheme.getGetListFieldMethodList().add(clazz.getMethod("get" + fieldUpName));
                    }

                    if (f.isAnnotationPresent(DigitHandleCol.class) && f.getType().isAssignableFrom(BigDecimal.class)){
                        digitScheme.getFieldList().add(f);
                        digitScheme.getGetMethodList().add(clazz.getMethod("get" + fieldUpName));
                        digitScheme.getSetMethodList().add(clazz.getMethod("set" + fieldUpName, BigDecimal.class));
                        DigitHandleCol digitHandleCol = f.getAnnotation(DigitHandleCol.class);
                        //处理获取小数点位数，我这先模拟
                        if(f.isAnnotationPresent(DigitHandleModule.class)){
                            DigitHandleModule digitHandleModuleField = f.getAnnotation(DigitHandleModule.class);
                            digitScheme.getDigitList().add(Integer.parseInt(digitHandleCol.value()) * Integer.parseInt(digitHandleModuleField.value()));
                        }
                        else {
                            digitScheme.getDigitList().add(Integer.parseInt(digitHandleCol.value()) * Integer.parseInt(digitHandleModule.value()));
                        }
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        digitScheme.setFlag(true);
        return digitScheme;
    }

    //处理小数点
    public void handleDigit(Object obj, DigitScheme digitScheme) throws IllegalAccessException, InvocationTargetException {
        for(int i = 0; i < digitScheme.getFieldList().size(); i++){
            BigDecimal val = (BigDecimal) digitScheme.getMethodList.get(i).invoke(obj);
            val = val.setScale(digitScheme.getDigitList().get(i), RoundingMode.HALF_UP);
            digitScheme.getSetMethodList().get(i).invoke(obj, val);
        }
    }
    //处理obj
    public void handleObj(Object obj, Map<String, DigitScheme> digitSchemeMap) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Class<?> clazz = obj.getClass();
        if(clazz.isAnnotationPresent(DigitHandleModule.class)){
            DigitScheme digitScheme = digitSchemeMap.get(clazz.getName());

            if(digitScheme == null){
                digitScheme = handleClass(clazz);
                digitSchemeMap.put(clazz.getName(), digitScheme);
            }


            handleDigit(obj, digitScheme);
            handleCollectionField(obj, digitSchemeMap, clazz.getName());
        }
    }
    //处理集合
    public void handleCollection(Object obj, Map<String, DigitScheme> digitSchemeMap) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(obj instanceof Collection){
            Class<?> c = obj.getClass();
            Type t = obj.getClass().getGenericSuperclass();
            ParameterizedType pt = (ParameterizedType) t;
            Type[] types = pt.getActualTypeArguments();

            if(types.length < 1) return;

            Class<?> clazz = (Class<?>) types[0];
            if(clazz.isAnnotationPresent(DigitHandleModule.class)){
                DigitScheme digitScheme = digitSchemeMap.get(clazz.getName());

                if(digitScheme == null){
                    digitScheme = handleClass(clazz);
                    digitSchemeMap.put(clazz.getName(), digitScheme);
                }

                Collection<Object> objects = (Collection<Object>) obj;
                for(Object object : objects){
                    handleDigit(object, digitScheme);
                    handleCollectionField(object, digitSchemeMap, clazz.getName());
                }
            }
        }
    }
    //处理集合字段
    public void handleCollectionField(Object object, Map<String, DigitScheme> digitSchemeMap, String className) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        DigitScheme digitScheme = digitSchemeMap.get(className);
        if(digitScheme.getGetListFieldMethodList().size() > 0){
            for(int i = 0; i < digitScheme.getGetListFieldMethodList().size(); i++){
                Object fieldObjs = digitScheme.getGetListFieldMethodList().get(i).invoke(object);
                if(fieldObjs != null){
                    handleCollection(fieldObjs, digitSchemeMap);
                }
            }
        }
    }

    //处理注解
    public void handleDigitAnnotation(Object obj) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if(obj == null) return;

        Map<String, DigitScheme> digitSchemeMap = new HashMap<>();
        if(obj instanceof Collection){
            //处理list
            handleCollection(obj, digitSchemeMap);
        }
        else {
            handleObj(obj, digitSchemeMap);
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

        private List<NCD> list;
    }

    @Data
    @DigitHandleModule("1")
    public static class NCD{
        @DigitHandleCol("1")
        private BigDecimal ncd1;
        @DigitHandleCol("2")
        private BigDecimal ncd2;
    }
    @Test
    public void testHandleDigit() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        NC n = new NC();
        BigDecimal initVal = new BigDecimal("1.11111111111");
        n.setN1(initVal);
        n.setN2(initVal);
        n.setN3(initVal);
        n.setList(new ArrayList<NCD>(){{
            add(new NCD(){{
                setNcd1(initVal);
                setNcd2(initVal);
            }});
            add(new NCD(){{
                setNcd1(initVal.add(BigDecimal.ONE));
                setNcd2(initVal);
            }});
        }});
        handleDigitAnnotation(n);
//        handleDigitAnnotation(null);
//        handleDigitAnnotation(n.getList());
//        System.out.println(n.getList());
        System.out.println(n.getN1());
        System.out.println(n.getN2());
        System.out.println(n.getN3());
        System.out.println(n.getList());
    }


}
