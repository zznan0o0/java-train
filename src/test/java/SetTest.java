import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SetTest {
    @Test
    public void test1(){
        Set s1 = new HashSet(){{
            add(1);
            add(2);
            add(3);
            add(3);
        }};
        System.out.println(s1);
        Set s2 = new HashSet(){{
            add(3);
            add(1);
            add(2);
        }};

        List<Integer> l1 = new ArrayList<Integer>(){{
            add(1);
            add(2);
            add(3);
            add(3);
        }};
        List<Integer> l2 = new ArrayList<Integer>(){{
            add(3);
            add(1);
            add(2);
        }};

        Set<Integer> s3 = l1.stream().collect(Collectors.toSet());
        System.out.println(s3);

        System.out.println(s1.equals(s2));
        System.out.println(l1.equals(l2));
        System.out.println(s1.equals(s3));
    }
}
