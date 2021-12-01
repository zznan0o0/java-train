import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Test;

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

public class ClassTest{
    @Test
    public void test(){
        TestClass tc = new TestClass(18);
        System.out.println(tc);

        Apples apples = new Apples("red");
        System.out.println(apples.getName());
//        System.out.println();
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

@Data
//@ToString(callSuper = true)
//@EqualsAndHashCode(callSuper = true)
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
