package indi.sword.util.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 模拟 CAS 算法
 * 通常将 CAS 用于同步的方式是从地址V读取值A，执行多步计算获得新值B，
 * 然后用CAS将V的值从 A改为B，如果 V处的值尚未同时更改，则CAS操作成功
 * 
 * CAS有效说明了“我认为位置 V应该包含值A了；如果包含该值，则将B放到这个位置；
 * 否则，不要更改该位置，只告诉我这个位置现在的值即可；
 * 
 * eg. SVN提交的时候，如果 位置V 是你预想的值，也就是在你之前没人改过的话，那么就可以 commit
 * 如果被改过了，告诉我那个位置，我先 update 再去commit
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Aug 30, 2017 10:35:39 AM
 */
public class _04_TestCAS_Latch {

    public static void main(String[] args) {

    	final CountDownLatch latch = new CountDownLatch(100);
    	final CAS_02 cas02 = new CAS_02();
        
    	long startTime = System.currentTimeMillis();
    	
        for (int i = 0; i < 100; i++) {
        	new Thread(new LatchThread(latch,cas02)).start();
        }
        
        try {
        	
        	// 注意： await() 如果上面 new CountDownLatch的初始值 减到了0了 就说明释放锁了，不管不顾了
			latch.await(); // 如果不加等待的话，那么就无法形成闭锁 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        long endTime = System.currentTimeMillis();
        System.out.println("it cost time : " + (endTime - startTime) + "millisecond");
    }

}

class CAS_02{
	
    private int value;

    //获取内存值
    public synchronized int get(){
        return value;
    }

    //比较
    public synchronized int compareAndSwap(int expectedValue, int newValue){
        int oldValue = value;

        if(oldValue == expectedValue){
            this.value = newValue;
        }

        return this.value;
    }

    /**
	 * 场景，给多个线程一个初始值，每个线程 +1 前 会有个预估值，如果是预估值那么就直接+1，如果不是，就把预估值提升一下。
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Aug 30, 2017 10:32:43 AM
	 */
	public int increament() {
		int oldValue = get();
		while (compareAndSwap(oldValue, oldValue + 1) != oldValue + 1) { //为了拿到预估值,如果 == 的话，说明值已经设置进去了，可以退出了
			oldValue = get(); //不断取最新的值
		}
		return value;
	}
	
	public int decreament() {
		int oldValue = get();
		while (compareAndSwap(oldValue, oldValue - 1) != oldValue - 1) { //为了拿到预估值
			oldValue = get(); //不断取最新的值
		}
		return value;
	}
}

/**
 * 实现CountDownLatch
 * 可以让主线程等待其他分线程全部结束处理完之后再执行。
 * 
 * 
 * 闭锁，在完成某些运算是，只有其他所有线程的运算全部完成，当前运算才继续执行
 * 可以用于计算商品最终数量，平均值。
 * 
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 3, 2017 11:29:03 AM
 */
class LatchThread implements Runnable{

	private CAS_02 cas02;
	
	private CountDownLatch latch;
	
	public LatchThread(CountDownLatch latch,CAS_02 cas02){
		this.latch = latch;
		this.cas02 = cas02;
	}
	
	@Override
	public void run() {

		try {
			System.out.println("Thread - " + Thread.currentThread().getName() + "," + cas02.decreament());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			latch.countDown();  //要减掉1
		}
		
	}

}