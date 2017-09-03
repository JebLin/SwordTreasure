package indi.sword.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

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
public class TestCallable {

	public static void main(String[] args){
		
		MyThread_callable mt = new MyThread_callable();
		
		//1.执行 Callable 方式，需要 FutureTask 实现类的支持，用于接收运算结果。
		FutureTask<Integer> result = new FutureTask<>(mt);
		
		new Thread(result).start();
		
		//2.接收线程运算后的结果
		try {
			Integer sum = result.get(); //FutureTask 可用于 闭锁
			System.out.println(sum);
			System.out.println("---");
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

class MyThread_callable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		int sum = 0;
		for (int i = 0; i <= 99999; i++) {
			sum += i;
		}
		return sum;

	}

}