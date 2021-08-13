import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

class GTest {
    <T1, T2> List<T1> t(T1 a, T2 b){
        System.out.println(b);
        return new ArrayList<T1>(){{
            add(a);
        }};
    }

    @Test
    public void test(){

        System.out.println(t(1, "asdasdad"));
    }
}

public class Generic{
    <T1, T2> List<T1> t(T1 a, T2 b){
        System.out.println(b);
        return new ArrayList<T1>(){{
            add(a);
        }};
    }

    @Test
    public void test(){
        GTest g = new GTest();
        System.out.println(g.t(1, "asdasdad"));
        System.out.println(g.t("ASasdasdads", 1));
    }
}
