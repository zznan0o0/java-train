import org.junit.Test;

public class BooleanTest {
    @Test
    public void testll(){
        boolean b1 = true;
        boolean b2 = false;
        boolean b3 = false;
        b1 = b2 || b3;
        System.out.println(b1);


        boolean b11 = false;
        boolean b22 = true;
        boolean b33 = false;
        b11 = b22 || b33;
        System.out.println(b11);

        boolean b111 = false;
        boolean b222 = false;
        boolean b333 = true;
        b111 = b222 || b333;
        System.out.println(b111);
    }
}
