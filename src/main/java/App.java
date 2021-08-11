import common.utils.HandleData;
import entity.P1;
import entity.P2;

public class App {
    public static void main(String[] args) throws Exception {
        P1 p1 = new P1();
        P2 p2 = new P2();

        p1.setAge(20);
        p2.setName("zs");
        HandleData<P1, P2> handleData = new HandleData<P1, P2>();
        p1 = handleData.add(p1, p2);
        System.out.println(p1);
    }
}
