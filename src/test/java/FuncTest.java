import lombok.Data;
import org.junit.Test;


import javax.sound.midi.Sequence;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FuncTest {
    @Test
    public void testChangeData(){
        List<Integer> list = new ArrayList<Integer>(){{
            add(1);
        }};
        Integer i = 1;
        this.changeData(list, i);
        System.out.println(list);
        System.out.println(i);
    }

    public void changeData(List<Integer> list, Integer i){
        list.set(0, 2);
        i = 2;
    }

    @Test
    public void test(){
        Integer n1 = 1;
        add(n1);
        System.out.println(n1);
    }

    public void  add(Integer n1){
        n1 = n1 + 1;
        System.out.println(n1);
    }
    @Test
    public void testForR(){
        List<Integer> l = Stream.of(1,2,3).collect(Collectors.toList());
        l.forEach(x -> {
            System.out.println(x);
            if(x.equals(4)) return;
        });
        System.out.println(4);
    }
    @Test
    public void testField(){
//        A::getName.
//        System.out.println(String.valueOf(A::getName) );
    }
    @Data
    public static class A{
        private String name;
    }

}
