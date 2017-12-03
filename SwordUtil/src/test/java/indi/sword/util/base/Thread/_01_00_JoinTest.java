package indi.sword.util.base.Thread;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class _01_00_JoinTest {

    public static void main(String[] args) throws InterruptedException {
        JoinThreadDemo demo = new JoinThreadDemo();
        Thread thread = new Thread(demo,"MyThread");
        thread.start();
//        thread.join();
        synchronized (demo){
            while (thread.isAlive()) {
                demo.wait(0);
            }
        }


        System.out.println("ggg");
        System.out.println("ggg");
        System.out.println("ggg");
        System.out.println("ggg");

        synchronized(demo){
            demo.notify();
        }
    }
}

class JoinThreadDemo implements Runnable{

    public void run() {
        System.out.printf("%s begins: %s\n", Thread.currentThread().getName(), new Date());
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("%s has finished: %s\n",  Thread.currentThread().getName(), new Date());
    }
}
