package indi.sword.util.concurrent;

/*
 * 题目：判断打印的 "one" or "two" ？
 * 
 * 1. 两个普通同步方法，两个线程，标准打印， 打印? //one  two
 * 2. 新增 Thread.sleep() 给 getOne() ,打印? //one  two
 * 3. 新增普通方法 getThree() , 打印? //three  one   two
 * 4. 两个普通同步方法，两个 Number 对象，打印?  //two  one
 * 5. 修改 getOne() 为静态同步方法，打印?  //two   one （重点）
 * 6. 修改两个方法均为静态同步方法，一个 Number 对象?  //one   two
 * 7. 一个静态同步方法，一个非静态同步方法，两个 Number 对象?  //two  one  （重点）
 * 8. 两个静态同步方法，两个 Number 对象?   //one  two
 * 
 * 线程八锁的关键：
 * ①非静态方法的锁默认为  this,  静态方法的锁为 对应的 Class 实例
 * ②某一个时刻内，只能有一个线程持有锁，无论几个方法。
 * 
 *  static 类锁
 *  this   对象锁
 *  不互斥
 *  
 */

// 7. 一个静态同步方法，一个非静态同步方法，两个 Number 对象?  //two  one 对比5
public class _15_07_TestSynchronized {
	public static void main(String[] args) throws Exception{
		Number07 number = new Number07();
		Number07 number2 = new Number07();

		new Thread(() -> number.getOne(),"A").start();
		Thread.sleep(50);
		new Thread(() -> number2.getTwo(),"B").start();
	}
}

class Number07{
	public static synchronized void getOne(){//Number.class
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		System.out.println(Thread.currentThread().getName() + "_one");
	}
	public synchronized void getTwo(){//this
		System.out.println(Thread.currentThread().getName() + "_two");
	}
}