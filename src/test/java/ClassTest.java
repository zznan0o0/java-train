import entity.CC;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Test;




public class ClassTest{
    @Test
    public void testCC(){
        CC cc = new CC();
        CC.C1 c1 = new CC.C1();
        CC.C2 c2 = new CC.C2();
        cc.setC1(c1);
        cc.setC2(c2);

        System.out.println(cc);
        System.out.println(cc.getC1());
    }


    @Test
    public void test(){
        TestClass tc = new TestClass(18);
        System.out.println(tc);

        Apples apples = new Apples("red");
        System.out.println(apples.getName());
//        System.out.println();
    }

    public void print(){
        System.out.println(3);
    }
    @Test
    public void testNewTest(){
        NewTest newTest = new NewTest(){
            @Override
            public void print(){
                System.out.println(2);
            }
        };
        newTest.print();

        NewTest newTest1 = new NewTest();
        newTest1.print();
    }

    class NewTest{
        public void print(){
            System.out.println(1);
        }
    }


    @Data
     class TestClass {
        private Integer age;
        private String name;

        public TestClass(Integer age){
            this(age, "王小明");
        }

        public  TestClass(Integer age, String name){
            this.age = age;
            this.name = name;
        }
    }

    @Data
    class Fruits{
        private  Integer num;
        private  String name;

        public Fruits(String name, Integer num){
            this.name = name;
            this.num = num;
        }
    }

    //@ToString(callSuper = true)
    //@EqualsAndHashCode(callSuper = true)
    @Data
    class Apples extends Fruits{
        private String color;

        public Apples(String color){
            super("apple", 10);
            System.out.println(this.getName());
            System.out.println(super.getName());
            this.color=color;
        }

        public String getName(){
            return "apple2";
        }
    }
}

