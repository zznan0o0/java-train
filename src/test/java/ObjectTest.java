import lombok.Data;
import org.junit.Test;

import java.util.*;

public class ObjectTest {
    @Test
    public void test(){
        Map<String, Object> m = new HashMap<>();
        m.put("a", 1);
        m.put("b", "abc");
        m.put("c", m);
        System.out.println(m);
        Map<String, Object> mm = (Map<String, Object>) m.get("c");
        Map<String, Object> mmm = (Map<String, Object>) mm.get("c");
        System.out.println(mm);
        System.out.println(mmm);
    }
    @Test
    public void testEquals(){
        int i = 1;
        Integer i2 = 1;
        Integer i3 = Integer.valueOf(1);

        System.out.println(Objects.equals(i, i2));
        System.out.println(Objects.equals(i, null));
        System.out.println(Objects.equals(i, i3));
        System.out.println(Objects.equals(i, 1L));
        System.out.println(Objects.equals(null, null));
    }
    @Test
    public void testEqualList(){
        Object o1 = 1;
        Object o2 = 1L;
        Object o3 = "1";
        Object o4 = 1.00;
        List<Object> list = new ArrayList<Object>(){{
            add(o1);
            add(o2);
            add(o3);
            add(o4);
        }};
        System.out.println(list);

        System.out.println(list.get(0).equals(list.get(1)));
        System.out.println(list.get(1).equals(list.get(2)));
        System.out.println(list.get(0).equals(list.get(2)));


        Object oo1 = new Integer(1);
        Object oo2 = 1L;
        Object oo3 = "1";
        Object oo4 = 1.00;
        List<Object> llist = new ArrayList<Object>(){{
            add(oo1);
            add(oo2);
            add(oo3);
            add(oo4);
        }};

        for(int i = 0; i < list.size(); i++){
            System.out.println(list.get(i).equals(llist.get(i)));
//            System.out.println("------start----");
//            System.out.println(list.get(i) instanceof Integer);
//            System.out.println("------end----");
        }

        System.out.println(list.equals(llist));
    }

    @Data
    public static class Op{
        private String op;
    }

    @Test
    public void testOptional(){
        Op op = new Op();
        Op op1 = new Op(){{
            setOp("123");
        }};
        Op op2 = null;

        String r = Optional.ofNullable(op).map(Op::getOp).orElse("");
        String r1 = Optional.ofNullable(op1).map(Op::getOp).orElse("1");
        String r2 = Optional.ofNullable(op2).map(x -> x.getOp()).orElse("2");
        System.out.println(r);
        System.out.println(r1);
        System.out.println(r2);
    }
    @Test
    public void testEqLI(){
        Long l = 0L;
        Integer i = 0;
        System.out.println(l.equals(i));;
    }
}
