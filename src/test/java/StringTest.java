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

    @Test
    public void test2(){
        String a = "ABCDEFG";
        System.out.println(a.indexOf("A"));
        System.out.println(a.charAt(1));
        char b = a.charAt(1);
        String s = a + b;
        System.out.println(s);

        String c = "10A99999";
        System.out.println(Integer.valueOf(c.substring(3)));
        System.out.println(c.charAt(2));

    }

    
}
