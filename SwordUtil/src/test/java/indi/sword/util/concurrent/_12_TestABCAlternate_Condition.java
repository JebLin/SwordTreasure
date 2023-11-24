package indi.sword.util.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * 编写一个程序，开启 3 个线程，这三个线程的 ID 分别为 A、B、C，每个线程将自己的 ID 在屏幕上打印 10 遍，要求输出的结果必须按顺序显示。
 * 如：ABCABCABC…… 依次递归
 * 
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 3, 2017 4:58:18 PM
 */
public class _12_TestABCAlternate_Condition {
	public static void main(String[] args) throws Exception{

		AlternateDemo demo = new AlternateDemo();

		new Thread(() -> {
			for (int i = 1; i < 5; i++) {
				demo.LoopC(i);
			}
		}, "C").start();

		Thread.sleep(15000);
//
//		new Thread(() -> {
//			for (int i = 1; i < 5; i++) {
//				demo.LoopB(i);
//			}
//		}, "B").start();
//
//		Thread.sleep(15000);
//		new Thread(() -> {
//			for (int i = 1; i < 5; i++) {
//				demo.LoopA(i);
//			}
//		}, "A").start();
	}
}

class AlternateDemo {
	private AtomicInteger number = new AtomicInteger(1); //当前正在执行线程的标记
	private ReentrantLock lock = new ReentrantLock();
	private Condition condition1 = lock.newCondition();
	private Condition condition2 = lock.newCondition();
	private Condition condition3 = lock.newCondition();
	/**
	 * @param totalLoop : 循环第几轮
	 */
	public void LoopA(int totalLoop) {

		lock.lock();
		try {
			//1. 判断
			if (number.intValue() != 1) {
				condition1.await();
			}
			//2. 打印
			System.out.println(Thread.currentThread().getName() + "-" + totalLoop);
			//3. 唤醒
			number.incrementAndGet();
			condition2.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void LoopB(int totalLoop) {
		lock.lock();
		try {
			if (number.intValue() != 2) {
				condition2.await();
			}
			System.out.println(Thread.currentThread().getName() + "-" + totalLoop);
			number.incrementAndGet();
			condition3.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public void LoopC(int totalLoop) {
		lock.lock();
		try {
			if (number.intValue() != 3) {
				condition3.await();
			}
			System.out.println(Thread.currentThread().getName() + "-" + totalLoop);
			System.out.println("--------------------------------------------------");
			number = new AtomicInteger(1);
			condition1.signal();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}