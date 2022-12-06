import lombok.Data;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    @Data
    public static class MM{
        private String k = null;
        private String v = null;
    }
    @Data
    public static class MMM{
        private String k = "k";
        private String v = "v";
    }

    @Test
    public void testToMap(){
        List<MM> mmList = new ArrayList<MM>(){{
            add(new MM());
            add(new MM());
            add(new MM());
        }};

        Map<String, String> map = mmList.stream().collect(HashMap::new, (m, v) -> m.put(v.getK(), v.getV()), HashMap::putAll);
        System.out.println(map);
        List<MMM> mmms = Stream.of(new MMM(), new MMM(), new MMM(),new MMM()).collect(Collectors.toList());
        Map<String, String> mmap = mmms.stream().collect(HashMap::new, (m, v) -> m.put(v.getK(), v.getV()), HashMap::putAll);
        System.out.println(mmap);


    }
    @Data
    public static class A{
        private String a;
    }

    @Test
    public void testSet(){
        List<A> aList = new ArrayList<A>(){{
            add(new A(){{
                setA("123");
            }});
        }};

        System.out.println(aList);

        List<A> b = aList.stream().filter(x -> true).collect(Collectors.toList());
        System.out.println(b);
        b.get(0).setA("456");
        System.out.println(aList);
        System.out.println(b);
    }
    @Test
    public void testFlatMap(){
        class A{
            List<Integer> a = Stream.of(1,2,3).collect(Collectors.toList());
        }

        List<A> aList = Stream.of(new A(), new A(), new A()).collect(Collectors.toList());

        System.out.println(aList.stream().map(x -> x.a).collect(Collectors.toList()));
        System.out.println(aList.stream().map(x -> x.a).flatMap(Collection::stream).collect(Collectors.toList()));

    }

}
