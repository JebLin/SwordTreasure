package indi.sword.util.jdk8.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by rd_jianbin_lin on 2017/9/14.
 *
 * JAVA 内置四大核心函数式接口
 *
 * Consumer<T> : 消费型接口
 *  void accept(T t);
 *
 * Supplier<T> : 供给型接口
 *  T get();
 *
 * Function<T,R> : 函数型接口
 *  R apply(T t);
 *
 * Predicate<T> : 断言型接口
 *  boolean test(T t);
 */
public class _03_TestLambda_4Interface {

    //Predicate<T> 断言型接口
    @Test
    public void test_Predicate(){
        List<String> list = Arrays.asList("AA","BB","CC");
        List<String> returnList = filter(list, (s -> s.startsWith("A")));
        returnList.forEach(System.out::println);
    }

    //需求：将满足条件的字符串，放入集合中
    public List<String> filter(List<String> list, Predicate<String> predicate){
        List<String> returnList = new ArrayList<>();
        for(String str:list){
            if(predicate.test(str)){ // boolean test(T t);
                returnList.add(str);
            }
        }
        return returnList;
    }


    // Function<T, R> 函数式接口
    @Test
    public void test_Function(){
        System.out.println(strHandle("123456",(str) -> str.substring(3)));
        System.out.println(strHandle("123456",(s -> s.replace("1","2"))));
    }

    public String strHandle(String str, Function<String,String> function){
        return function.apply(str);  // R apply(T t);
    }

    // Supplier<T> 供给型接口
    @Test
    public void test_Supplier(){
        List<Integer> list = supply(10,() -> (int)(Math.random() * 100));
        list.forEach(System.out::println);
    }

    public List<Integer> supply(int num, Supplier<Integer> supplier){
        List<Integer> list = new ArrayList<>();
        for(int i =0 ;i < num;i++){
            list.add(supplier.get()); // T get();
        }
        return list;
    }


    // Consumer<T> 消费型接口
    @Test
    public void test_consumer(){
        consum(100, (m) -> System.out.println("消费了 ->" + m));
    }

    public void consum(double money, Consumer<Double> consumer){
        consumer.accept(money); // void accept(T t);
    }

}
