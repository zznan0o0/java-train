import okhttp3.HttpUrl;
import org.junit.Test;

public class HttpTest {
    @Test
    public void test1(){
        System.out.println(HttpUrl.parse("https://play.min.io:9000"));
    }
}
