package indi.sword.util.concurrent;


import java.util.Scanner;
import java.util.concurrent.locks.Condition;
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
public class _12_02_TestCondition {
	public static void main(String[] args) throws Exception{
		LockDemo lockDemo = new LockDemo();
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				try {
					lockDemo.await();
				} catch (Exception e) {
					e.printStackTrace();
				}
			},"A" + i).start();
		}
		// 确保上面代码先执行
		Thread.sleep(1000);
		Scanner sc = new Scanner(System.in);
		System.out.println("点击任意键唤醒线程 ...");
		sc.nextLine();

		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				lockDemo.signal();

			},"B" + i).start();
		}
	}
	static class LockDemo{
		ReentrantLock lock = new ReentrantLock(false);
		Condition condition = lock.newCondition();
		public void await() throws Exception{
			System.out.println(Thread.currentThread().getName() + ",condition ready to lock ===");
			lock.lock();
			System.out.println(Thread.currentThread().getName() + ",condition ready to await ");
			condition.await();
			System.out.println(Thread.currentThread().getName() + ",condition ready to unlock ===");
			lock.unlock();
		}
		public void signal(){
			System.out.println(Thread.currentThread().getName() + ",condition ready to lock ");
			lock.lock();
			System.out.println(Thread.currentThread().getName() + ",condition ready to signal ");
			condition.signal();
			System.out.println(Thread.currentThread().getName() + ",condition ready to unlock ");
			lock.unlock();
		}
	}
}
