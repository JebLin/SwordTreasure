package indi.sword.util.concurrent;

/**
 * 多线程下的 生产者与消费者 案例
 * 
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 3, 2017 4:24:52 PM
 */
public class _10_TestProductorAndConsumer {

	public static void main(String[] args){
		Salesperson salesperson = new Salesperson();
		
		for(int i = 0;i < 2;i++){
			new Thread(new Producer(salesperson)).start();
		}
		
		for(int i = 0;i < 2;i++){
			new Thread(new Consumer(salesperson)).start();
		}
		
	}
}

class Salesperson {

	private int number = 0;

	/**
	 * 进货 接收生产者的生产
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 3, 2017 4:26:46 PM
	 */
	public synchronized void get() {

		while (number >= 5) { // 仓库大小，现在设置为 1
			System.out.println("库存已满...");
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println(Thread.currentThread().getName() + "生产了，现存量" + ++number);
		this.notifyAll();
	}

	/**
	 * 卖货 相当于消费者帮忙消费
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 3, 2017 4:26:40 PM
	 */
	public synchronized void sale() {
		while (number <= 0) {
			System.out.println("缺货...");

			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + "消费了，现存量" + --number);
		this.notifyAll();
	}

}

class Producer implements Runnable {

	private Salesperson salesperson;

	public Producer(Salesperson salesperson) {
		this.salesperson = salesperson;
	}

	@Override
	public void run() { //生产20次
		for(int i = 0; i < 20 ;i++){
			salesperson.get();
		}
	}
}

class Consumer implements Runnable {

	private Salesperson salesperson;

	public Consumer(Salesperson salesperson) {
		this.salesperson = salesperson;
	}

	@Override
	public void run() { //消费20次
		for(int i = 0; i < 20 ;i++){
			salesperson.sale();
		}
	}

}