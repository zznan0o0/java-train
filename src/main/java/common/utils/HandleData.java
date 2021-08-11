package common.utils;

import java.lang.reflect.Field;

public class HandleData<T1, T2>{
    public  T1 add(T1 t1, T2 t2) throws Exception {
//        (String) t1.name = (String) t2.name;
        Class t1Cla = (Class) t1.getClass();
        Class t2Cla = (Class) t2.getClass();
        Field t2Name = t2Cla.getDeclaredField("name");
        t2Name.setAccessible(true);
//        System.out.println();
        Field[] fs = t1Cla.getDeclaredFields();
        Field f = fs[1];
        String fn = f.getName();

        Field t1Name = t1Cla.getDeclaredField("name");
        t1Name.setAccessible(true);
        t1Name.set(t1, t2Name.get(t2));

//        for(int i = 0 ; i < fs.length; i++){
//            System.out.println(fs[i]);
//        }
        f.setAccessible(true); //设置些属性是可以访问的
        Object val = f.get(t1);
        System.out.println(fn);
        System.out.println(val);
        f.set(t1, 30);


        return t1;
        // return new Pair<K>(first, last);
    }
}