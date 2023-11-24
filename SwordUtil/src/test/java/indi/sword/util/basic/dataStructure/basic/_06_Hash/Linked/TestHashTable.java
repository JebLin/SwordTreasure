package indi.sword.util.basic.dataStructure.basic._06_Hash.Linked;

import indi.sword.util.basic.dataStructure.basic._06_Hash.Info;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 16:28 2018/2/7
 * @Modified By
 */
public class TestHashTable {
    public static void main(String[] args) {

        MyHashTable_linked ht = new MyHashTable_linked();
        ht.insert(new Info("aa","张三")); // 28
        ht.insert(new Info("dt","李四")); // 128 取模也变成 28 于是覆盖掉了
        ht.insert(new Info("c","王五"));

        System.out.println(ht.find("aa").getName());
        System.out.println(ht.find("dt").getName());
        System.out.println(ht.find("c").getName());

        System.out.println("----------------");
        System.out.println(ht.delete("aa"));
        System.out.println(ht.delete("aa"));
        System.out.println(ht.delete("dt"));

    }


    public static void testNode(){
        MyLinkList list = new MyLinkList();
        list.insertFirst(new Info("a","zhangsan"));
        list.insertFirst(new Info("b","lisi"));
        list.insertFirst(new Info("c","wangwu"));
        list.insertFirst(new Info("d","zhaoliu"));
        list.print();
        System.out.println("----");
        System.out.println(list.find("a"));
        System.out.println(list.find("b"));
        System.out.println(list.find("c"));
        System.out.println(list.find("d"));
        System.out.println("----");
//        list.deleteFirst();
        System.out.println("--" + list.delete("d"));
        list.print();
    }
}
