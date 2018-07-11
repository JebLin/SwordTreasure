package indi.sword.util.guava.collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 10:24 AM 11/07/2018
 * @MODIFIED BY
 */
/*
    Collection
    不可变集合
    不可变对象有很多优点，包括：

    当对象被不可信的库调用时，不可变形式是安全的；
    不可变对象被多个线程调用时，不存在竞态条件问题
    不可变集合不需要考虑变化，因此可以节省时间和空间。所有不可变的集合都比它们的可变形式有更好的内存利用率（分析和测试细节）；

    不可变对象因为有固定不变，可以作为常量来安全使用。

    JDK也提供了Collections.unmodifiableXXX方法把集合包装为不可变形式，但：

    笨重而且累赘：不能舒适地用在所有想做防御性拷贝的场景；

    不安全：要保证没人通过原集合的引用进行修改，返回的集合才是事实上不可变的；

    低效：包装过的集合仍然保有可变集合的开销，比如并发修改的检查、散列表的额外空间，等等。

    创建不可变集合方法：

    1、copyOf方法，如ImmutableSet.copyOf(set);
    2、of方法，如ImmutableSet.of(“a”, “b”, “c”)或 ImmutableMap.of(“a”, 1, “b”, 2);
    3、Builder工具
 */
public class ImmutableDemo {
    public static void main(String[] args) {
        ImmutableSet<String> set1 = ImmutableSet.of("aa","bb","cc","dd");
        ImmutableSet<String> set2 = ImmutableSet.copyOf(set1);
        ImmutableSet<String> set3 = ImmutableSet.<String>builder().addAll(set1).add("ee").build();
        System.out.println(set1);
        System.out.println(set2);
        System.out.println(set3);
        ImmutableList<String> list1 = set1.asList();
        System.out.println(list1);

    }

}
