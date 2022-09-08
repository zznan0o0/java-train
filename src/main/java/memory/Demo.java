package memory;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class Test{
    public List<String> createList() {
        List<String> list = new ArrayList<String>() {{
            add("a");
            add("b");
        }};
        return list;
    }
}

public class Demo {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        List<String> list = new Test().createList();
        // 获取一个类的所有字段
        Field field = list.getClass().getDeclaredField("this$0");
        // 设置允许方法私有的 private 修饰的变量
        field.setAccessible(true);
        System.out.println(field.get(list).getClass());
    }
}