package indi.sword.util.base.collection;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 *
    Hahtable:无序存放,旧的操作类,key不允许重复
    HashMap:无序存放,新的操作类,key不允许重复
    TreeMap:可以排序的Map集合,按集合的key排序,key不允许重复
    WeakHashMap:弱引用的Map集合,清除集合中不再使用的内容,使用gc进行回收
    IndentityHashMap:key可以重复的Map集合


 * @Author:rd_jianbin_lin
 * @Date: 20:25 2017/9/22
 */
public class Test_Map {

    @Test
    public void test_HashTable(){
        Map<Person,String> map = new Hashtable<>() ; // 声明Map对象，其中key和value的类型为String
        map.put(new Person("c",3),"C");
        map.put(new Person("c",4),"C");
        map.put(new Person("b",4),"B");
        map.put(new Person("a",3),"A");
        map.put(new Person("a",4),"A");
        map.put(new Person("a",3),"A");
        map.forEach((person, s) -> {
            System.out.println(person.toString() + "--" + s);
        });
    }

    @Test
    public void test_HashMap(){
        Map<Person,String> map = new HashMap<Person,String>() ; // 声明Map对象，其中key和value的类型为String
        map.put(new Person("c",3),"C");
        map.put(new Person("c",4),"C");
        map.put(new Person("b",4),"B");
        map.put(new Person("a",3),"A");
        map.put(new Person("a",4),"A");
        map.put(new Person("a",3),"A");
        map.forEach((person, s) -> {
            System.out.println(person.toString() + "--" + s);
        });
    }

    @Test
    public void test_TreeMap(){
        Map<Person,String> map = new TreeMap<Person,String>() ; // 声明Map对象，其中key和value的类型为String
        map.put(new Person("c",3),"C");
        map.put(new Person("c",4),"C");
        map.put(new Person("b",4),"B");
        map.put(new Person("a",3),"A");
        map.put(new Person("a",4),"A");
        map.put(new Person("a",3),"A");
        map.forEach((person, s) -> {
            System.out.println(person.toString() + "--" + s);
        });
    }

    @Test
    public void test_WeakHashMap(){
        Map<Person,String> map = new WeakHashMap<Person,String>() ; // 声明Map对象，其中key和value的类型为String
        map.put(new Person("c",3),"C");
        map.put(new Person("c",4),"C");
        map.put(new Person("b",4),"B");
        map.put(new Person("a",3),"A");
        map.put(new Person("a",4),"A");
        map.put(new Person("a",3),"A");

        System.gc();
        map.put(new Person("a",3),"A");

        map.forEach((person, s) -> {
            System.out.println(person.toString() + "--" + s);
        });
    }

    /**
     *  1.简单说IdentityHashMap与常用的HashMap的区别是：前者比较key时是“引用相等”而后者是“对象相等”，
     *  即对于k1和k2，当k1==k2时，IdentityHashMap认为两个key相等，而HashMap只有在k1.equals(k2) == true 时才会认为两个key相等。
     *  IdentityHashMap 允许使用null作为key和value. 不保证任何Key-value对的之间的顺序, 更不能保证他们的顺序随时间的推移不会发生变化.
     *
     *  2.IdentityHashMap有其特殊用途，比如序列化或者深度复制。或者记录对象代理。
     *
     *  3.举个例子，jvm中的所有对象都是独一无二的，哪怕两个对象是同一个class的对象，而且两个对象的数据完全相同，
     *  对于jvm来说，他们也是完全不同的，如果要用一个map来记录这样jvm中的对象，你就需要用IdentityHashMap，而不能使用其他Map实现。
     */
    @Test
    public void test_IdentityHashMap(){
        Map<Person,String> map = new IdentityHashMap<Person,String>() ; // 声明Map对象，其中key和value的类型为String
        map.put(new Person("cccccc",33),"C");
        map.put(new Person("c",4),"C");
        map.put(new Person("b",4),"B");
        map.put(new Person("a",3),"A");
        map.put(new Person("a",4),"A");
        map.put(new Person("a",3),"A");


        Person p = new Person("c",3);
        map.put(p,"C");
        map.put(p,"C");
        map.put(p,"C");
        map.put(p,"C");

        map.forEach((person, s) -> {
            System.out.println(person.toString() + "--" + s);
        });
    }

    @Test
    public void test_HashMap_remove(){
        Map<Long, String> mReqPacket = new HashMap<Long, String>();
        for (long i = 0; i < 15; i++) {
            mReqPacket.put(i, i + "");
        }

        for (Iterator<Map.Entry<Long, String>> iterator = mReqPacket.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry<Long, String> entry = iterator.next();
            long key = entry.getKey();
            if (key < 10) {
                iterator.remove();
            }
        }

        for (Map.Entry<Long, String> entry : mReqPacket.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    @Test
    public void test_concurrentHashMap_remove(){
        Map<Long, String> conMap = new ConcurrentHashMap<Long, String>();
        for (long i = 0; i < 15; i++) {
            conMap.put(i, i + "");
        }

        for (Map.Entry<Long, String> entry : conMap.entrySet()) {
            long key = entry.getKey();
            if (key < 10) {
                conMap.remove(key);
            }
        }

        for (Map.Entry<Long, String> entry : conMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
