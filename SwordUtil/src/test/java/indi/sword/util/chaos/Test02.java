package indi.sword.util.chaos;


import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Vector;

/**
 * @author jeb_lin
 * 11:13 AM 2019/9/20
 */
public class Test02 {
    public static void main(String[] args) {
        List<String> list = Lists.newArrayList();
        list.add("1");
        list.add("2");

        Object[] arr = list.toArray();
        System.out.println(arr[0]);
        arr[0] = "4";
        System.out.println(arr[0]);
        System.out.println(list.get(0));

        List<String> list2 = Lists.newArrayList();
        list2.add(null);
        list2.add(null);

        List<String> list3 = Lists.newLinkedList();
        list3.add(null);
        list3.add(null);

        Vector<String> vector = new Vector<>();
        vector.add(null);

        List<String> list4 = ImmutableList.of("A","A","A",null);

    }
}
