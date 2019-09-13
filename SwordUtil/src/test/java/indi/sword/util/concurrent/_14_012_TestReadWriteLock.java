//package indi.sword.util.concurrent;
//
//import java.util.Scanner;
//import java.util.concurrent.locks.ReadWriteLock;
//import java.util.concurrent.locks.ReentrantReadWriteLock;
//
///*
// * 1. ReadWriteLock : 读写锁
// *
// * 情形： 有些人不喜欢产生 脏读，于是在读写上面加了Synchronized
// *
// * 写写/读写 需要“互斥”
// * 只要有人在读，你就不能写，必须得等到全部人 都不读了你才能写
// * 只要有人在写，你就不能读，写完你再读
// *
// * 读读 不需要互斥
// *
// */
//public class _14_07_TestReadWriteLock {
//	public static void main(String[] args) throws Exception{
//		ReadWriteDemo rw = new ReadWriteDemo();
//
//		new Thread(() -> {
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			rw.read();
//		},"Read:").start();
//
//		Thread.sleep(1000);
//
//		new Thread(() -> {
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			rw.write(20);
//		},"Write:").start();
//
//	}
//
//}
////
////class ReadWriteDemo {
////
////	private int number = 0;
////
////	private ReadWriteLock lock = new ReentrantReadWriteLock();
////
////	public void write(int num) {
////		System.out.println(Thread.currentThread().getName() + " wait to lock ...");
////		lock.writeLock().lock();
////		System.out.println(Thread.currentThread().getName() + " get the lock ...");
////
////		try {
////			this.number = num;
////			System.out.println(Thread.currentThread().getName() + "-----------------");
////			System.out.println("点击任意键唤醒线程 ...");
////			Scanner sc = new Scanner(System.in);
////			sc.nextLine();
////		} catch (Exception e) {
////			e.printStackTrace();
////		} finally {
////			System.out.println(Thread.currentThread().getName() + " ready to unlock ...");
////			lock.writeLock().unlock();
////		}
////	}
////
////	public int read() {
////		System.out.println(Thread.currentThread().getName() + " wait to lock ...");
////		lock.readLock().lock();
////		System.out.println(Thread.currentThread().getName() + " get the lock ... ===");
////		System.out.println("点击任意键唤醒线程 ...");
////		Scanner sc = new Scanner(System.in);
////		sc.nextLine();
////		try {
////			Thread.sleep(1000);
////		} catch (Exception e) {
////			e.printStackTrace();
////		} finally {
////			System.out.println(Thread.currentThread().getName() + " ready to unlock ...");
////			lock.readLock().unlock();
////		}
////		return number;
////	}
////}
