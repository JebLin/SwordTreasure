package indi.sword.util.jdk8.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description StreamApi的测试题
 * @Author:rd_jianbin_lin
 * @Date: 19:15 2017/9/18
 */
public class _07_TestStreamAPI_Quiz {

    List<_01_Employee> employees = Arrays.asList(
            new _01_Employee(102, "李四", 59, 6666.66, _01_Employee.Status.BUSY),
            new _01_Employee(101, "张三", 18, 9999.99, _01_Employee.Status.FREE),
            new _01_Employee(103, "王五", 28, 3333.33, _01_Employee.Status.VOCATION),
            new _01_Employee(104, "赵六", 8, 7777.77, _01_Employee.Status.BUSY),
            new _01_Employee(104, "赵六", 8, 7777.77, _01_Employee.Status.FREE),
            new _01_Employee(104, "赵六", 8, 7777.77, _01_Employee.Status.FREE),
            new _01_Employee(105, "田七", 38, 5555.55, _01_Employee.Status.BUSY)
    );

    /*
	  	1.	给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？
		，给定【1，2，3，4，5】， 应该返回【1，4，9，16，25】。
	 */
    @Test
    public void test01(){
        Integer[] integers = new Integer[]{1,2,3,4,5};
        Arrays.stream(integers).map(e -> e * e).forEach(System.out::println);

        System.out.println("------------------");
        List<Integer> list = Arrays.stream(integers).map(e -> e * e).collect(Collectors.toList());
        list.forEach(System.out::println);

        String str = Arrays.stream(integers).map(e -> String.valueOf(e * e)).collect(Collectors.joining(","));
        System.out.println(str);

    }

    /*
       2.怎样用 map 和 reduce 方法数一数流中有多少个Employee呢？
     */
    @Test
    public void test02(){
        long count = employees.stream().count();
        System.out.println(count);

        Optional<Integer> count2 = employees.stream().map(e -> 1).reduce(Integer::sum); // [1,1,1,1,1,1,1]
        System.out.println(count2.get());
    }

}
