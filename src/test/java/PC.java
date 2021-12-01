import org.junit.Test;

class P {
    public void p() {
        System.out.println(1);
    }
}

class C extends P {
    public void pP() {
        super.p();
    }

    public void p() {
        System.out.println(2);
    }
}


public class PC {
    @Test
    public void t(){
        C c = new C();
        c.p();
        c.pP();
    }
}
