import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureTest {

    @Test
    public void testCF() throws ExecutionException, InterruptedException {
        System.out.println(testGet());
        System.out.println("2");
//        System.out.println(c.get());
    }

    public String testGet() throws ExecutionException, InterruptedException {
        //这么写好像没啥区别还是得和异步、多线程搭配
        CompletableFuture<String> c = new CompletableFuture<>();
        c.complete("123");
        return c.get();
    }
    @Test
    public void testAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> c = CompletableFuture.supplyAsync(() ->{
            System.out.println(1);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(2);
            return 4;
        });
        //先执行这个
        System.out.println(3);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Integer r = c.get();
        System.out.println(r);
        System.out.println(5);

        //3 1 2 4 5

    }

    @Test
    public void testAsync2() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> c = CompletableFuture.supplyAsync(() ->{
            System.out.println(11);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(12);
            return 1;
        });

        CompletableFuture<Integer> c2 = CompletableFuture.supplyAsync(() ->{
            System.out.println(21);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(22);
            return 2;
        });
        System.out.println(3);
        System.out.println(c.get());
        System.out.println(c2.get());
        System.out.println(4);

        //11 3 21 12 1 22 2 4

    }

    @Test
    public void testAsync3() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> c = CompletableFuture.supplyAsync(() ->{
            System.out.println(11);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(12);
            return 1;
        });

        CompletableFuture<Integer> c2 = CompletableFuture.supplyAsync(() ->{
            System.out.println(21);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(22);
            return 2;
        });
        System.out.println(3);
//        System.out.println(c.get());
//        System.out.println(c2.get());
        System.out.println(4);
        //没有get也会执行而且不会等到执行结束而是直接结束
        //11 3 4 21

    }

}
