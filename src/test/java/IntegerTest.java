import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class IntegerTest {
    @Test
    public void testNull(){
        Integer a = null;
        System.out.println(a);
        if(a != null){
            System.out.println(1);
        }
        else {
            System.out.println(2);
        }
    }
    @Test
    public void test(){
        int a = 1;
        int b = 1;
        int c = Integer.valueOf("1");
        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(b));
        System.out.println(System.identityHashCode(c));

    }

    @Test
    public void test2(){
        Integer a = 1;
        Integer b = new Integer("1");
        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(b));
        System.out.println(a != b);
    }

    @Test
    public void test3(){
        Integer a = 1;
        Integer b = new Integer("1");
        int c = a;
        int d = b;
        System.out.println(System.identityHashCode(a));
        System.out.println(System.identityHashCode(b));
        System.out.println(a != b);

        System.out.println(System.identityHashCode(c));
        System.out.println(System.identityHashCode(d));
        System.out.println(a != d);
        System.out.println(b != c);
    }

    @Test
    public void test4(){
        Integer a = 1;
        Integer b = new Integer("1");

        System.out.println(a >= b);
        System.out.println(a <= b);

        b = 3;
        System.out.println(a >= b);
        System.out.println(a <= b);
    }
    @Test
    public void test5(){
        int a = 1;
        int b = 3;
        System.out.println((int)Math.ceil((float)a / b));
    }
    @Test
    public void test6(){
        AtomicInteger idx = new AtomicInteger(0);
        idx.getAndIncrement();
        idx.getAndIncrement();
        idx.getAndIncrement();
        idx.getAndIncrement();
        System.out.println(idx.get());
    }
    @Test
    public void testCNull(){
        Integer a = null;
        if(a > 0){
            System.out.println(1);
        }
    }
    @Test
    public void testLE(){
        Integer a = new Integer(1);
        Integer b = new Integer(1);
        System.out.println(a == b);
        System.out.println(a.equals(b));
        System.out.println(a >= b);

    }
}
