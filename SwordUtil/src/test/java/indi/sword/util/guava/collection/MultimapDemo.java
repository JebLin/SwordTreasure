package indi.sword.util.guava.collection;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Map;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 10:44 AM 11/07/2018
 * @MODIFIED BY
 */
public class MultimapDemo {
    public static void main(String[] args) {
        Multimap<String, Integer> multimap = HashMultimap.create(); //Multimap是把键映射到任意多个值的一般方式
        multimap.put("a", 1);  //key相同时不会覆盖原value
        multimap.put("a", 2);  //value 相同会覆盖
        multimap.put("a", 2);
        multimap.put("a", 3);
        System.out.println(multimap);
        System.out.println(multimap.get("a")); //返回的是集合
        System.out.println(multimap.size()); //返回所有”键-单个值映射”的个数,而非不同键的个数
        System.out.println(multimap.keySet().size()); //返回不同key的个数

        Map<String, Collection<Integer>> mapview = multimap.asMap();
        System.out.println(mapview.size());

        System.out.println("-----------");
        Multimap<String, Integer> multimap2 = LinkedListMultimap.create();
        multimap2.put("a", 1);  //key相同时不会覆盖原value
        multimap2.put("a", 2);  //value 相同会覆盖
        multimap2.put("a", 2);
        multimap2.put("a", 3);
        System.out.println(multimap2);
        System.out.println(multimap2.get("a")); //返回的是集合
        System.out.println(multimap2.size()); //返回所有”键-单个值映射”的个数,而非不同键的个数
        System.out.println(multimap2.keySet().size()); //返回不同key的个数

    }
}
