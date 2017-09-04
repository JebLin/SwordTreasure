package indi.sword.util.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 一、用于解决多线程安全问题的方式：
 * 
 * synchronized:隐式锁
 * 1. 同步代码块
 * 2. 同步方法
 * 
 * jdk 1.5 后：
 * 3. 同步锁 Lock
 * 注意：是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
 * 
 * 
 * 可以用 lock ， 也可以用 Synchronzied ,但是呢，
 * 1）获取锁的线程执行完了该代码块，然后线程释放对锁的占有；
　　2）线程执行发生异常，此时JVM会让线程自动释放锁.
   3） 线程假如由于IO阻塞（下面用sleep模拟等待），那么其他线程都没法获得锁。   如果是用lock的话，可以设置超时等待时间,还可以插队
   
 *  Lock可以知道线程有没有成功获取到锁。这个是synchronized无法办到的。
 */
public class _08_TestLock_synchronzied {

	
	public static void main(String[] args) {
		final int NUM = 1000;

		CountDownLatch latch = new CountDownLatch(NUM);
		MyThread_lock mt = new MyThread_lock(latch);
//		MyThread_Synchronzied mt = new MyThread_Synchronzied(latch);
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < NUM; i++) {
			new Thread(mt, "Thread-" + i).start();
		}

		try {
			latch.await();
			long endTime = System.currentTimeMillis();
			System.out.println(endTime - startTime + "millisecond"); //38230   37718

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class MyThread_lock implements Runnable {

	private int ticket = 1000;

	private CountDownLatch latch;

	private Lock lock = new ReentrantLock(); // 底层使用 CAS 算法  锁住对象
	
	
	public MyThread_lock(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {
		
		lock.lock(); // 上锁
		try {
			if (ticket > 0) {
				if(Thread.currentThread().getName().equals("Thread-100")){
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread().getName() + " 拿到锁头，售票，余票为：" + --ticket);
			}
		} finally {
			System.out.println(Thread.currentThread().getName() + "释放锁 ");
			lock.unlock(); // 释放锁
			latch.countDown();
		}
	}
}

/**
 * 用一下 Synchronized，试一下效率
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 3, 2017 2:10:20 PM
 */
class MyThread_Synchronzied implements Runnable {
	private int ticket = 1000;

	private CountDownLatch latch;

	public MyThread_Synchronzied(CountDownLatch latch){
		this.latch = latch;
	}

	@Override
	public void run() {
		synchronized(this){
			try {
				if(Thread.currentThread().getName().equals("Thread-100")){
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (ticket > 0) {
					System.out.println(Thread.currentThread().getName() + " 完成售票，余票为：" + --ticket);
				}
			} finally {
				latch.countDown();
			}
		}
	}

}