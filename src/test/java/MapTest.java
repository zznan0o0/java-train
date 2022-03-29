import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class MapTest {
    @Test
    public void test(){
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(1,2);
        System.out.println(map.get(1));
        System.out.println(map.get(2));
        System.out.println(map.get(null));
    }


    @Test
    public void test2(){
        E e1 = new E(1, "n1");
        E e11 = new E(1, "n11");
        E e2 = new E(2, "n2");

        List<E> list = new ArrayList(){{add(e1); add(e11); add(e2);}};
        System.out.println(this.handleMap(list, E::getId));
        System.out.println(this.handleMap(list, E::getId2));
        System.out.println(this.handleMap(list, E::getId3));



//        this.h(e1, E::getName);

    }

    public <T1, T2> Map<T1, List<T2>> handleMap(List<T2> list, Function<T2, T1> fn){
//    public <T1, T2> void handleMap(List<T2> list, Function<T2, T1> fn){
        Map<T1, List<T2>> map = new HashMap<>();
        for(T2 v : list){
            T1 k = fn.apply(v);
            if(map.get(k) == null) map.put(k,  new ArrayList());
            map.get(k).add(v);
        }

        return map;
    }

    public <T1, T2> void h(T2 t2, Function<T2, T1> fn){
        T1 k = fn.apply(t2);
        System.out.println(k);
    }
    @Test
    public void test0(){
        Map<Integer, Integer> map = new HashMap<>();
        map.put(-0, 1);
        map.put(0, 2);
        System.out.println(map);

        Map<Float, Integer> m2 = new HashMap<>();
        m2.put(-0.0F, 1);
        m2.put(0.0F, 2);
        System.out.println(m2);

        Map<Long, Integer> m3 = new HashMap<>();
        m3.put(-0L, 1);
        m3.put(0L, 2);
        System.out.println(m3);

        Map<Double, Integer> m4 = new HashMap<>();
        m4.put(-0.0D, 1);
        m4.put(0.0D, 2);
        System.out.println(m4);
    }
}

@Data
class E{
    E(Integer id, String name){
        this.id = id;
        this.name = name;
    }
    private Integer id;
    private String name;

    public Integer getId2(){
        return this.id;
    }

    public static Integer getId3(E e){
        return e.getId();
    }
}
