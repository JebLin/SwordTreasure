package indi.sword.util.basic.dataStructure.basic._06_Hash.openAddress;

import indi.sword.util.basic.dataStructure.basic._06_Hash.Info;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 19:43 2018/2/5
 * @Modified By
 */
/*
    开放地址法：
        当冲突发生时，通过查找数组的一个空位，并将数据填入，而不再用哈希函数得到数组的下标，这种方法叫做开放地址法。
 */
public class TestHashTable {
    public static void main(String[] args) {
        MyHashTable_openAddress ht = new MyHashTable_openAddress();
        ht.insert(new Info("aa","张三")); // 28
        ht.insert(new Info("dt","李四")); // 128 取模也变成 28 于是覆盖掉了
        ht.insert(new Info("c","王五"));

        System.out.println(ht.find("aa"));
        System.out.println(ht.find("dt"));
        System.out.println(ht.find("c"));

        ht.delete("aa");
        System.out.println(ht.find("dt"));


    }
}
