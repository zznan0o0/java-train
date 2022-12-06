import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regular {
    @Test
    public void testFileName(){
        String s = "http://192.168.2.117:9000/test/20220601-TSGY-1263001978007588864-264b3f97-d60d-484e-8970-1f67281533a5.jpg?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=test%2F20220601%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20220601T031259Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=0d9a5dab35f33e5687aa102bb4206a9f32ce08687a332df66b2d14e257f909ff";
        String pattern = ".+\\/(.+?)\\..+";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(s);
        if(m.find()){
            System.out.println(m.groupCount());
            System.out.println(m.group(0));
            System.out.println(m.group(1));
        }
    }
    @Test
    public void testTest(){
        Pattern r = Pattern.compile("\\d");
        System.out.println("a11a1a".matches(".*\\d.*"));
        System.out.println("a11a1a".matches(".*[a-z].*"));
        System.out.println("a11aA1a".matches(".*[A-Z].*"));
        System.out.println("a11a$A1a".matches(".*\\W.*"));
    }
}
