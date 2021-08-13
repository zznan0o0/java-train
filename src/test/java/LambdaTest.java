import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
