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
// 场景八：thread1 在 write， thread再次 write （测试write锁重入）
public class _14_08_TestReadWriteLock {
    public static void main(String[] args) throws Exception {
        new Thread(() -> {
            ReadWriteLock lock = new ReentrantReadWriteLock();

            System.out.println(Thread.currentThread().getName() + " wait to write lock ...");
            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " get the write lock ... ===");

            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " get the write lock2 ... ===");

            lock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + " get the write lock3 ... ===");


            System.out.println(Thread.currentThread().getName() + " ready to write unlock ...");
            lock.writeLock().unlock();

            System.out.println(Thread.currentThread().getName() + " ready to write unlock2 ...");
            lock.writeLock().unlock();

            System.out.println(Thread.currentThread().getName() + " ready to write unlock3 ...");
            lock.writeLock().unlock();
        }).start();
    }
}

