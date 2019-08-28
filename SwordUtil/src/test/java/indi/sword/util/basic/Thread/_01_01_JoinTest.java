package indi.sword.util.basic.Thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * rd_jianbin_lin
 * t.join()方法阻塞调用此方法的线程(calling thread)，直到线程t完成，此线程再继续；
 * 通常用于在main()主线程内，等待其它线程完成再结束main()主线程。
 */
/*
    大家重点关注一下join(long millis)方法的实现，可以看出join方法就是通过wait方法来将线程的阻塞，
    如果join的线程还在执行，则将当前线程阻塞起来，直到join的线程执行完成，当前线程才能执行。
    不过有一点需要注意，这里的join只调用了wait方法，却没有对应的notify方法，
    原因是Thread的start方法中做了相应的处理，所以当join的线程执行完成以后，会自动唤醒主线程继续往下执行。

    锁是线程，锁对象执行完毕后，会调用自身对象上的notify();
    当线程运行结束的时候，notify是被线程的子系统调用的
 */
public class _01_01_JoinTest implements Runnable {

    private String name;

    public _01_01_JoinTest(String name) {
        this.name = name;
    }

    public void run() {
        System.out.printf("%s begins: %s\n", name, new Date());
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s has finished: %s\n", name, new Date());
    }

    public static void main(String[] args) {
        Thread thread1 = new Thread(new _01_01_JoinTest("One"),"One");
        Thread thread2 = new Thread(new _01_01_JoinTest("Two"),"Two");
        thread1.start();
        thread2.start();
        try {
            /*
                Waits for this thread to die. 阻塞main线程，等待跑完这个线程
                join(10)的话，表示main线程等你thread1运行10ms，不管你是否执行完毕，都跑Main相关的了
             */
//            thread1.join();
            simulateJoin(thread1);

            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread is finished");
    }

    private static void simulateJoin(Thread thread1) throws InterruptedException{
        synchronized (thread1){
            while (thread1.isAlive()) {
                thread1.wait(0);
            }
        }

    }

}