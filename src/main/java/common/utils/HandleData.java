package common.utils;

import com.sun.deploy.util.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleData {

    public <T1, T2> List<T1> setListProps(List<T1> t1List, List<T2> t2List, List<String> ks1, List<String> ks2, Map<String, String> props) throws Exception {
        return this.mapDictDict(t1List, t2List, ks1, ks2,  (T1 x1, T2 x2, String k) -> {
            Class<?> clzX1 = x1.getClass();
            Class<?> clzX2 = x2.getClass();

            for(String propsKey : props.keySet()){
                Field f1 = clzX1.getDeclaredField(propsKey);
                f1.setAccessible(true);
                Field f2 = clzX2.getDeclaredField(props.get(propsKey));
                f2.setAccessible(true);
                f1.set(x1, f2.get(x2));
            }
            return x1;
        });
    }

    public <T1, T2, T3> List<T3> mapDictDict(List<T1> t1List, List<T2> t2List, List<String> ks1, List<String> ks2, MapDictDictFn<T1,T2,T3> fn) throws Exception {
        Map<String, T2> t2Map = this.convertDict(t2List, ks2);
        List<T3> t3List = new ArrayList<>();
        for(T1 t1 : t1List){
            String key = this.convertKey(t1, ks1);
            t3List.add(fn.apply(t1, t2Map.get(key), key));
        }

        return t3List;
    }

    public <T1, T2> List<T1> mapDictDict(List<T1> t1List, List<T2> t2List, List<String> ks1, List<String> ks2, MapDictDitc2TFn<T1,T2> fn) throws Exception {
        Map<String, T2> t2Map = this.convertDict(t2List, ks2);
        List<T1> resultList = new ArrayList<>();
        for(T1 t1 : t1List){
            String key = this.convertKey(t1, ks1);
            resultList.add(fn.apply(t1, t2Map.get(key), key));
        }

        return resultList;
    }


    public <T> Map<String, T> convertDict(List<T> tList, List<String> ks) throws Exception {
        Map<String, T> tMap = new HashMap<>();

        for(T v : tList){
            tMap.put(this.convertKey(v, ks), v);
        }

        return tMap;
    }

    public <T> String convertKey(T t, List<String> ks) throws Exception {
        Class<?> clz = t.getClass();
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
}