package memory;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class DoubleBracket {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Map<String, String> map = new DoubleBracket().createMap();
        Field field = map.getClass().getDeclaredField("this$0");
        field.setAccessible(true);
        System.out.println(field.get(map).getClass());
    }
    public Map<String, String> createMap() {
        return  new HashMap<String, String>() {{
            put("map1", "value1");
            put("map2", "value2");
            put("map3", "value3");
        }};
    }

}