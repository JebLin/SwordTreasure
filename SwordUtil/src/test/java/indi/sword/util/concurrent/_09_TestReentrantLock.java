package indi.sword.util.concurrent;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by rd_jianbin_lin on 2017/9/3.
 * 测试 Reentrantlock的方法
 * 可重入概念
 * <p>
 * 若一个程序或子程序可以“安全的被并行执行(Parallel computing)”，则称其为可重入（reentrant或re-entrant）的。
 * 即当该子程序正在运行时，可以再次进入并执行它（并行执行时，个别的执行结果，都符合设计时的预期）。可重入概念是在单线程操作系统的时代提出的。
 * <p>
 * 公平锁与不公平锁区别：
 * 公平情况下会一个一个执行，不公平情况下，无序，不是谁先等待就谁获得锁，而是由JVM控制
 * <p>
 * lock.lock(false)
 * 如果锁被其他资源锁定，那么就会在此处等待. 默认是false，表示不公平锁。
 * <p>
 * if(lock.trylock(3，TimeUnit.SECONDS)){...}else{}
 * 尝试获得锁，刚好拿到就跑if，拿不到就跑else，不等待了。参数是不断尝试获得锁的时间。防止死锁。
 * <p>
 * lock.lockinterruptibly();
 * 调用后一直阻塞到获得锁,但是接受中断信号(相对于lock.lock())
 * <p>
 * <p>
 * ReentrantLock的中断和非中断加锁模式的区别在于：线程尝试获取锁操作失败后，
 * 在等待过程中，如果该线程被其他线程中断了，它是如何响应中断请求的。
 * lock方法会忽略中断请求，继续获取锁直到成功；而lockInterruptibly则直接抛出中断异常，由上层调用者处理中断。
 */
public class _09_TestReentrantLock {
    public static void main(String[] args) throws Exception {

        Thread t1 = new Thread(new MyThread_Interrupted(), "AAAAAA");
        Thread t2 = new Thread(new MyThread_Interrupted(), "BBBBBB");

        // 1. B 线程先跑起来
        t2.start();
        // sleep 确保 B 线程先走 ,A 线程再启动
        TimeUnit.SECONDS.sleep(1);

        // 2. A 在 B 线程后面跑起来
        t1.start();

        // sleep 确保先 lock 操作，再来打断
//        TimeUnit.SECONDS.sleep(1);
        /*
            3. A 线程现在依旧在等待，然后这个时候打断。
            lock.lock() 与 lock.lockInterruptibly() 的处理方式不同
          */
//        t1.interrupt();
//        System.out.println(" t1.interrupt() OK ...");
    }
}

class MyThread_Interrupted implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();

    private boolean hasLock = false;
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " wait to lock .");
            lock.lock(); //被中断，继续等待
//            lock.lockInterruptibly(); //被中断，直接抛出异常
            hasLock = true;
            System.out.println(Thread.currentThread().getName() + " get the lock and running .");

            // 重点：这个操作是模拟B线程一直在处理没能释放锁，为的是让A一直等待着拿锁。
            if (Thread.currentThread().getName().equals("BBBBBB")) {
                Scanner sc = new Scanner(System.in);
                System.out.println("点击任意键终止线程 ...");
                sc.nextLine();
            }
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " be interrupted .");
        } finally {
            System.out.println(Thread.currentThread().getName() + ", has lock -> " + hasLock);
            // 如果没获取到 lock ，然后去 unlock ，会跑出 IllegalMonitorStateException 异常
            if(hasLock){
                System.out.println(Thread.currentThread().getName() + " begin to unlock .");
                lock.unlock();
                System.out.println(Thread.currentThread().getName() + " finished . unlock ");
            }
        }
    }
}
