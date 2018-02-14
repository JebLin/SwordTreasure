package indi.sword.util.concurrent;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 20:43 2018/2/11
 * @Modified By
 */

import java.util.concurrent.TimeUnit;

/**
 * Created by zejian on 2017/6/2.
 * Blog : http://blog.csdn.net/javazejian [原文地址,请尊重原创]
 * 也就是对于synchronized来说，如果一个线程在等待锁，那么结果只有两种，
 * 要么它获得这把锁继续执行，要么它就保存等待，即使调用中断线程的方法，也不会生效。
 */
public class _08_04_SynchronizedBlocked implements Runnable{

    public synchronized void f() {
        System.out.println(Thread.currentThread().getName() + " Trying to call f()");
        while(true) // Never releases lock
            Thread.yield();
    }

    /**
     * 在构造器中创建新线程并启动获取对象锁
     */
    public _08_04_SynchronizedBlocked() {
        //该线程已持有当前实例锁
        new Thread(() -> f(),"AA");
    }
    public void run() {
        //中断判断
        while (true) {
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Thread.interrupted()) {
                System.out.println("中断线程!!");
                break;
            } else {
                f();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        _08_04_SynchronizedBlocked sync = new _08_04_SynchronizedBlocked();
        Thread t = new Thread(sync);
        //启动后调用f()方法,无法获取当前实例锁处于等待状态
        t.start();
        TimeUnit.SECONDS.sleep(1);
        //中断线程,无法生效
        t.interrupt();
    }
}
