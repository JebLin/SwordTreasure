package indi.sword.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by rd_jianbin_lin on 2017/9/3.
 * 测试 Reentrantlock的方法
 * 可重入概念

 若一个程序或子程序可以“安全的被并行执行(Parallel computing)”，则称其为可重入（reentrant或re-entrant）的。
 即当该子程序正在运行时，可以再次进入并执行它（并行执行时，个别的执行结果，都符合设计时的预期）。可重入概念是在单线程操作系统的时代提出的。
 *
 * 公平锁与不公平锁区别：  公平情况下会一个一个执行，不公平情况下，无序，不是谁先等待就谁获得锁，而是由JVM控制
 *
 * lock.lock(false)
 *
 * 如果锁被其他资源锁定，那么就会在此处等待. 默认是false，表示不公平锁。
 *
 * if(lock.trylock(3，TimeUnit.SECONDS)){...}else{}
 * 尝试获得锁，刚好拿到就跑if，拿不到就跑else，不等待了。参数是不断尝试获得锁的时间。防止死锁。
 *
 * lock.lockinterruptibly(); 调用后一直阻塞到获得锁,但是接受中断信号(相对于lock.lock())
 *
 *
 * ReentrantLock的中断和非中断加锁模式的区别在于：线程尝试获取锁操作失败后，
 * 在等待过程中，如果该线程被其他线程中断了，它是如何响应中断请求的。
 * lock方法会忽略中断请求，继续获取锁直到成功；而lockInterruptibly则直接抛出中断异常，由上层调用者处理中断。
 *
 */
public class _09_TestReentrantLock {
    public static void main(String[] args){

        Thread t1 = new Thread(new MyThread_Interrupted(),"t1");
        Thread t2 = new Thread(new MyThread_Interrupted(),"t2");
        t1.start();
        t2.start();
        t2.interrupt();


    }
}

class MyThread_Interrupted implements Runnable{

    private static Lock lock = new ReentrantLock();
    public void run(){
        try{
            //---------------------------------a
//            lock.lock(); //中断继续等待
            lock.lockInterruptibly(); //中断直接抛出异常

            System.out.println(Thread.currentThread().getName() + " running");
            TimeUnit.SECONDS.sleep(5);
            System.out.println(Thread.currentThread().getName() + " finished");
        }
        catch (InterruptedException e){
            System.out.println(Thread.currentThread().getName() + " interrupted");

        } finally {
            lock.unlock();
        }

    }
}
