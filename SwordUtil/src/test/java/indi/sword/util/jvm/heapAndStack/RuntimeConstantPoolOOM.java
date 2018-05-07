package indi.sword.util.jvm.heapAndStack;


import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 *
 *      -XX:PermSize=10M -XX:MaxPermSize=10M -XX:+PrintGCDetails
 *      jdk1.6以及之前由于常量池分配在永久代内，
 *      我们可以通过 -XX:PermSize 和 -XX:MaxPermSize 限制方法区大小，从而间接限制其中常量池的容量
 * @Author jeb_lin
 * @Date Created in 11:55 PM 01/05/2018
 * @MODIFIED BY
 */


public class RuntimeConstantPoolOOM {

    public static void main(String[] args) {

        // 使用List保持着常量池引用，避免Full GC回收常量池的行为
        List<String> list = new ArrayList<>();

        // 10MB的PermSize在Integer范围内足够产生OOM了。
        int i = 0;
        while (true){
            list.add(String.valueOf(i++).intern());
        }


        // 测试一下Intern关键字。
//        testIntern();


    }

    /*
        在jdk1.6 中，会得到两个false，intern方法会把首次遇到的字符串复制到永久代，返回永久代中的这个字符串的引用，
        而StringBuilder创建的字符串是放在堆中的，返回的就是堆中对象的引用，那肯定就是false。

        在jdk1.7中，常量池移动到了堆中去了，intern方法不会再复制实例了，只是在常量池中记录首次出现的位于堆中的实例引用，
        因此intern返回引用和StringBuilder返回的引用是同一个。

        -- 对于 "java" 字符串，它属于java中的关键字，在你new StringBuilder之前就已经出现过了，
        所以你这个时候再new一个，肯定就不是同一个对象了.
     */
    public static void testIntern(){

        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString(); // java 是关键字，你再new就是另一个对象了

        System.out.println(str2.intern() == str2);


        // false false
        // true false

        String str3 = new StringBuilder("56").append("78").toString();
        System.out.println(str3.intern() == str3);

        System.out.println("------");

        /*
            jdk 1.7测试结果下面俩个：false，false。推断1.6中也都是false。
            由于new String("890")，new StringBuilder("1234")都是两步骤过程：1、把 "890" 写入堆中常量池（1.7 之后常量池从永久代移动到堆）。
            2、在堆中创建对象。
            所以str4.intern()返回的是字符串在常量池中的地址，不是字符串在堆中的地址，所以都是false。
         */
        String str4 = new StringBuilder("1234").toString();
        System.out.println(str4.intern() == str4);

        String str5 = new String("890");
        System.out.println(str5.intern() == str5);
        System.out.println("------");

    }
}
