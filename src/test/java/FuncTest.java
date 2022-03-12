import org.junit.Test;


import javax.sound.midi.Sequence;
import java.util.ArrayList;
import java.util.List;

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


}
