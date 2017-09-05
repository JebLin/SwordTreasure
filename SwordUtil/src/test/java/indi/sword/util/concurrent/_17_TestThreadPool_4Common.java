 package indi.sword.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。 (线程可复用)
 * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。 
 * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。 
 * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 * 
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 4, 2017 7:08:05 PM
 */
public class _17_TestThreadPool_4Common {
	public static void main(String[] args) throws Exception {
//		method_newCachedThreadPool();
//		method_newFixedThreadPool();
//		newScheduledThreadPool();
//		method_newSingleThreadExecutor();
		System.out.println("并发数：" + Runtime.getRuntime().availableProcessors());
	}

	/**
	 * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
	 * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 4, 2017 7:32:33 PM
	 */
	public static void method_newCachedThreadPool() {
		// 1、创建线程池
		ExecutorService pool = Executors.newCachedThreadPool();

		// 2、分配任务
		for (int i = 0; i < 10; i++) {
			final int index = i;
			try {
				Thread.sleep(index * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			pool.execute(new Runnable() {
				public void run() {
					System.out.println(Thread.currentThread().getName() + "-" + index);
				}
			});
		}
		// 3、关闭线程池
		pool.shutdown();
	}

	/**
	 * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。 
	 * 因为线程池大小为3，每个任务输出index后sleep 2秒，所以每两秒打印3个数字。
	 * 定长线程池的大小最好根据系统资源进行设置。如Runtime.getRuntime().availableProcessors() 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 4, 2017 7:32:51 PM
	 */
	public static void method_newFixedThreadPool() {
		// 1、创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(4);

		// 2、分配任务
		for (int i = 0; i < 10; i++) {
			final int index = i;
			pool.execute(new Runnable() {
				public void run() {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + "-" + index);
				}
			});
		}
		// 3、关闭线程池
		pool.shutdown();
	}

	/**
	 * 线程池配置，也就是说，当你有n个线程要跑的时候，
	 * 规定线程池里面到底能同时跑几个线程，是固定线程数，然后剩余的排队等待，
	 * 还是说，不上限，要几个有几个。等等一系列配置。
	 * 
	 * newScheduledThreadPool 创建一个定长线程池，支持定时及周期性任务执行。 
	 * 表示延迟1秒后每2秒执行一次。
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 4, 2017 7:34:13 PM
	 */
	public static void newScheduledThreadPool() {
		// 1、创建线程池
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);
		
		// 2、分配任务
		for (int i = 0; i < 2; i++) {
			final int index = i;
			pool.scheduleAtFixedRate(new Runnable(){
				@Override
				public void run() {
					
					System.out.println(Thread.currentThread().getName() + "-" + index);	
					
				}
			}, 1, 2, TimeUnit.SECONDS);
		}
	}
	
	public static void method_newSingleThreadExecutor() {
		// 1、创建线程池
		ExecutorService pool = Executors.newSingleThreadExecutor();
		// 2、分配任务
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			final int index = i;
			pool.execute(new Runnable() {
				public void run() {
					System.out.println(Thread.currentThread().getName() + "-" + index);
				}
			});
		}
		// 3、关闭线程池
		pool.shutdown();
	}
}
