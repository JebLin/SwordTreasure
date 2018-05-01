package indi.sword.util.jvm.heapAndStack;


/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 11:55 PM 01/05/2018
 * @MODIFIED BY
 */
/*
    在jdk1.6 中，会得到两个false，intern方法会把首次遇到的字符串复制到永久代，返回永久代中的这个字符串的引用，
    而StringBuilder创建的字符串是放在堆中的，返回的就是堆中对象的引用，那肯定就是false。

    在jdk1.7中，常量池移动到了堆中去了，intern方法不会再复制实例了，只是在常量池中记录首次出现的位于堆中的实例引用，
    因此intern返回引用和StringBuilder返回的引用是同一个。

    -- 对于 "java" 字符串，它属于java中的关键字，在你new StringBuilder之前就已经出现过了，所以你这个时候再new一个，肯定就不是同一个对象了
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {

        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);

        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

        // false false
        // true

    }
}
