package indi.sword.util.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 
 * 
 * 创建线程有4种方式： 继承Thread、实现Runnable、实现Callable、使用线程池
 * 
 * 执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。  FutureTask 是  Future 接口的实现类
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 3, 2017 12:38:26 PM
 */
public class _06_TestCallable {

	public static void main(String[] args) throws Exception{

		CountDownLatch latch = new CountDownLatch(4);

//		MyThread_callable mt = new MyThread_callable(latch);
//
//		//1.执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。
//		FutureTask<Integer> result = new FutureTask<>(mt);
//
//		new Thread(result).start();

		long begin = System.currentTimeMillis();
		// 1、创建线程池
		final ExecutorService pool = Executors.newFixedThreadPool(4);

		List<Callable<Integer>> partitions = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			partitions.add(new MyThread_callable(latch));
		}

		List<Future<Integer>> resultFromPart = pool.invokeAll(partitions);
//		for (int i = 0; i < resultFromPart.size(); i++) {
//
//			int result = resultFromPart.get(i).get();
//			System.out.println(result);
//		}


		//2.接收线程运算后的结果
//		try {
//			Integer sum = result.get(); //FutureTask 可用于 闭锁
//			System.out.println(sum);
//
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("--- ok --- ");

		long end = System.currentTimeMillis();
		System.out.println(end - begin);
		pool.shutdown();


	}
	
	
}

class MyThread_callable implements Callable<Integer> {

	private CountDownLatch latch;

	public MyThread_callable(CountDownLatch latch){
		this.latch = latch;
	}

	@Override
	public Integer call() throws Exception {

		int sleepMills = new Random().nextInt(10000);
		System.out.println(Thread.currentThread().getName() + "," + sleepMills + "ms");
		Thread.sleep(sleepMills);
		int sum = 0;
		try {
			for (int i = 0; i <= 99999; i++) {
                sum += i;
            }
		} finally {
			if (sum == 704982704) {
				System.out.println(latch.getCount());
				while (latch.getCount() != 0) {
					latch.countDown();
				}
			}
		}
		return sum;

	}

}