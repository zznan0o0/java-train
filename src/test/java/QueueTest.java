import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

public class QueueTest {
    @Test
    public void testQueue(){
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>(3);
        q.offer(1);
        q.offer(1);
        q.offer(1);
        q.offer(1);
        System.out.println(q);
    }
}
