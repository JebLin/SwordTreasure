package indi.sword.util.concurrent;

import java.util.concurrent.CountDownLatch;

/*
 * @Description
 * CountDownLatch ：闭锁，在完成某些运算是，只有其他所有线程的运算全部完成，当前运算才继续执行.
 * 					可以用于计算数量，平均值等等等等
 * 
 * @author rd_jianbin_lin
 * @Date Sep 2, 2017 7:20:01 PM
 */
public class TestCountDownLatch {

	public static void main(String[] args) {
		final CountDownLatch latch = new CountDownLatch(50);
		MyThread_CountDownLatch ld = new MyThread_CountDownLatch(latch);

		long start = System.currentTimeMillis();

		for (int i = 0; i < 50; i++) {
			new Thread(ld).start();
		}

		try {
			latch.await();
		} catch (InterruptedException e) {
		}

		long end = System.currentTimeMillis();

		System.out.println("耗费时间为：" + (end - start) + "ms");
	}

}

class MyThread_CountDownLatch implements Runnable {

	private CountDownLatch latch;

	public MyThread_CountDownLatch(CountDownLatch latch) {
		this.latch = latch;
	}

	@Override
	public void run() {

		try {
			for (int i = 0; i < 50000; i++) {
				if (i % 2 == 0) {
					System.out.println(i);
				}
			}
		} finally {
			latch.countDown();
		}

	}

}