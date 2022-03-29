import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListTrain {
    @Test
    public void testSort(){
        List<Integer> list = new ArrayList<Integer>(){{
            add(2);
            add(5);
            add(1);
            add(10);
            add(8);
            add(7);
        }};

        List<Integer> list1 = list.stream().sorted(Comparator.comparing(x -> x)).collect(Collectors.toList());
        System.out.println(list);
        System.out.println(list1);

    }
    @Test
    public void testAsList(){
        String[] a = new String[]{"1","2"};
        System.out.println(Arrays.asList("1"));
        System.out.println(Arrays.asList(a));
    }
    @Test
    public void testSetList(){
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1,1);
        map.put(2,2);
        map.put(3,3);
        System.out.println(map.size());
        List<Integer> list = Arrays.asList(new Integer[map.size()]);
        System.out.println(list);
        list.set(0, 1);
        System.out.println(list);
    }
    @Test
    public void listTrain(){
        List<Integer> list1 = new ArrayList<Integer>(){{
            add(1);
            add(2);
        }};

        List<Integer> list2 = new ArrayList<Integer>(){{
            add(2);
            add(3);
        }};

        List<Integer> delList = new ArrayList<>(list1);
        delList.removeAll(list2);
        System.out.println(delList);
        System.out.println(list1);

        Map<Integer, Integer> m = list1.stream().collect(Collectors.toMap(x -> null, x -> x, (x1, x2) -> x1));

        System.out.println(m);



    }

    @Test
    public void test(){
        List<String> l = null;
        System.out.println(l.size());
    }

    @Test
    public void test2(){
        List<String> l = new ArrayList(){{add("aaa");}};
        add(l);
        System.out.println(l);
    }

    private void add(List<String> l){
        l.add("bbb");
    }

    @Test
    public void loop(){
        List<Integer> list = new ArrayList<Integer>(){{
            add(1);
            add(2);
            add(3);
        }};

        for(Integer i : list){
            System.out.println(i);
            if(i.equals(2)) return;
        }
    }

    static public <T> Map<List<Object>, T> list2MapByKeys(List<T> list, List<Function<T, Object>> fns){
        Map<List<Object>, T> map = new HashMap<>();

        for(T v : list){
            List<Object> keys = new ArrayList<>();
            for(Function<T, Object> fn : fns){
                keys.add(fn.apply(v));
            }
            map.put(keys, v);
        }

        return map;
    }

    @Data
    static public class A{
        private Integer id;
        private String name;
        private Integer age;

        public A(Integer id, String name, Integer age){
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

    @Test
    public void testL2m(){
        List<A> aList = new ArrayList<A>(){{
            add(new A(1, "A1", 10));
            add(new A(2, "A2", 10));
            add(new A(3, "A1", 11));
            add(new A(4, "A1", 10));
        }};

        Map<List<Object>, A> map = list2MapByKeys(aList, new ArrayList<Function<A, Object>>(){{add(A::getName);add(A::getAge);}});
        System.out.println(map);
    }



}
