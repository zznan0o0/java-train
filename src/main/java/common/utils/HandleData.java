package common.utils;

import com.sun.deploy.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleData <T1, T2, T3>{
    public List<T3> mapDictDict(List<T1> t1List, List<T2> t2List, List<String> ks1, List<String> ks2, MapDictDictFn fn) throws Exception {
        Map<String, T2> t2Map = this.<T2>convertDict(t2List, ks2);
        List<T3> t3List = new ArrayList<>();
        for(T1 t1 : t1List){
            String key = this.convertKey(t1, ks1);
            t3List.add((T3) fn.apply(t1, t2Map.get(key), key));
        }

        return t3List;
    }


    public <T> Map<String, T> convertDict(List<T> tList, List<String> ks) throws Exception {
        Map<String, T> tMap = new HashMap<>();

        for(T v : tList){
            tMap.put(this.convertKey(v, ks), v);
        }

        return tMap;
    }

    public <T> String convertKey(T t, List<String> ks) throws Exception {
        Class clz = t.getClass();
        List<String> list = new ArrayList<>();
        for(String k : ks){
            Field f = clz.getDeclaredField(k);
            f.setAccessible(true);
            Object v = f.get(t);
            if(v == null){
                list.add("");
            }
            else{
                list.add(String.valueOf(v));
            }
        }

        return StringUtils.join(list, ",");
    }




//    public  T1 add(T1 t1, T2 t2) throws Exception {
//        (String) t1.name = (String) t2.name;
//        Class t1Cla = (Class) t1.getClass();
//        Class t2Cla = (Class) t2.getClass();
//        Field t2Name = t2Cla.getDeclaredField("name");
//        t2Name.setAccessible(true);
//        System.out.println();
//        Field[] fs = t1Cla.getDeclaredFields();
//        Field f = fs[1];
//        String fn = f.getName();

//        Field t1Name = t1Cla.getDeclaredField("name");
//        t1Name.setAccessible(true);
//        t1Name.set(t1, t2Name.get(t2));

//        for(int i = 0 ; i < fs.length; i++){
//            System.out.println(fs[i]);
//        }
//        f.setAccessible(true); //设置些属性是可以访问的
//        Object val = f.get(t1);
//        System.out.println(fn);
//        System.out.println(val);
//        f.set(t1, 30);


//        return t1;
        // return new Pair<K>(first, last);
//    }
}