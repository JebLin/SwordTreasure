package indi.sword.util.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/*
 * @Description
 * 一、i++ 的原子性问题：
 * 		  int i = 10;
 * 		  i = i++; //10
 * 
 * 		  i++ :
 * 			oldvalue = i;
 * 			i = i + 1;
 * 			return oldvalue;
 * 
 * 		  ++i :
 * 			oldvalue = i;
 * 			i = i + 1;
 * 			return i;
 * 
 * 二、原子变量：在 java.util.concurrent.atomic 包下提供了一些原子变量。
 * 		1. volatile 保证内存可见性
 * 		2. CAS（Compare-And-Swap） 算法保证数据变量的原子性
 * 			CAS 算法是硬件对于并发操作的支持
 * 			CAS 包含了三个操作数：
 * 			①内存值  V
 * 			②预估值  A
 * 			③更新值  B
 * 			当且仅当 V == A 时， V = B; 否则，不会执行任何操作。
 * 
 * @author rd_jianbin_lin
 * @Date Sep 2, 2017 7:20:01 PM
 */
public class _02_01_TestAtomic {

	public static void main(String[] args) {
		MyThread_Atomic mt = new MyThread_Atomic();
		
		for (int i = 0; i < 10; i++) {
			new Thread(mt).start();
		}
	}
	
}

class MyThread_Atomic implements Runnable{
	
	// volatile 虽然是让线程每次去直接操作主存中的数据，只能保证读到了最新的，但是有可能 A B C 读了同一个数值，卖出了多余的票
//	private volatile int number = 0;
	
	
	//类似的还有 AtomicFloat AtomicDouble等等
	private AtomicInteger number = new AtomicInteger(5);

	@Override
	public void run() {
		
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int number = getNumber();
		if(number > 0){
			saleTicket();
			System.out.println("name -> " + Thread.currentThread().getName() + ",sale the ticket ... " + "number == " + number);
		}else{
			System.out.println("the ticket is over ...");
		}
	}
	
	public void saleTicket(){
		number.getAndDecrement();
	}
	
	public int getNumber(){
		return number.get();
	}
	
}
