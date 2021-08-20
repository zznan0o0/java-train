import org.junit.Test;

public class FuncTest {
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
