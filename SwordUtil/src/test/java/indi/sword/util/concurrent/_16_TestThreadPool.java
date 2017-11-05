package indi.sword.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了相应速度。
 * 
 * 二、线程池的体系结构：
 * Executor:
 * The {@code Executor} implementations provided in this package
 * implement {@link ExecutorService}, which is a more extensive
 * interface.  The {@link ThreadPoolExecutor} class provides an
 * extensible thread pool implementation. The {@link Executors} class
 * provides convenient factory methods for these Executors.
 * 
 * java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 * 		||--**ExecutorService 子接口：线程池的主要接口
 * 			||--ThreadPoolExecutor 线程池的实现类
 * 			||--** ScheduledExecutorService 子接口：负责线程的调度
 * 				||-- ScheduledThreadPoolExecutor ：extends ThreadPoolExecutor，implements ScheduledExecutorService
 * 
 * 三、工具类 ： Executors
 * <ul>
 *   <li> Methods that create and return an {@link ExecutorService}
 *        set up with commonly useful configuration settings.
 *   <li> Methods that create and return a {@link ScheduledExecutorService}
 *        set up with commonly useful configuration settings.
 *   <li> Methods that create and return a "wrapped" ExecutorService, that
 *        disables reconfiguration by making implementation-specific methods
 *        inaccessible.
 *   <li> Methods that create and return a {@link ThreadFactory}
 *        that sets newly created threads to a known state.
 *   <li> Methods that create and return a {@link Callable}
 *        out of _other closure-like forms, so they can be used
 *        in execution methods requiring {@code Callable}.
 * </ul>
 * 
 * 使用最多的就是 上面的<li> 1
 * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。 
 * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。  
 * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。 
 * 
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 4, 2017 4:20:01 PM
 */
public class _16_TestThreadPool {
	public static void main(String[] args) throws Exception{

		// -------------------------- 实现Runnable ---------------------------
		//1、创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(10);
		
		//2、为线程池中的线程分配任务
		MyThread_pooledThread mt = new MyThread_pooledThread();
		
		for(int i = 0;i < 20;i++){
			pool.submit(mt);
		}
		
		//3、关闭线程池
		pool.shutdown();
		
		// -------------------------- 实现Callable ---------------------------
		//1、创建线程池
		ExecutorService pool2 = Executors.newFixedThreadPool(10);
		
		//2、为线程池中的线程分配任务
		MyThread_pooledThread_callable mt2 = new MyThread_pooledThread_callable();
		
//		Future<Integer> future = null;
		for(int i = 0;i < 20;i++){
			Future<Integer> future = pool2.submit(mt2);
			System.out.println(future.get());
		}
		
		//3、关闭线程池
		pool2.shutdown();
	}
}



class MyThread_pooledThread implements Runnable {
	@Override
	public void run() {
		System.out.println("name -> " + Thread.currentThread().getName());
	}
}

class MyThread_pooledThread_callable implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		return 10;
	}
}

