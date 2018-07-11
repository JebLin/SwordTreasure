package indi.sword.util.guava.collection;

import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;

import java.util.Set;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 10:32 AM 11/07/2018
 * @MODIFIED BY
 */
public class MultiSetDemo {
    public static void main(String[] args) {
        Multiset<String> multiset = LinkedHashMultiset.create();
        multiset.add("aa");
        multiset.add("aa");
        multiset.add("aa");
        multiset.add("aa");
        multiset.add("bb");

        multiset.setCount("cc",5);//添加或删除指定元素使其在集合中的数量是count

        System.out.println(multiset);
        System.out.println(multiset.count("aa")); //给定元素在Multiset中的计数
        System.out.println(multiset.size()); //所有元素计数的总和,包括重复元素
        Set<String> set1 = multiset.elementSet();
        System.out.println(set1.size());  //所有元素计数的总和,不包括重复元素


        set1.clear(); //清空集合
        System.out.println(multiset);
        System.out.println(set1);

    }

}
