import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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


    }
}
