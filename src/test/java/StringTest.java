import org.junit.Test;

public class StringTest {
    @Test
    public void test(){
        String a = "123";
        String c =  String.valueOf(123) + String.valueOf("asdasd");
        System.out.println(c);
        System.out.println(String.valueOf(null));
        if(a != null || a != ""){

            System.out.println(a != null || a != "");
        }
    }
}
