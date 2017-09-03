package indi.sword.util.concurrent;

public class CasCounter {

	private int value = 10;

	public synchronized int getValue() {
		return value;
	}

	public synchronized int compareAndSwap(int expectedValue, int newValue) {
		if (value == expectedValue) {
			value = newValue;
		}
		return value;
	}

	/**
	 * 场景，给多个线程一个初始值，每个线程 +1 前 会有个预估值，如果是预估值那么就直接+1，如果不是，就把预估值提升一下。
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Aug 30, 2017 10:32:43 AM
	 */
	public int increament() {
		int oldValue = getValue();
		while (compareAndSwap(oldValue, oldValue + 1) != oldValue) {
			oldValue = getValue() + 1;
		}
		return oldValue + 1;
	}
	
	public static void main(String[] args){
		
		CasCounter cc = new CasCounter();
		for(int i = 0; i < 3 ;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					cc.increament();
				}
			}).start();
		}
		
		try {
			Thread.sleep(1000);
			System.out.println(cc.value);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
