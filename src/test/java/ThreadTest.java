import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadTest {
    class Print implements Runnable{
        @Override
        public void run(){
            System.out.println(1);
        }
    }

    @Test
    public void t0(){
        Print p = new Print();
        new Thread(p).start();
    }


    static int tickets = 10;

    class SellTickets implements Runnable{

        @Override
        public void run() {
            // 未加同步时产生脏数据
            while(tickets > 0) {

                System.out.println(Thread.currentThread().getName()+"--->售出第：  "+tickets+" 票");
                tickets--;

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            if (tickets <= 0) {

                System.out.println(Thread.currentThread().getName()+"--->售票结束！");
            }
        }
    }
    @Test
    public void t1() {


        SellTickets sell = new ThreadTest().new SellTickets();

        Thread thread1 = new Thread(sell, "1号窗口");
        Thread thread2 = new Thread(sell, "2号窗口");
        Thread thread3 = new Thread(sell, "3号窗口");
        Thread thread4 = new Thread(sell, "4号窗口");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();


    }

    //synchronized关键字只让需要同步的地方同步运行
    class SellTicketsSync implements Runnable{

        @Override
        public void run() {
            //同步方法
            while (tickets > 0) {
                //不需要同步的代码
                System.out.println(tickets);
                //需要同步的代码
                synMethod();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (tickets<=0) {

                    System.out.println(Thread.currentThread().getName()+"--->售票结束");
                }

            }


        }

        synchronized void synMethod() {

            synchronized (this) {
                if (tickets <=0) {

                    return;
                }

                System.out.println(Thread.currentThread().getName()+"---->售出第 "+tickets+" 票 ");
                tickets-- ;
            }

        }

    }
    @Test
    public void t2(){
        SellTicketsSync sell = new ThreadTest().new SellTicketsSync();

        Thread thread1 = new Thread(sell, "1号窗口");
        Thread thread2 = new Thread(sell, "2号窗口");
        Thread thread3 = new Thread(sell, "3号窗口");
        Thread thread4 = new Thread(sell, "4号窗口");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    //使用lock保证线程安全
    //并发量比较小的情况下，使用synchronized是个不错的选择；但是在并发量比较高的情况下，其性能下降会很严重，此时ReentrantLock是个不错的方案。
    class SellTicketsLock implements Runnable{

        Lock lock = new ReentrantLock();

        @Override
        public void run() {
            // Lock锁机制
            while(tickets > 0) {
                //不需要同步代码
                System.out.println(tickets);
                try {
                    //需要同步代码
                    lock.lock();

                    if (tickets <= 0) {

                        return;
                    }

                    System.out.println(Thread.currentThread().getName()+"--->售出第：  "+tickets+" 票");
                    tickets--;
                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }finally {

                    lock.unlock();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (tickets <= 0) {

                System.out.println(Thread.currentThread().getName()+"--->售票结束！");
            }

        }
    }
    @Test
    public void t3(){
        SellTicketsLock sell = new ThreadTest().new SellTicketsLock();

        Thread thread1 = new Thread(sell, "1号窗口");
        Thread thread2 = new Thread(sell, "2号窗口");
        Thread thread3 = new Thread(sell, "3号窗口");
        Thread thread4 = new Thread(sell, "4号窗口");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }


    //在使用synchronized 代码块时,可以与wait()、notify()、nitifyAll()一起使用，从而进一步实现线程的通信。
    static final Object obj = new Object();

    //第一个子线程
    static class ThreadA implements Runnable{

        @Override
        public void run() {


            int count = 10;
            while(count > 0) {

                synchronized (ThreadTest.obj) {

                    System.out.println("A-----"+count);
                    count--;

                    synchronized (ThreadTest.obj) {

                        //notify()方法会唤醒因为调用对象的wait()而处于等待状态的线程，从而使得该线程有机会获取对象锁。
                        //调用notify()后，当前线程并不会立即释放锁，而是继续执行当前代码，直到synchronized中的代码全部执行完毕，
                        ThreadTest.obj.notify();

                        try {
                            ThreadTest.obj.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

    }

    static class ThreadB implements Runnable{


        @Override
        public void run() {

            int count = 10;

            while(count > 0) {

                synchronized (ThreadTest.obj) {
                    System.out.println("B-----"+count);
                    count--;

                    synchronized (ThreadTest.obj) {

                        //notify()方法会唤醒因为调用对象的wait()而处于等待状态的线程，从而使得该线程有机会获取对象锁。
                        //调用notify()后，当前线程并不会立即释放锁，而是继续执行当前代码，直到synchronized中的代码全部执行完毕，
                        ThreadTest.obj.notify();

                        try {
                            ThreadTest.obj.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }

            }

        }

    }

    static class ThreadC implements Runnable{


        @Override
        public void run() {

            int count = 10;

            while(count > 0) {

                synchronized (ThreadTest.obj) {
                    System.out.println("C-----"+count);
                    count--;

                    synchronized (ThreadTest.obj) {

                        //notify()方法会唤醒因为调用对象的wait()而处于等待状态的线程，从而使得该线程有机会获取对象锁。
                        //调用notify()后，当前线程并不会立即释放锁，而是继续执行当前代码，直到synchronized中的代码全部执行完毕，
                        ThreadTest.obj.notify();

                        try {
                            ThreadTest.obj.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }

            }

        }

    }

    @Test
    public void t4(){
        //A和B会互相抢占线程
        new Thread(new ThreadA()).start();
        new Thread(new ThreadB()).start();
        //多加一个C只能最后抢到一次?只能俩个线程互相抢吗
        new Thread(new ThreadC()).start();


    }

    @Test
    public void t5(){
        Thread threadA = new Thread(new ThreadA());
        Thread threadB = new Thread(new ThreadB());
        Thread threadC = new Thread(new ThreadC());
        //改成这样就可以互相抢了
        threadA.start();
        threadB.start();
        threadC.start();
    }
}
