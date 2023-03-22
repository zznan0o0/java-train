import lombok.Data;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@FunctionalInterface
interface Add<T> {
    public int add(T x);
}

@FunctionalInterface
interface Add2<T1, T2, T3> {
     T3 apply(T1 t1, T2 t2);
}

@FunctionalInterface
interface Fn1 <T, R>{
    R apply(T t);
}


public class LambdaTest {

    public  int addInt(int s, Add<Integer> add) {

        s += 1;
        return add.add(s);
    }
//    @Test
//    public static void main(String[] args) {
//        System.out.println(addInt(2, (x) -> 1+x));
//    }

    @Test
    public void test(){
//        System.out.println(addInt(2, (x) -> 1+x));
//        System.out.println(addInt(2, LambdaTest::add));

//        List<String> list = Arrays.asList("a", "b", "c");
//        list.forEach(x -> System.out.println(x));
//        list.forEach(LambdaTest::print);
//        list.stream().map(LambdaTest::print).collect(Collectors.toList());

        List<TestEntity> testEntityList = new ArrayList<TestEntity>(){{
            for(int i = 0; i < 3; i++){
                TestEntity testEntity = new TestEntity();
                testEntity.setId(i);
                add(testEntity);
            }
        }};
        System.out.println(testEntityList);
        System.out.println(testEntityList.stream().map(LambdaTest::add).collect(Collectors.toList()));

    }


    public static   <T> T add(T x){
        return x;
    }

    public static String print(String x){
        System.out.println(x);
        return x;
    }

    public void a1(String a){
        System.out.println(a + "1");
    }

    public void a2(String a){
        System.out.println(a + "2");
    }

    public String a3(String a, String b){
        return a;
//        System.out.println(a + "2");
    }
    @Test
    public void testMap(){
        String a = "a";
        Map<String, Consumer<String>> m = new HashMap<>();
        m.put("a1", this::a1);
        m.put("a2", this::a2);
//        m.put("a3", this::a3);

        m.get("a1").accept(a);
        m.get("a2").accept(a);
//        m.get("a3").accept(a);

    }

}

@Data
class TestEntity{
    private Integer id;

    public Integer getId(){
        return this.id;
    }

    public Integer add(Integer x){
        return  x+1;
    }
}
