package indi.sword.util.concurrent;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 1. ReadWriteLock : 读写锁
 * 
 * 情形： 有些人不喜欢产生 脏读，于是在读写上面加了Synchronized
 * 
 * 写写/读写 需要“互斥”   
 * 只要有人在读，你就不能写，必须得等到全部人 都不读了你才能写
 * 只要有人在写，你就不能读，写完你再读
 * 
 * 读读 不需要互斥
 * 
 */
public class _14_TestReadWriteLock {
	public static void main(String[] args){
		ReadWriteDemo rw = new ReadWriteDemo();
		
		// 500个人读  ，1 个人写   ,如果不加锁，会造成读取情况不一致
		for(int i = 0; i < 500 ; i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					rw.read();
				}
				
			},"Read:" + i).start();
		}
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rw.write(20);
			}
		},"Write:").start();
		
	}
	
}

class ReadWriteDemo{
	
	private int number = 0;
	
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	
	public void write(int num){
		lock.writeLock().lock();
		
		
		try {
			this.number = num;
			System.out.println(Thread.currentThread().getName() + "-----------------");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.writeLock().unlock();
		}
	}
	
	public int read(){
		
		lock.readLock().lock();
		
		try {
			System.out.println(Thread.currentThread().getName() + "-" + number);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			lock.readLock().unlock();
		}
		return number;
	}
	
	
}
