package indi.sword.util.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多线程下的 生产者与消费者 案例,使用 Lock Condition替换
 * 
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 3, 2017 4:24:52 PM
 */
public class _11_TestProductorAndConsumerLock {

	public static void main(String[] args){
		Saler_Lock saler = new Saler_Lock();
		
		for(int i = 0;i < 2;i++){
			new Thread(new Productor_Lock(saler)).start();
		}
		
		for(int i = 0;i < 2;i++){
			new Thread(new Consumer_Lock(saler)).start();
		}
		
	}
}

class Saler_Lock {

	private int number = 0;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	/**
	 * 进货 接收生产者的生产
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 3, 2017 4:26:46 PM
	 */
	public void get() {

		lock.lock();
		try {
			while (number >= 5) { // 仓库大小，现在设置为 1
				System.out.println("库存已满...");
				try {
					condition.await();  // await -->  wait 
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			System.out.println(Thread.currentThread().getName() + "生产了，现存量" + ++number);
			condition.signalAll(); // signalAll --> notifyAll 
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
	}

	/**
	 * 卖货 相当于消费者帮忙消费
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 3, 2017 4:26:40 PM
	 */
	public void sale() {
		
		lock.lock();
		try {
			while (number <= 0) {
				System.out.println("缺货...");

				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + "消费了，现存量" + --number);
			condition.signalAll();
		} catch (Exception e) {
		} finally{
			lock.unlock();
		}
	}

}

class Productor_Lock implements Runnable {

	private Saler_Lock saler;

	public Productor_Lock(Saler_Lock saler) {
		this.saler = saler;
	}

	@Override
	public void run() { //生产20次
		for(int i = 0; i < 20 ;i++){
			saler.get();
		}
	}
}

class Consumer_Lock implements Runnable {

	private Saler_Lock saler;

	public Consumer_Lock(Saler_Lock saler) {
		this.saler = saler;
	}

	@Override
	public void run() { //消费20次
		for(int i = 0; i < 20 ;i++){
			saler.sale();
		}
	}

}