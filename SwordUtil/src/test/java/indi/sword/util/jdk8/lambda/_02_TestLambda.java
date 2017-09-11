package indi.sword.util.jdk8.lambda;

import java.util.function.Consumer;

import org.junit.Test;

public class _02_TestLambda {
	
	@Test
	public void test1(){
		
		int num = 0;
		
		Runnable r = new Runnable(){

			@Override
			public void run() {
				System.out.println("Hello world! " + num);
			}
		};
		
		r.run();
		System.out.println("-------------------------------");
		
		Runnable r1 = () -> System.out.println("Hello Lambda !");
		r1.run();
	}
	
	@Test
	public void test2(){
		Consumer<String> con = x -> System.out.println(x);
		con.accept("国泰平安");
	}
	
}
