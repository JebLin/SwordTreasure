package indi.sword.util.jdk8.lambda;

import indi.sword.util.jdk8.Employee;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import indi.sword.util.jdk8.lambda._01_Employee.Status;

/**
 * @Description  search_sort
 *
 * 3. 终止操作 !!!!!!
 * allMatch——检查是否匹配所有元素
 * anyMatch——检查是否至少匹配一个元素
 * noneMatch——检查是否没有匹配的元素
 * findFirst——返回第一个元素
 * findAny——返回当前流中的任意元素
 * count——返回流中元素的总个数
 * max——返回流中最大值
 * min——返回流中最小值
 *
 * sorted()——自然排序
 * sorted(Comparator com)——定制排序
 *
 *
 * warning!warning!warning!
 * 一、 Stream 的操作步骤
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *
 * 3. 终止操作
 *
 * @Author:rd_jianbin_lin
 * @Date: 9:41 2017/9/17
 */
public class _06_TestStreamAPI_TerminateOp {
    List<_01_Employee> employees = Arrays.asList(
            new _01_Employee(102, "李四", 59, 6666.66, Status.BUSY),
            new _01_Employee(101, "张三", 18, 9999.99, Status.FREE),
            new _01_Employee(103, "王五", 28, 3333.33, Status.VOCATION),
            new _01_Employee(104, "赵六", 8, 7777.77, Status.BUSY),
            new _01_Employee(104, "赵六", 8, 7777.77, Status.FREE),
            new _01_Employee(104, "赵六", 8, 7777.77, Status.FREE),
            new _01_Employee(105, "田七", 38, 5555.55, Status.BUSY)
    );

    /// 测试 Sort
    /*
		sorted()——自然排序
		sorted(Comparator com)——定制排序
	 */
    @Test
    public void test_sort(){
        employees.stream()
                .map(_01_Employee::getAge)
                .sorted()
                .forEach(System.out::println);

        System.out.println("-------------------------------");

        employees.stream()
                .sorted((x,y) -> {
                    if(x.getAge() == y.getAge()){
                        return Double.compare( x.getSalary(), y.getSalary());
                    }else{
                        return Integer.compare(x.getAge(),y.getAge());
                    }

                })
                .forEach(System.out::println);
    }


    /*
     * allMatch——检查是否匹配所有元素
     * anyMatch——检查是否至少匹配一个元素
     * noneMatch——检查是否没有匹配的元素
     */
    @Test
    public void test_Match(){
        boolean boolean_allMatch= employees.stream()
                .allMatch(e -> e.getStatus().equals(Status.BUSY));
        System.out.println(boolean_allMatch);

        boolean boolean_anyMatch = employees.stream()
                .anyMatch(e -> e.getStatus().equals(Status.BUSY));

        System.out.println(boolean_anyMatch);

        boolean boolean_noneMatch = employees.stream()
                .noneMatch(e -> e.getStatus().equals(Status.BUSY));

        System.out.println(boolean_noneMatch);
    }

    /*
     * findFirst——返回第一个元素
     * findAny——返回当前流中的任意元素
     */
    @Test
    public void test_Find(){
        Optional<_01_Employee> op = employees.stream()
                .sorted((e1, e2) -> -Double.compare(e1.getSalary(),e2.getSalary())).findFirst();

        System.out.println(op.get());

        System.out.println("-------------------------------");

        Optional<_01_Employee> op2 = employees.parallelStream().filter(e -> e.getStatus().equals(Status.BUSY)).findAny();
        System.out.println(op.get());
    }

    /*
     *count——返回流中元素的总个数
     * max——返回流中最大值
     * min——返回流中最小值
     */
    @Test
    public void test_Count_Max_Min(){
        long count = employees.stream()
                .filter(e -> e.getStatus().equals(Status.BUSY))
                .count();
        System.out.println(count);

        Optional<Double> op = employees.stream()
                .map(_01_Employee::getSalary)
                .max(Double::compare);
        System.out.println(op.get());

        Optional<_01_Employee> op2 = employees.stream()
                .min((e1,e2) -> Double.compare(e1.getSalary(),e2.getSalary()));

        System.out.println(op2.get());
    }

    //注意：流进行了终止操作后，不能再次使用
    @Test
    public void test4(){
        Stream<_01_Employee> stream = employees.stream()
                .filter((e) -> e.getStatus().equals(Status.FREE));
        long count = stream.count();

        System.out.println(count);
        stream.map(_01_Employee::getSalary)
                .max(Double::compare);
    }

}
