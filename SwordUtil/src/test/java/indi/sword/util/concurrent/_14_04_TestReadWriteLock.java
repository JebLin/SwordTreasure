package indi.sword.util.concurrent;


import java.util.concurrent.locks.ReadWriteLock;
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
// 场景四：thread1 在 read，thread1 重入 read
public class _14_04_TestReadWriteLock {
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            ReadWriteLock lock = new ReentrantReadWriteLock();

            System.out.println(Thread.currentThread().getName() + " wait to read lock ...");
            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " get the read lock ... ===");

            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " get the read lock2 ... ===");

            lock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + " get the read lock3 ... ===");


            System.out.println(Thread.currentThread().getName() + " ready to read unlock ...");
            lock.readLock().unlock();

            System.out.println(Thread.currentThread().getName() + " ready to read unlock2 ...");
            lock.readLock().unlock();

            System.out.println(Thread.currentThread().getName() + " ready to read unlock3 ...");
            lock.readLock().unlock();
        }).start();
    }
}

