import common.utils.HandleData;
import common.utils.MapDictDictFn;
import lombok.Data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

class MapDictDictFnImp implements MapDictDictFn<ET1, ET2, ET3>{

    @Override
    public ET3 apply(ET1 o, ET2 o2, String key) {
        return null;
    }
}

public class HandleDataTest {
    @Test
    public void test() throws Exception {
        long startTime = System.currentTimeMillis();
        HandleData<ET1, ET2, ET3> handleData = new HandleData();
        List<ET1> t1List = new ArrayList<ET1>(){{
            for(int i = 0; i <1000000; i++){
                ET1 t1 = new ET1();
                t1.setId(i);
                t1.setId2(i);
                t1.setName("name" + String.valueOf(i));
                add(t1);
            }
        }};
        List<ET2> t2List = new ArrayList<ET2>(){{
            for(int i = 0; i < 1000000; i++){
                ET2 t2 = new ET2();
                t2.setId(i);
                t2.setId2(i);
                t2.setAge(10+i);
                add(t2);
            }
        }};
        long endTime = System.currentTimeMillis();
        System.out.println("运行时间:" + (endTime - startTime) + "ms");

        long startTime1 = System.currentTimeMillis();

//        System.out.println(t1List);
//        System.out.println(t2List);
        List<String> ks = new ArrayList<String>(){{
            add("id");
            add("id2");
        }};

        MapDictDictFn<ET1,ET2, ET3> h = HandleDataTest::handle;
        List<ET3> t3List =  handleData.mapDictDict(t1List, t2List, ks, ks, h);
        long endTime1 = System.currentTimeMillis();
        System.out.println("运行时间:" + (endTime1 - startTime1) + "ms");
//        System.out.println(t3List);

//        this.handleTest(t1List.get(0), t2List.get(0), "0,0", h);
//        System.out.println(t3List);


    }




    public static MapDictDictFn<ET1, ET2, ET3> handle2 = new MapDictDictFn<ET1, ET2, ET3>() {
        @Override
        public ET3 apply(ET1 t1, ET2 t2, String key) {
            System.out.println(t1);
            System.out.println(t2);
            System.out.println(key);
            return null;
        }


    };

    public void handleTest(ET1 t1, ET2 t2, String k, MapDictDictFn fn){
        System.out.println(fn.apply(t1, t2, k));
    }


    public static ET3 handle(ET1 t1, ET2 t2, String k){
        ET3 t3 = new ET3();
        t3.setId(t1.getId());
        t3.setName(t1.getName());
        t3.setAge(t2.getAge());
//        System.out.println(k);
        return t3;
    }
}

@Data
class ET1{
    private Integer id;
    private Integer id2;
    private String name;
}

@Data
class ET2{
    private Integer id;
    private Integer id2;
    private Integer age;
}

@Data
class ET3{
    private Integer id;
    private String name;
    private Integer age;
}
