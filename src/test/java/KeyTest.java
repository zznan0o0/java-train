import common.utils.SignUtil;
import org.junit.Test;

import java.util.TreeMap;

public class KeyTest {
    @Test
    public void test(){
//        app_id=20210909885597260442763264&method=auth.access.token&request_id=t6fcmjxbqiqb99t3malp2fvkozquua37&timestamp=2021-09-13+13%3a53%3a52&version=1.0
        TreeMap<String, String> treeMap = new TreeMap<>();

        treeMap.put("app_id","20210909885597260442763264");
        treeMap.put("method","auth.access.token");
        treeMap.put("request_id","t6fcmjxbqiqb99t3malp2fvkozquua37");
        treeMap.put("timestamp","2021-09-13+13%3a53%3a52");
        treeMap.put("version","1.0");

        SignUtil signUtil = new SignUtil();
//        System.out.println(signUtil.sign(treeMap));
    }
}
