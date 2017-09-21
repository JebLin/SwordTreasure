package indi.sword.util.jdk8.lambda;

import java.util.*;
import java.util.function.Consumer;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;

/**
 * 一、Lambda 表达式的基础语法：Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符或 Lambda 操作符
 * 						    箭头操作符将 Lambda 表达式拆分成两部分：
 *
 *   左侧：Lambda表达式的参数列表
 *   右侧：Lambda表达式中所需执行的供暖，即Lambda体
 *
 *   语法格式一：无参数，无返回值
 *   	（）-> System.out.prinln("Hello world");
 *
 *   语法格式二：有一个参数，无返回值
 *   	(x) -> System.out.prinln(x);
 *   若只有一个参数，小括号可以省略不写：
 *       x  -> System.out.prinln(x);
 *
 *   语法格式三：有两个以上参数，有返回值，并且Lambda体中有多条语句
 *   	Comparator<Integer> com = (x,y) -> {
 *   	  System.out.prinln("函数式接口");
 *   	  return Integer.compare(x,y);
 *   	}
 *   	若Lambda体中只有一条语句， return 和 大括号 可以省略不写
 *		Comparator<Integer> com = (x,y) -> Integer.compare(x,y);
 *
 *	Lambda 表达式的参数列表的数据类型可以胜率不写，因为JVM编译器通过上下文可以推断出数据类型 ，即“类型推断”
 *
 *  上联：左右遇一括号省
 *  下联：左侧推断类型省
 *  横批：能省则省
 *
 * 二、Lambda 表达式需要“函数式接口”的支持
 * 函数式接口：接口中只有一个抽象方法的接口，成为函数式接口。
 * 可以使用@FunctionalInterface修饰 检查是否是函数式接口
 *
 * 只包含一个抽象方法、只包含一个抽象方法、只包含一个抽象方法
 */
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
