import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class ListTrain {
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
}
