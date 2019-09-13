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
// 场景三：thread1 在 read，thread2-N 准备来 write
public class _14_03_TestReadWriteLock {
	public static void main(String[] args) throws Exception{
		ReadWriteLock lock = new ReentrantReadWriteLock();
		new Thread(() -> {
			System.out.println(Thread.currentThread().getName() + " wait to read lock  ...");
			lock.readLock().lock();
			System.out.println(Thread.currentThread().getName() + " get the read lock ... ===");

			System.out.println("点击任意键唤醒线程 ...");
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
			System.out.println(Thread.currentThread().getName() + " ready to unlock read ...");
			lock.readLock().unlock();

		}).start();

		Thread.sleep(5000);

		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + " wait to lock write ...");
				lock.writeLock().lock();
				System.out.println(Thread.currentThread().getName() + " get the lock write ...");
				System.out.println(Thread.currentThread().getName() + " ready to unlock write ...");
				lock.writeLock().unlock();
			}).start();
		}
	}
}

