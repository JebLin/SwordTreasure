package indi.sword.util.basic.dataStructure.basic._06_Hash.common;

import indi.sword.util.basic.dataStructure.basic._06_Hash.Info;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 19:43 2018/2/5
 * @Modified By
 */
public class TestHashTable {
    public static void main(String[] args) {
        MyHashTable ht = new MyHashTable();
        ht.insert(new Info("dt","李四")); // 128
        ht.insert(new Info("aa","张三")); // 28
        ht.insert(new Info("c","王五"));

        System.out.println(ht.find("aa"));
        System.out.println(ht.find("dt"));
        System.out.println(ht.find("c"));


    }
}
