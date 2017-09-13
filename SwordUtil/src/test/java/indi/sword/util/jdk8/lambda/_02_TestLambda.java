package indi.sword.util.jdk8.lambda;

import java.util.*;
import java.util.function.Consumer;

import com.sun.org.apache.xpath.internal.SourceTree;
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

	@Test
	public void test3(){
		Comparator<Integer> com = (x, y) -> {
			System.out.println("函数式接口");
			return Integer.compare(x,y);
		};
	}


	@Test
	public void test4(){
		Comparator<Integer> com = (x,y) -> Integer.compare(x,y);
	}

	@Test
	public void test5(){
//		String[] strs;
//		strs = {"aaa", "bbb", "ccc"};

		List<String> list = new ArrayList<>();

		show(new HashMap<>()); // 可以这样传入泛型Map
	}

	public void show(Map<String, Integer> map){

	}
	
	//需求：对一个数进行运算
	@Test
	public void test6(){
		Integer sum  = operation(100, (x) -> x * x);
		System.out.println(sum);
		System.out.println(operation(100, (x) -> x + x));
	}

	public Integer operation(Integer sum,_02_MyFun myFun){
		return myFun.getValue(sum);
	}
}
