import org.junit.Test;

@FunctionalInterface
interface Add<T> {
    public int add(T x);
}

public class LambdaTest {

    public static int addInt(int s, Add<Integer> add) {
        s += 1;
        return add.add(s);
    }
    @Test
    public static void main(String[] args) {
        System.out.println(addInt(2, (x) -> 1+x));
    }

}
