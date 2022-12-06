import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExceptionTest {
    @Test
    public void testTry(){
        try {
            try {
                if(1==1)throw new Exception();
                System.out.println(1);

            }
            finally {
                System.out.println(2);
            }
            System.out.println(3);
        }
        catch (Exception e){
            System.out.println(4);
        }
    }

    @Test
    public void testTry2(){
        try {
            try {
                if(1==1)throw new Exception();
                System.out.println(1);

            }
            catch (Exception e){
                System.out.println("e");
            }
            finally {
                System.out.println(2);
            }
            System.out.println(3);
        }
        catch (Exception e){
            System.out.println(4);
        }
    }
    @Test
    public void testT(){

        try {
            List<String> s = new ArrayList<>();
            throw new Exception("123");
//            System.out.println(s.get(0));
        }
        catch (Throwable t){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            t.printStackTrace(new PrintStream(baos));
            String exception = baos.toString();
            System.out.println(exception);
//            t.printStackTrace();
//            System.out.println(Arrays.toString(t.getStackTrace()));
        }
    }
}
