import lombok.Data;
import org.apache.commons.codec.binary.StringUtils;
import org.junit.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringTest {
    @Test
    public void testByte(){
        String s = "12123sdfs";
        byte[] b = s.getBytes(StandardCharsets.UTF_8);
        byte[] bb = new byte[0];
        System.out.println(bb);
        System.out.println(b);
    }
    @Test
    public void testJoinSet(){
        Set<String> s1 = new HashSet<>();
        s1.add("1");
        s1.add("2");
        s1.add("3");
        s1.add("1");
        Set<String> s2 = new HashSet<>();

        System.out.println(String.join(",", s1));
        System.out.println(String.join(",", s2));
    }

    @Test
    public void getNo(){
        List<String> strList = new ArrayList<String>(){{
            add("220311.B02");
            add("220311.B05");
            add("220311.B03");
        }};

        List<Integer> str2List = new ArrayList<>();

        for(String v : strList){
            str2List.add(Integer.valueOf(v.substring(v.length()-2)));
        }

        System.out.println(str2List.stream().max(Comparator.comparing(x -> x)).get());
    }

    @Test
    public void testJoin(){
        List<Integer> list = new ArrayList<Integer>(){{
            add(1);
            add(1);
            add(1);
            add(1);
        }};


    }

    @Test
    public void testFix(){
        System.out.println(String.format("%03d", 10));
    }

    @Test
    public void testToStr(){
        int[] ints = new int[3];
        this.toStr(1, "1", 1L, new String[]{"1", "2"}, new int[]{1,2,3});
    }


    public void toStr(Object ... objs){

        String s = "";
        for(Object obj : objs){
             s += String.valueOf(obj);
        }
        System.out.println(s);
    }


    @Test
    public void test() {
        String a = "123";
        String c = String.valueOf(123) + String.valueOf("asdasd");
        System.out.println(c);
//        System.out.println(String.valueOf(null));
        if (a != null || a != "") {

            System.out.println(a != null || a != "");
        }
    }

    @Test
    public void test2() {
        String a = "ABCDEFG";
        System.out.println(a.indexOf("A"));
        System.out.println(a.charAt(1));
        char b = a.charAt(1);
        String s = a + b;
        System.out.println(s);

        String c = "10A99999";
        System.out.println(Integer.valueOf(c.substring(3)));
        System.out.println(c.charAt(2));

    }

    @Test
    public void test3() {
        String a = "2000";
        String b = String.valueOf(2000);
        System.out.println(a); //2000
        System.out.println(b); // 2000
        System.out.println(a == b); // false
        System.out.println(a.equals(b)); // true

        Integer i1 = 1;
        Integer i2 = 1;
        Integer i3 = Integer.valueOf("1");
        Integer i4 = new Integer(1);
        System.out.println(i1 == i2); //true
        System.out.println(i1 == i3); // true
        System.out.println(i1 == i4); // false
        System.out.println(i1.equals(i3)); // true
        System.out.println(i1.equals(i4)); // true



        SE se1 = new SE();
        se1.setN("2000");
        SE se2 = new SE();
        se2.setN(String.valueOf(2000));
        System.out.println(se1); //SE(n=2000)
        System.out.println(se2); //SE(n=2000)
        System.out.println(se1.getN() == se2.getN()); //false
        System.out.println(se1.getN().equals(se2.getN())); //true
    }

    @Test
    public void test4() {
        String a = "abc";
        System.out.println(System.identityHashCode(a));
        String b = "abc";
        System.out.println(System.identityHashCode(b));
        a="456";
        System.out.println(System.identityHashCode(a));
        System.out.println(a);
        System.out.println(b);
        if (a == b) {
            System.out.println("a==b");
        } else {
            System.out.println("a!=b");
        }
    }

    @Test
    public void test5(){
        String a = "100";
        String b = String.valueOf(100);
        Map<String, String> m = new HashMap<>();
        m.put(a, "1");
        System.out.println(m.get(b));
        m.put(b, "2");
        System.out.println(m.get(a));
        System.out.println(a == b);
    }

    @Test
    public void test6(){
        String str = "hello";
        char carr[] = new char[3];
        carr[0] = '1';
        carr[1] = '2';
        carr[2] = '中';

        System.out.println(carr);


        char c = str.charAt(0);
        char d = str.charAt(0);
        System.out.println(System.identityHashCode(c));
        System.out.println(System.identityHashCode(d));

        String a = "123";
        String b = String.valueOf(123);
        char c1 = a.charAt(0);
        char c2 = b.charAt(0);
        System.out.println(c1); //1
        System.out.println(c2); //1
        System.out.println(System.identityHashCode(a)); //1724731843
        System.out.println(System.identityHashCode(b)); //1305193908
        System.out.println(a.hashCode()); // 49
        System.out.println(b.hashCode()); // 49
        System.out.println(System.identityHashCode(c1)); // 1313953385
        System.out.println(System.identityHashCode(c2)); // 1313953385
        System.out.println(a == b); //false
        System.out.println(c1 == c2); // true
    }

    @Test
    public void test7(){
        SE s1 = new SE();
        SE s2 = new SE();
        System.out.println(System.identityHashCode(s1));
        System.out.println(System.identityHashCode(s2));

        Integer i1 = 1;
        Integer i2 = Integer.valueOf(1);
        System.out.println(System.identityHashCode(i1));
        System.out.println(System.identityHashCode(i2));

        Integer ii1 = new Integer(1);
        Integer ii2 = new Integer(1);
        System.out.println(System.identityHashCode(ii1));
        System.out.println(System.identityHashCode(ii2));

        String ss1 = new String("1");
        String ss2 = new String("1");
        System.out.println(System.identityHashCode(ss1));
        System.out.println(System.identityHashCode(ss2));

        String s3 = "3";
        String s4 = "2";

        System.out.println(System.identityHashCode(s3));
        System.out.println(System.identityHashCode(s4));


    }
    @Test
    public void test8(){
//值一样但 != 为true
//        for(TbPropsContent v : updateList){
//            if(!v.getContent().equals(contentMap.get(v.getId()))){
//                v.setContent(contentMap.get(v.getId()));
//                needUpdateList.add(v);
//            }
//        }
        SE se1 = new SE();
        SE se2 = new SE();
        System.out.println(System.identityHashCode(se1.getN()));
        System.out.println(System.identityHashCode(se2.getN()));

        se1.setN(String.valueOf(1));
        System.out.println(System.identityHashCode(se1.getN()));
        se1.setN("1");
        System.out.println(System.identityHashCode(se1.getN()));
        se2.setN(String.valueOf(1));
        System.out.println(System.identityHashCode(se1.getN()));
        System.out.println(System.identityHashCode(se2.getN()));
    }

    @Test
    public void test9(){
        String a = "1";

        System.out.println(System.identityHashCode(a));
        a = String.valueOf(1);
        System.out.println(System.identityHashCode(a));
        a = new String("1");
        System.out.println(System.identityHashCode(a));
        a = String.valueOf(1);
        System.out.println(System.identityHashCode(a));
        String b = "1";
        System.out.println(System.identityHashCode(b));
        b = String.valueOf(1);
        System.out.println(System.identityHashCode(b));
    }

    @Test
    public void test10(){
        String s = "";
        String s2 = " ";
        System.out.println(s2.isEmpty());
        System.out.println(s.isEmpty());
        System.out.println(s2 == "");
        System.out.println(s == "");
        System.out.println(s.equals(""));
    }

    @Data
    class SE {
        private String n;
    }
    @Test
    public void testSplitEmpty(){
        String[] arr = "1".split(",");
        System.out.println(Arrays.toString(arr));
    }
    @Test
    public void testFormat(){
        System.out.println(String.format("asdasd",1,2,3));
    }
    @Test
    public void testUUId(){
        System.out.println(UUID.randomUUID().toString());
        System.out.println(System.currentTimeMillis());
    }
    @Test
    public void toS(){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher m = pattern.matcher("V123w4dsg5");
//        String s = m.replaceAll("").trim();
        if(m.find()){
            System.out.println(m.group(0));
        }
//        System.out.println(s);
//        System.out.println(Integer.valueOf("1000"));
//        System.out.println(Integer.valueOf("0001"));
    }
    @Test
    public void toI(){
        System.out.println("");
        System.out.println(String.format("%.2f*%04.2f*%04d", 123.12, 1.2, 1));

        DecimalFormat g1=new DecimalFormat("0000.##");
        System.out.println(g1.format(BigDecimal.valueOf(1.1)));
        System.out.println(String.format("%.1f", 1.11));
        //String message = MessageFormat.format("您好{0}，晚上好！您目前余额：{1,number,#.00}元，积分：{2}", "张三", "10.1", 10);
        //System.out.println(message);

        System.out.println(String.format("%.0f", 1f));
    }
    @Test
    public void testLength(){
        System.out.println("123".length());
        System.out.println("12a".length());
        System.out.println("12啊".length());
    }
    @Test
    public void testJoinNull(){
        System.out.println("123"+null);
        System.out.println(String.valueOf("123").equals(null));
    }
    @Test
    public void testVar(){
        String a = null, b = "ab";
        System.out.println(a);
        System.out.println(b);
    }

}


