import java.util.HashMap;
import java.util.Map;

public class StaticMap {
    public static Map<String, String> m;

    static {
         m = new HashMap<>();
     }
}
