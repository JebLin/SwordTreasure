package indi.sword.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用JDK自带的监控工具来监控我们创建的线程数量，运行一个不终止的线程，创建指定量的线程，来观察：
 * 打开D:\SOFTWARE\CODE\JDK1.8\INSTALL\bin\jconsole.exe
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Sep 4, 2017 7:37:58 PM
 */
public class _18_TestJConsole {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newCachedThreadPool();
		
		for(int i = 0 ; i < 88 ;i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pool.execute(new Runnable(){
				@Override
				public void run() {
					while(true){
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName());
					}
				}
			});
			
		}
	
		
	}
}
