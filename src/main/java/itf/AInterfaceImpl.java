package itf;

public class AInterfaceImpl {
    public interface AInterface {
        void pp();
        void pp2();
    }

    public interface AInterface2 {
        void pp();
    }


    public void testNew(){
        AInterface a = new AInterface(){
            public void pp(){
                System.out.println(1);
            }
            public void pp2(){
                System.out.println(2);
            }
        };
        a.pp();
        a.pp2();
    }

    public void testNew2(){
        AInterface2 a = () -> System.out.println(1);
        a.pp();
    }
}
