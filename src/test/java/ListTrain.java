import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListTrain {
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
}
