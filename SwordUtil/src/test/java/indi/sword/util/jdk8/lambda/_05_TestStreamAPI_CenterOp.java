package indi.sword.util.jdk8.lambda;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by rd_jianbin_lin on 2017/9/15.
 * Java8中有两大最为重要的改变。第一个是Lambda 表达式；另外一个则是Stream API(java.util.stream.*)。
 * Stream 是Java8 中处理集合的关键抽象概念，它可以指定你希望对集合进行的操作，
 * 可以执行非常复杂的查找、过滤和映射数据等操作。使用Stream API 对集合数据进行操作，
 * 就类似于使用SQL 执行的数据库查询。也可以使用Stream API 来并行执行操作。
 *
 * 流（Stream）到底是什么？
 * 是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。
 * “集合讲的是数据，流讲的是计算”
 *
 * 注意：
 * 1、Stream自己不会存储元素
 * 2、Stream不会改变原对象。相反，他们会返回一个持有结果的新Stream。
 * 3、Stream操作时延时执行的。这意味着他们会等到需要结果时才执行。
 *
 * sorted()——自然排序
 * sorted(Comparator com)——定制排序
 *
 *
 * 操作步奏：
 * 1、创建 Stream
 *      一个数据源（如：集合、数组），获取一个流
 * 2、中间操作
 *      一个中间操作链，对数据源的数据进行处理
 * 3、终止操作
 *      一个终止操作，执行中间操作练，并产生结果
 *
 *
 *
 */
public class _05_TestStreamAPI_CenterOp {

    List<_01_Employee> employees = Arrays.asList(
            new _01_Employee(11,"张三",10,11.11),
            new _01_Employee(15,"张四",30,21.11),
            new _01_Employee(14,"张五",50,11.11),
            new _01_Employee(12,"张六",20,41.11),
            new _01_Employee(18,"张七",40,21.11),
            new _01_Employee(17,"张八",20,51.11),
            new _01_Employee(16,"张九",10,61.11)
    );

    @Test
    public void test_createStream(){

        // 1、Collection提供了两个方法，stream() 与 parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); // 返回一个顺序流
        Stream<String> parallelStrean = list.parallelStream(); // 返回一个并行流

        // 2、通过Arrays 中的stream() 获取一个数据流
        Integer[] nums = new Integer[10];
        Stream<Integer> stream1 = Arrays.stream(nums);

        // 3、通过Stream类中的静态方法 of()
        Stream<Integer> stream2 = Stream.of(1,2,3,4);

        // 4、创建无限流   //iterate(final T seed, final UnaryOperator<T> f)
        // 迭代
        Stream<Integer> stream3 = Stream.iterate(0, x -> x + 2).limit(10);
        stream3.forEach(System.out::println);

        // 生成
        Stream<Double> stream4 = Stream.generate(Math::random).limit(2);
        stream4.forEach(System.out::println);

    }
    /*
      筛选与切片
        filter——接收 Lambda ， 从流中排除某些元素。
        limit——截断流，使其元素不超过给定数量。
        skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
        distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
     */
    @Test
    public void test_demo(){
        // 2、中间操作

        // 所有中间操作不会做任何处理
        Stream<_01_Employee> stream1 = employees.stream() // 返回一个顺序流
//                .filter(e -> {
//                    System.out.println("测试中间操作...");
//                    return e.getAge() <= 35;
//                });
                .filter(_01_Employee::returnTrue)
                .skip(3)
                .limit(2)   // 这个 skip 与 limit 互补 ，先skip后limit 与先limit再skip效果完全不一样
                .distinct()
                ;
        // 只有当做终止操作时，所有中间操作都会一次性执行，称为 “惰性求值”，延迟加载
        stream1.forEach(System.out::println);

    }

    //2. 中间操作
	/*
		映射
		map——接收 Lambda ， 将元素转换成其他形式或提取信息。
		接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
		flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */
    @Test
    public void test_Map(){

        List list = Arrays.asList("a","b",1,2,3,employees); // [a, b, 1, 2, 3, [_01_Employee [id=11, name=张三, age=10, salary=11.11], _01_Employee [id=15, name=张四, age=30, salary=21.11], _01_Employee [id=14, name=张五, age=50, salary=11.11], _01_Employee [id=12, name=张六, age=20, salary=41.11], _01_Employee [id=18, name=张七, age=40, salary=21.11], _01_Employee [id=17, name=张八, age=20, salary=51.11], _01_Employee [id=16, name=张九, age=10, salary=61.11]]]
        System.out.println(list);
        System.out.println("--------------------------------------");


        Stream<String> empNameStream = employees.stream().map(e -> e.getName());  // 流里面就这些东西：["张三","张四","张五","张六","张七","张八","张九"]
        empNameStream.forEach(System.out::println);
        System.out.println("--------------------------------------");


        List<String> strList = Arrays.asList("aa","bb","cc");
        Stream<String> stream = strList.stream().map(String::toUpperCase); // 流里面就这些东西 ： ["AAA","BBB","CCC"]
        stream.forEach(System.out::println);
        System.out.println("--------------------------------------");

        Stream<Stream<Character>> stream_map = strList.stream()
                .map(_05_TestStreamAPI_CenterOp::filterCharacter);                   // map 流里面就这些东西 ： [['a','a','a'],['b','b','b'],['c','c','c']] 类似于List.add()
        stream_map.forEach((sm) -> sm.forEach(System.out::println));
        System.out.println("--------------------------------------");

        Stream<Character> stream_flatMap = strList.stream().flatMap(_05_TestStreamAPI_CenterOp::filterCharacter);
        stream_flatMap.forEach(System.out::println);      // flatMap 流里面就这些东西 ： ['a','a','a','b','b','b','c','c','c'] 类似于List.addAll()

    }

    public static Stream<Character> filterCharacter(String str){
        List<Character> list = new ArrayList<>();
        for (Character ch :
                str.toCharArray()) {
//            System.out.println(ch);
            list.add(ch);
        }
        return list.stream();
    }
}
