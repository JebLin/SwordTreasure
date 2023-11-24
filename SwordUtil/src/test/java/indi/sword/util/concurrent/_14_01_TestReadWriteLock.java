package indi.sword.util.concurrent;


import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 1. ReadWriteLock : 读写锁
 *
 * 情形： 有些人不喜欢产生 脏读，于是在读写上面加了Synchronized
 *
 * 写写/读写 需要“互斥”
 * 只要有人在读，你就不能写，必须得等到全部人 都不读了你才能写
 * 只要有人在写，你就不能读，写完你再读
 *
 * 读读 不需要互斥
 *
 */
// 场景一：thread1 在 read，thread2-threadN 接着 read
public class _14_01_TestReadWriteLock {
    public static void main(String[] args) throws Exception {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " wait to lock ...");
                lock.readLock().lock();
                System.out.println(Thread.currentThread().getName() + " get the lock ... ===");
                System.out.println("点击任意键唤醒线程 ...");
                Scanner sc = new Scanner(System.in);
                sc.nextLine();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + " ready to unlock ...");
                    lock.readLock().unlock();
                }
            }, "Read_" + i).start();
        }
    }
}

