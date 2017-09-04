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
		Saler saler = new Saler();
		
		for(int i = 0;i < 2;i++){
			new Thread(new Productor(saler)).start();
		}
		
		for(int i = 0;i < 2;i++){
			new Thread(new Consumer(saler)).start();
		}
		
	}
}

class Saler {

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
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + "消费了，现存量" + --number);
		this.notifyAll();
	}

}

class Productor implements Runnable {

	private Saler saler;

	public Productor(Saler saler) {
		this.saler = saler;
	}

	@Override
	public void run() { //生产20次
		for(int i = 0; i < 20 ;i++){
			saler.get();
		}
	}
}

class Consumer implements Runnable {

	private Saler saler;

	public Consumer(Saler saler) {
		this.saler = saler;
	}

	@Override
	public void run() { //消费20次
		for(int i = 0; i < 20 ;i++){
			saler.sale();
		}
	}

}