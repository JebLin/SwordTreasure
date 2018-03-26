package indi.sword.util.basic.algorithm;

/**
 * @Description
 * 单项函数：如果某个函数在给定输入的时候，很容易计算出其结果来；而当给定结果的时候，很难计算出输入来，这就是单项函数。
 * Hash函数（或者成为散列函数）也可以看成是单向函数的一个逼近。即它接近于满足单向函数的定义。
 * @Author jeb_lin
 * @Date Created in 6:11 PM 26/03/2018
 * @MODIFIED BY
 */
/*
    一般的说，Hash函数可以简单的划分为如下几类：
        1. 加法Hash；
        2. 位运算Hash；
        3. 乘法Hash；
        4. 除法Hash；
        5. 查表Hash；
        6. 混合Hash；
 */
public class TestHashAlgorithm {
    public static void main(String[] args) {
        System.out.println(rotatingHash("bddfsdfdsbb",15));
    }

    /**
     * @Description 所谓的加法Hash就是把输入元素一个一个的加起来构成最后的结果。
     * @Author jeb_lin
     * @Date 6:21 PM 26/03/2018
     * @MODIFIED BY
     */
    public static int additiveHash(String key,int prime){
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash += key.charAt(i);
        }
        return (hash % prime);
    }

    /**
     * @Description hash函数通过利用各种位运算（常见的是移位和异或）来充分的混合输入元素。
     * 抄hashmap内部hash算法
     * @Author jeb_lin
     * @Date 6:29 PM 26/03/2018
     * @MODIFIED BY
     */
    public static int rotatingHash(String key,int prime){
        int hash = key.hashCode();
        hash = (hash >>> 20) ^ (hash >>> 12) ^ (hash >>> 7) ^ (hash >>> 4);
        return (hash % prime);
    }

    //TODO
}
