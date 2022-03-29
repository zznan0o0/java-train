import lombok.Data;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class StreamTest {
    @Test
    public void add(){
        List<BigDecimal> list = new ArrayList<BigDecimal>(){{
            add(new BigDecimal(1));
            add(new BigDecimal(2));
            add(new BigDecimal(3));
            add(new BigDecimal(4));
        }};

        BigDecimal t = list.stream().map(x -> x).reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(t);

    }

    @Data
    class M{
        private String name;
        private Integer age;
    }
    @Test
    public void  testMin(){
        List<M> mList = new ArrayList<M>(){{
            add(new M(){{
                setName("4");
                setAge(4444);
            }});
            add(new M(){{
                setName("1");
                setAge(11111);
            }});
            add(new M(){{
                setName("2");
                setAge(22222);
            }});
            add(new M(){{
                setName("3");
                setAge(33333);
            }});
        }};
        System.out.println(mList.stream().min(Comparator.comparing(M::getAge)).get());
    }
    @Test
    public void testLL(){
        List<List<Integer>> iLL = new ArrayList<List<Integer>>(){{
           add(new ArrayList<Integer>(){{
               add(1);
               add(1);
               add(1);
               add(1);
           }});
            add(new ArrayList<Integer>(){{
                add(2);
                add(2);
                add(2);
                add(2);
            }});
            add(new ArrayList<Integer>(){{
                add(3);
                add(3);
                add(3);
                add(3);
            }});
        }};

        List<Integer> iL = iLL.stream().flatMap(x -> x.stream()).map(x -> x).distinct().collect(Collectors.toList());
        System.out.println(iL);
    }

    @Test
    public void testGroup(){
        List<String> list = new ArrayList<String>(){{
            add("1");
            add("1");
            add("1");
            add("1");
            add("1");
            add("2");
            add("2");
            add("2");
            add("2");
            add("3");
            add("3");
            add("3");
            add("3");
            add("3");
        }};

        System.out.println(list.stream().collect(Collectors.groupingBy(x -> x)));
    }

}
