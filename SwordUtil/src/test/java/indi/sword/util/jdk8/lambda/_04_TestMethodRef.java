package indi.sword.util.jdk8.lambda;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/**
 * 一、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 * 			  （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 *
 * 1. 对象的引用 :: 实例方法名
 *
 * 2. 类名 :: 静态方法名
 *
 * 3. 类名 :: 实例方法名
 *
 * 注意：
 * 	 ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * 	 ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 *
 * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 *
 * 1. 类名 :: new
 *
 * 三、数组引用
 *
 * 	类型[] :: new;
 * 、
 * Created by rd_jianbin_lin on 2017/9/14.
 *
 */
public class _04_TestMethodRef {

    // 数组引用
    @Test
    public void test7_Array(){
        Function<Integer,String[]> function = (len) -> new String[len];
        String[] strs = function.apply(100);
        System.out.println(strs.length);

        Function<Integer,String[]> function2 = String[] ::new;
        String[] strs2 = function.apply(100);
        System.out.println(strs2.length);

        Function<Integer,_01_Employee[]> function3 =  (len) -> new _01_Employee[len];
        _01_Employee[] employees = function3.apply(100);
        System.out.println(employees.length);
        Function<Integer,_01_Employee[]> function4=  _01_Employee[] :: new;
        _01_Employee[] employees2 = function4.apply(100);
        System.out.println(employees2.length);

    }

    // 构造器引用  Constructor
    // 构造器的参数列表，需要与函数式接口中参数列表保持一致！
    @Test
    public void test6_constructor(){
        Supplier<_01_Employee> supplier = () -> new _01_Employee();
        System.out.println(supplier.get());

        Supplier<_01_Employee> supplier2 = _01_Employee :: new;
        System.out.println(supplier2.get());

        Function<String,_01_Employee> function = (name) -> new _01_Employee(name);
        System.out.println(function.apply("rd_jianbin_lin"));
        Function<String,_01_Employee> function2 = _01_Employee :: new; // 传入1个参数
        System.out.println(function2.apply("rd_jianbin_lin"));

        BiFunction<String,Integer,_01_Employee> biFunction = (name,age) -> new _01_Employee(name,age);
        System.out.println(biFunction.apply("rd_jianbin_lin",18));
        BiFunction<String,Integer,_01_Employee> biFunction2 = _01_Employee :: new; // 传入2个参数
        System.out.println(biFunction2.apply("rd_jianbin_lin",18));
    }


    // 类名 :: 实例方法名
    // 若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，
    // 格式： ClassName::MethodName
    @Test
    public void test5_class_object_Method(){
        BiPredicate<String,String> bp = (x,y) -> x.equals(y); // 注意：equals是实例方法
        System.out.println(bp.test("abcde","abcde"));

        System.out.println("-----------------------------------");
        BiPredicate<String,String> bp2 = String::equals;
        System.out.println(bp2.test("abc","abc"));

        System.out.println("-----------------------------------");
        Function<_01_Employee,String> fun = (e) -> e.show();
        System.out.println(fun.apply(new _01_Employee()));
        System.out.println("-----------------------------------");

        Function<_01_Employee,String> fun2 = _01_Employee::show;
        System.out.println(fun2.apply(new _01_Employee()));

    }

    // 类名 :: 静态方法名
    @Test
    public void test4_class_static_Method(){
        Comparator<Integer> com = (x,y) -> Integer.compare(x,y);
        System.out.println("-------------------------------");
        Comparator<Integer> com2 = Integer::compare;
    }

    @Test
    public void test3_class_static_Method(){
        BiFunction<Double,Double,Double> fun = (x,y) -> Math.max(x,y);
        System.out.println(fun.apply(10.8,20.8));

        System.out.println("--------------------------------");

        BiFunction<Double,Double,Double> fun2 = Math :: max;
        System.out.println(fun2.apply(11.1,22.2));

    }

    // 对象的引用 :: 实例方法名
    @Test
    public void test2_objectMethod(){
        _01_Employee emp = new _01_Employee(111,"zhangsan",18,888.88);

        Supplier<String> supplier = () -> emp.getName();
        System.out.println(supplier.get());

        System.out.println("--------------------------");

        Supplier<String> supplier2 = emp::getName;
        System.out.println(supplier2.get());
    }

    @Test
    public void test1_objectMethod(){
        PrintStream ps = System.out;
        Consumer<String> consumer = (str) -> ps.println(str);
        consumer.accept("Hello world !");

        System.out.println("--------------------------------");

        Consumer<String> consumer2 = ps :: println;
        consumer2.accept("GG GG GG");

        Consumer<String> consumer3 = System.out::println;
    }

}
