package indi.sword.util.basic.Thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class _01_00_JoinTest {

    public static void main(String[] args) throws InterruptedException {
        JoinThreadDemo demo = new JoinThreadDemo();
        Thread thread = new Thread(demo, "MyThread");
        thread.start();
        /*
            跟下面那句一模一样，记住，锁是锁对象，不是锁线程，thread.join锁的是demo对象，
            然后在run方法里面写个notify方法，把demo给唤醒，进入。
            join方法呢，是会在start()方法结束，也就是线程结束之后把demo给唤醒的。
            下面写个count就是这个目的，因为线程唤醒main这边的demo的时候，那边的线程还是alive状态。
            所以进入while之后会继续wait，导致main线程没法结束。

          */
        //
        System.out.println("Main Begin");
        System.out.println("-------------");
        thread.join(); //   As a thread terminates the {@code this.notifyAll} method is invoked.
        Thread.currentThread().join();
//
//
//        synchronized (demo) {
//            System.out.println("synchronzied ... " + demo);
////            errorTest01thread,demo);
////            normal01(thread,demo);
//            normal02(thread, demo);
//
//        }
        System.out.println("--------------");
        System.out.println("Main End");

    }


    private static void normal02(Thread thread, JoinThreadDemo demo) throws InterruptedException {
        if (thread.isAlive()) {
            System.out.println("0000");
            /*
                main线程，让出对象锁 ObjectMonitor
             */
            demo.wait();
            System.out.println("wake up ...");

        }
    }

    /**
     * thread 唤醒main这边的demo的时候，那边的线程还是alive状态。
     * 所以又重新入睡了.
     * BLOCKING...
     */
    private static void errorTest01(Thread thread, JoinThreadDemo demo) throws InterruptedException {
        while (thread.isAlive()) {
            System.out.println("0000");
//            demo.wait(0);

            /*
                如果 wait 1的话，也就是阻塞 1s，自动唤醒。
                wait 0，让出对象锁，无限阻塞，等待唤醒。
             */
//            demo.wait(1);
            System.out.println("wake up ...");
        }
    }

    private static void normal01(Thread thread, JoinThreadDemo demo) throws InterruptedException {
        int count = 0;
        while (thread.isAlive() && count == 0) {
            count++;
            System.out.printf("0000, count -> %d \n", count);
            demo.wait();
            System.out.println("wake up ...");

        }
    }
}

class JoinThreadDemo implements Runnable {

    public void run() {
        System.out.printf("%s begins: %s\n", Thread.currentThread().getName(), new Date());
        try {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("run ..." + this);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s has finished: %s\n", Thread.currentThread().getName(), new Date());
        synchronized (this) {
            /*
                锁头释放
             */
            this.notifyAll();
        }
    }
}
