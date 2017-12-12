package indi.sword.util.basic.Thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class _01_00_JoinTest {

    public static void main(String[] args) throws InterruptedException {
        JoinThreadDemo demo = new JoinThreadDemo();
        Thread thread = new Thread(demo,"MyThread");
        thread.start();

//        thread.join(); //   As a thread terminates the {@code this.notifyAll} method is invoked.
        /*
            跟下面那句一模一样，记住，锁是锁对象，不是锁线程，thread.join锁的是demo对象，
            然后在run方法里面写个notify方法，把demo给唤醒，进入。
            join方法呢，是会在start()方法结束，也就是线程结束之后把demo给唤醒的。
            下面写个count就是这个目的，因为线程唤醒main这边的demo的时候，那边的线程还是alive状态。
            所以进入while之后会继续wait，导致main线程没法子结束。

          */
        //
        System.out.println("-------------");
        synchronized (demo){
            System.out.println("synchronzied ... " + demo);
            int count = 0;
            while (thread.isAlive() && count == 0) {
                count++;
                System.out.println("0000");
                demo.wait(0);
            }
        }
        System.out.println("--------------");

        System.out.println("Main End");

    }
}

class JoinThreadDemo implements Runnable{

    public void run() {
        System.out.printf("%s begins: %s\n", Thread.currentThread().getName(), new Date());
        try {
            TimeUnit.SECONDS.sleep(3);
            System.out.println("run ..." + this);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s has finished: %s\n",  Thread.currentThread().getName(), new Date());
        // 手动唤醒，我有点担心这个run线程不结束呀
        synchronized (this){
            this.notifyAll(); // 怎么让这个程序结束后，再去唤醒对象呢？
        }

    }
}
