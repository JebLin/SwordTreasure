package indi.sword.util.base.collection;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

/**
 * @Description 测试Set 子类
 *
 * 1.Set的子类
    (1).HashSet
        底层数据结构是哈希表，线程是不同步的，无序，高效；
        HashSet集合保证元素唯一性：
        1).当元素的hashCode值不同
        那么不判断equals，元素添加会成功。
        2).当元素的hashCode值相同
        继续判断元素的equals是否为true。如果为true，则视为同一元素，添加不能成功，否则添加成功。
        关于HashCode的详细介绍>>
    (2).TreeSet
        TreeSet底层的数据结构就是二叉树，对Set集合中的元素的进行指定顺序的排序，不同步。

   2.HashSet与TreeSet的比较
    (1).HashSet
        根据哈希值进行元素的重复检查，先hashCode()(可能的equals()),元素的顺序没有改变
    (2).TreeSet
        TreeSet中添加的元素自身具有比较性或者集合具有比较性，比较性一定要有，由于String等实现了Comparable接口，这使得元素本身具有比较性！比较性的实现查看下面的说明。但元素和集合都具有比较性时，以集合比较器为主，没有则以元素比较器进行比较，会根据元素的比较重新排序。

   3.比较器
    1).让元素自身具备比较性
        A).元素类实现Comparable接口,class Person implements Comparable
        B).重写compareTo方法,public int compareTo(Object obj
        记忆:元素具有比较性故用形容词Comparable修饰,元素之间去比较compareTo()
    2).让容器自身具备比较性
        A).新建一类MyComparator，使其实现接口Comparator，class MyComparator implements Comparator
        B).重写compare方法，public int compare(Object obj1, Object obj2)
        记忆:新建比较器为名词Comparator,Comparator主动去比较，用动词compare();
    当两者比较器都存在时，以容器自身比较为主！
 *
 *
 * @Author:rd_jianbin_lin
 * @Date: 19:08 2017/9/22
 */
public class Test_HashSet_LinkedHashSet_TreeSet {

    // 测试输出顺序
    @Test
    public void test_printOrder(){
        // 无序输出
        Set<Person> hashSet = new HashSet<>();
        hashSet.add(new Person("a",3));
        hashSet.add(new Person("d",5));
        hashSet.add(new Person("b",2));
        hashSet.add(new Person("c",4));
        hashSet.add(new Person("a",2));
        System.out.println(hashSet.size());
        hashSet.stream().forEach(System.out::println);
        System.out.println("-----------------------");

        // 按照compareTo的规则输出
        Set<Person> treeSet = new TreeSet<>();
        treeSet.add(new Person("a",3));
        treeSet.add(new Person("d",5));
        treeSet.add(new Person("b",2));
        treeSet.add(new Person("c",4));
        treeSet.add(new Person("a",2));
        System.out.println(treeSet.size());
        treeSet.stream().forEach(System.out::println);
        System.out.println("-----------------------");


        // 先进先出 双链表来记录插入 （输出的顺序时确定的，就是插入的顺序。）
        Set<Person> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add(new Person("a",3));
        linkedHashSet.add(new Person("d",5));
        linkedHashSet.add(new Person("b",2));
        linkedHashSet.add(new Person("c",4));
        linkedHashSet.add(new Person("a",2));
        linkedHashSet.stream().forEach(System.out::println);
    }


    /**
     *
     * HashSet是基于散列表实现的，元素没有顺序；add、remove、contains方法的时间复杂度为O(1)。
       TreeSet是基于树实现的（红黑树），元素是有序的；add、remove、contains方法的时间复杂度为O(log (n))。因为元素是有序的，它提供了若干个相关方法如first(), last(), headSet(), tailSet()等；
       LinkedHashSet介于HashSet和TreeSet之间，是基于哈希表和链表实现的，支持元素的插入顺序；基本方法的时间复杂度为O(1)；
     * @throws Exception
     */

    @Test
    public void test_insert() throws Exception{
        Random random = new Random();
        final int N = 5000000;

        HashSet<Person> hashSet = new HashSet<>();
        TreeSet<Person> treeSet = new TreeSet<>();
        LinkedHashSet<Person> linkedHashSet = new LinkedHashSet<>();

        Instant t1_start = Instant.now();
        for(int i = 0;i< N;i++){
            hashSet.add(new Person("hashSet_Person-"+ i,random.nextInt(100)));
        }
        Instant t1_end = Instant.now();
        System.out.println(Duration.between(t1_start,t1_end).toMillis());

        Instant t2_start = Instant.now();
        for(int i = 0;i< N;i++){
            treeSet.add(new Person("treeSet_Person-"+ i,random.nextInt(100)));
        }
        Instant t2_end = Instant.now();
        System.out.println(Duration.between(t2_start,t2_end).toMillis());

        Instant t3_start = Instant.now();
        for(int i = 0;i< N;i++){
            linkedHashSet.add(new Person("linkedHashSet_Person-"+ i,random.nextInt(100)));
        }
        Instant t3_end = Instant.now();
        System.out.println(Duration.between(t3_start,t3_end).toMillis());
    }

}




// 实现 Comparable接口是为了TreeSet与SortedSet可以进行排序用
class Person implements Comparable //实现接口Comparable
{
    private String name;
    private int age;
    Person(String name, int age)
    {
        this.name = name;
        this.age = age;
    }
    public int getAge()
    {
        return this.age;
    }
    public String getName()
    {
        return this.name;
    }
    public int compareTo(Object obj)    //自动调用
    {
        if(!(obj instanceof Person))
            throw new RuntimeException("对象出错!");
        Person p = (Person)obj;
        if(this.getName().compareTo(p.getName()) < 0){
            return -1;
        }
        if(this.getName().compareTo(p.getName()) == 0)
        {
            if(this.age < p.age)
                return -1;
            else if(this.age == p.age)
                return 0;
            else
                return 1;
        }
        return 1;
    }

    /*
     * Object类里面的默认equals方法是比较内存地址是否相等，默认的hashCode方法则是根据内存地址产生一个整数，
     * 基于这种情况，
     * 假如只覆盖了equals的话，那么set里面可能存入两个相同内容的对象。
     * 假如只覆盖了hashCode的话，那么也有可能误判为两个不同内容的对象为同一个，导致错误覆盖。例子细想一个，
     * hashMap里面存key-value的时候，一个Array里面存了一个List，那么假如碰撞发生的时候，内容不同会往链表List添加元素。
     *
     * 所以，比较两个元素是否相等，一定要覆盖 equals与hashCode方法
     *
     */
    @Override
    public boolean equals(Object o){
        if(null == o){
            return false;
        }
        if(!(o instanceof Person)){
            return false;
        }
        if(o == this){
            return true;
        }
        else
        {
            return this.name.equals(((Person)o).getName()) && ((Person)o).getAge() == this.age;
        }
    }


    @Override
    public int hashCode(){
        return this.name.hashCode() * this.age;
    }

    @Override
    public String toString(){
        return "Person[name = " + name + ",age = " + age + "]";
    }

}