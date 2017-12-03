package indi.sword.util.base.Thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * rd_jianbin_lin
 * t.join()方法阻塞调用此方法的线程(calling thread)，直到线程t完成，此线程再继续；
 * 通常用于在main()主线程内，等待其它线程完成再结束main()主线程。
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
        Thread thread1 = new Thread(new _01_01_JoinTest("One"));
        Thread thread2 = new Thread(new _01_01_JoinTest("Two"));
        thread1.start();
        thread2.start();

        try {
            thread1.join(); // Waits for this thread to die. 阻塞main线程，等待跑完这个线程
            thread2.join(); //
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("Main thread is finished");
    }

}