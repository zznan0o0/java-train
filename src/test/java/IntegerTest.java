import org.junit.Test;

public class IntegerTest {
    @Test
    public void test(){
        int a = 1;
        int b = 1;
        int c = Integer.valueOf("1");
        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(b));
        System.out.println(System.identityHashCode(c));

    }
}
