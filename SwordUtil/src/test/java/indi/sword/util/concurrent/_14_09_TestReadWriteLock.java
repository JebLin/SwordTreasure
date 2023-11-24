package indi.sword.util.concurrent;


import java.util.Scanner;
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
// 场景九：thread_write_01持有锁，后面1读1写3读排队（测试读写混合唤醒）
public class _14_09_TestReadWriteLock {
    public static void main(String[] args) throws Exception {

        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        // 1. 上一个 write 锁
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " wait to write lock ...");
            lock.writeLock().lock();

            Scanner sc = new Scanner(System.in);
            System.out.println("点击任意键唤醒线程 ...");
            sc.nextLine();

            System.out.println(Thread.currentThread().getName() + " ready to write unlock ...");
            lock.writeLock().unlock();

        },"write_01").start();


        // 确保上面代码先执行
        Thread.sleep(1000);

        // 2. 上一个 read 锁
        new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " wait to write lock ...");
                lock.readLock().lock();

                System.out.println(Thread.currentThread().getName() + " ready to write unlock ...");
                lock.readLock().unlock();

        },"read").start();

        // 确保上面代码先执行
        Thread.sleep(1000);

        // 3. 上一个 write 锁
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " wait to write lock ...");
            lock.writeLock().lock();

            System.out.println(Thread.currentThread().getName() + " ready to write unlock ...");
            lock.writeLock().unlock();

        },"write_02").start();

        // 确保上面代码先执行
        Thread.sleep(1000);

        // 4. 上3个 read 锁
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " wait to write lock ...");
                lock.readLock().lock();

                System.out.println(Thread.currentThread().getName() + " ready to write unlock ...");
                lock.readLock().unlock();

            },"read_0" + i).start();
        }
    }
}

