import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EnumTest {

    public interface SortEnumBase{
        String getDefault();
    }

    @AllArgsConstructor
    @Getter
    public enum E1  implements SortEnumBase{
        Default("default");
        private String fieldName;

        public String getDefault(){
            return Default.getFieldName();
        }
    }

    @AllArgsConstructor
    @Getter
    public enum E2  {
        Default("default", "1"),
        default2("default2", "2");
        private String key;
        private String val;

    }

    @Test
    public void testEnum(){
//        System.out.println(toMap(E2, E2::getKey, E2::getVal));
//        System.out.println(toMap2(E2.values(), E2::getKey, E2::getVal));
        System.out.println(this.getDefault(E1.Default));
    }

    public String getDefault(SortEnumBase sortEnumBase){
        return sortEnumBase.getDefault();
    }

    public static <E extends Enum<E>, K, V> Map<K, V> toMap(Class<E> eClass, Function<E, K> getKey, Function<E, V> getVal){
        E[] eArr = eClass.getEnumConstants();
        Map<K, V> map = new HashMap<>();
        for(E v : eArr){
            map.put(getKey.apply(v), getVal.apply(v));
        }

        return map;
    }

    public static <E, K, V> Map<K, V> toMap2(E[] eClasses, Function<E, K> getKey, Function<E, V> getVal){
        Map<K, V> map = new HashMap<>();
        for(E v : eClasses){
            map.put(getKey.apply(v), getVal.apply(v));
        }

        return map;
    }
}
