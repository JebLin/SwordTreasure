package indi.sword.util.concurrent;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示。
 * 如：ABCABCABC…… 依次递归
 *
 * @author rd_jianbin_lin
 * @Descrption
 * @Date Sep 3, 2017 4:58:18 PM
 */
public class _12_01_TestCondition {
    public static void main(String[] args) throws Exception {
        LockDemo lockDemo = new LockDemo();

        new Thread(() -> {
            try {
                lockDemo.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "A").start();

        Thread.sleep(5000);
        System.out.println("===================");

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lockDemo.signal();

        },"B").start();

    }

    static class LockDemo {
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        public void await() throws Exception {
            System.out.println(Thread.currentThread().getName() + ",condition ready to lock ");
            lock.lock();
            System.out.println(Thread.currentThread().getName() + ",condition ready to await ");
            condition.await();
            System.out.println(Thread.currentThread().getName() + ",condition ready to unlock ");
            lock.unlock();
        }

        public void signal() {
            System.out.println(Thread.currentThread().getName() + ",condition ready to lock ");
            lock.lock();
            System.out.println(Thread.currentThread().getName() + ",condition ready to signal ");
            condition.signal();
            System.out.println(Thread.currentThread().getName() + ",condition ready to unlock ");
            lock.unlock();
        }
    }
}
