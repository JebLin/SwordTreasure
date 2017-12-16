package indi.sword.util.concurrent;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/*
 * @Description
 * CopyOnWriteArrayList/CopyOnWriteArraySet : “写入并复制”
 * 注意：添加操作多时，效率低，因为每次添加时都会进行复制，开销非常的大。并发迭代操作多时可以选择。
 * 
 * @author rd_jianbin_lin
 * @Date Sep 2, 2017 7:20:01 PM
 */
public class _13_TestCopyOnWriteArrayList {

	public static void main(String[] args) {
		MyThread_CopyOnWriteArrayList mt = new MyThread_CopyOnWriteArrayList();
		
		for (int i = 0; i < 10; i++) {
			new Thread(mt).start();
		}
	}
	
}

class MyThread_CopyOnWriteArrayList implements Runnable{
	
//	private static _03_List<String> list = Collections.synchronizedList(new ArrayList<String>());
	
	private static CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
	
	static{
		list.add("AA");
		list.add("BB");
		list.add("CC");
	}

	@Override
	public void run() {
		
		//iterator的长度不变， 但是 list的长度不断加1
		Iterator<String> it = list.iterator();
		
		while(it.hasNext()){
			System.out.println(it.next());
			
			list.add("AA");
			System.out.println(list.size());
		}
		
	}
	
}