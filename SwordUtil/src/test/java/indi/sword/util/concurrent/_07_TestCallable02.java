package indi.sword.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 用callable 实现闭锁
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 3, 2017 1:18:41 PM
 */
public class _07_TestCallable02 {

	public static void main(String[] args){
		MyThread_callable02 mt = new MyThread_callable02();
		FutureTask<Long> result = new FutureTask<>(mt);
		

		long startTime = System.currentTimeMillis();
		new Thread(result).start();
		
		try {
			result.get(); 
			long endTime = System.currentTimeMillis();
			System.out.println("it cost -> " + (endTime - startTime));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}


class MyThread_callable02 implements Callable<Long>{

	@Override
	public Long call() throws Exception {
		
		for(int i = 0; i < 15;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					System.out.println("ThreadName -> " + Thread.currentThread().getName());
				}
			}).start();
		}
		return null;
	}
	
}
