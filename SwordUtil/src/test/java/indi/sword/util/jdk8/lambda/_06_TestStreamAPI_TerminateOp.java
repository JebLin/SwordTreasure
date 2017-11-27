package indi.sword.util.jdk8.lambda;

import indi.sword.util.jdk8.lambda._01_Employee.Status;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description  search_sort
 *
 * 3. 终止操作 !!!!!!   （ 一个终止操作，执行中间操作练，并产生结果 ）
 * allMatch——检查是否匹配所有元素
 * anyMatch——检查是否至少匹配一个元素
 * noneMatch——检查是否没有匹配的元素
 * findFirst——返回第一个元素
 * findAny——返回当前流中的任意元素
 * count——返回流中元素的总个数
 * max——返回流中最大值
 * min——返回流中最小值
 *
 * reduce—— 归纳
 * collect—— 收集
 *
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
                .sorted((e1, e2) -> Double.compare(e1.getSalary(),e2.getSalary())).findFirst();

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
    public void test_terminate(){
        Stream<_01_Employee> stream = employees.stream()
                .filter((e) -> e.getStatus().equals(Status.FREE));
        long count = stream.count();

        System.out.println(count);
        stream.map(_01_Employee::getSalary)
                .max(Double::compare); // 报错
    }

    //  归约reduce      可以将流中元素反复结合起来，得到一个值。
    @Test
    public void test_reduce(){
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);

        /*
         * x = 0 ,y = 1;
         * x = x + y ;y = 2;
         * x = x + y ;y = 3;
         * x = x + y ;y = 4;
         * x = x + y
         * return x;
         */
        Integer sum = list.stream().reduce(0,(x,y) -> x + y); //T reduce(T identity, BinaryOperator<T> accumulator);

        System.out.println(sum);

        System.out.println("---------------------------------------------");

        Optional<Integer> sum2 = list.stream().reduce(Integer::sum); // 由于没有上面的 identity初始值，那么结果有可能有null，那么就需要用Optional接收

        System.out.println(sum2.get());

        System.out.println("----------------------------------------------");

        Optional<Double> sum3 = employees.stream().map(_01_Employee::getSalary).reduce(Double::sum);

        System.out.println(sum3.get());
        System.out.println("----------------------------------------------");

        Optional<Integer> sum4 = employees.stream()
                .map(_01_Employee::getName)
                .flatMap(_05_TestStreamAPI_CenterOp::filterCharacter)
                .map((ch) -> {   // 这个map返回的结果是流 [0,1,0,1,0] 等
                    if(ch.equals('六'))   // 记住，这里是 单引号'' 不是双引号
                        return 1;
                    else
                        return 0;
                }).reduce(Integer::sum);

        System.out.println(sum4.get());
    }

    //collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
    @Test
    public void test_collect_To_Collection(){
        List<String> list = employees.stream()
                                    .map(_01_Employee::getName)
                                    .collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("-----------------------------------------");
        Set<String> set = employees.stream()
                                    .map(_01_Employee::getName)
                                    .collect(Collectors.toSet());
        set.forEach(System.out::println);
        System.out.println("-----------------------------------------");

        // 转换成特定类型的集合Collection
        HashSet<String> hashSet = employees.stream()
                                            .map(_01_Employee::getName)
                                            .collect(Collectors.toCollection(HashSet::new));
        hashSet.forEach(System.out::println);
    }

    @Test
    public void test_collect_collect_operate(){

        Optional<Double> max = employees.stream().map(_01_Employee::getSalary)
                .collect(Collectors.maxBy(Double::compare));

        System.out.println(max.get());

        Optional<_01_Employee> op = employees.stream()
                .collect(Collectors.minBy((e1,e2) -> Double.compare(e1.getSalary(),e2.getSalary())));
        System.out.println(op.get());

        Double sum = employees.stream().collect(Collectors.summingDouble(_01_Employee::getSalary));
        System.out.println(sum);

        Double average = employees.stream().collect(Collectors.averagingDouble(_01_Employee::getSalary));
        System.out.println(average);

        Long count = employees.stream().collect(Collectors.counting());
        System.out.println(count);

        System.out.println("--------------------------------------------");
        DoubleSummaryStatistics dss = employees.stream().collect(Collectors.summarizingDouble(_01_Employee::getSalary));

        System.out.println(dss.getMax());
    }

    // 测试分组
    @Test
    public void test_group(){
        Map<Status,List<_01_Employee>> map = employees.stream()
                .collect(Collectors.groupingBy(_01_Employee::getStatus));

        System.out.println(map);

        Map<Status, Map<String, List<_01_Employee>>> map_group =
                employees.stream().collect(Collectors.groupingBy(_01_Employee::getStatus,Collectors.groupingBy(e -> {
                    if(e.getAge() >= 60){
                        return "old";
                    }else if(e.getAge() >= 35){
                        return "mid";
                    }else{
                        return "adult";
                    }
        })));
        System.out.println(map_group);
    }

    // 测试分区
    @Test
    public void test_partition(){
        Map<Boolean, List<_01_Employee>> map =
        employees.stream().collect(Collectors.partitioningBy(e -> e.getSalary() >= 5000));
        System.out.println(map);
    }

    // 测试 拼接
    @Test
    public void test_Join(){
        String str =
        employees.stream().map(_01_Employee::getName)
                .collect(Collectors.joining(",","----","----"));
        System.out.println(str);

        String str1 = employees.stream().map(e -> {
            return String.valueOf(e.getAge());
        }).collect(Collectors.joining(","));

        System.out.println(str1);
    }



}
