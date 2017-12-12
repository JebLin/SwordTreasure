package indi.sword.util.basic.collection;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

/**
 * @Description ArrayList 、LinkedList、Vector区别
 *
 * List：有序，可重复，有索引。
    |--ArrayList：底层是数组数据结构。是线程不同步的。查询元素的速度很快。但是增删元素的效率稍低。
    |--LinkedList：底层是链表数据结构，是线程不同步的。查询元素的速度稍慢，但是增删速度很快。
    |--Vector：底层也是数组数据结构。是线程同步的。被ArrayList替代了。查询速度，和增删的速度非常慢。效率低。
 * 其常用子类有：
 * Vector：  同步的
 *       Vector 类可以实现可增长的对象数组。与数组一样，
 *       它包含可以使用整数索引进行访问的组件。但是，Vector 的大小可以根据需要增大或缩小，
 *       以适应创建 Vector 后进行添加或移除项的操作。
 *       ------------------------------------------------------
 * LinkedList：可用于实现堆栈 队列
 *            堆栈：先进后出。First In Last Out
 *            队列：先进先出。First In First In
 *            围绕头和尾展开定义：
 *
 *            addFirst（）
 *            addLast（）
 *
 *            getFirst（）
 *            getLast（）
 *
 *            removeFirst（）获取头部元素并且删除
 *            removeLast（）
 *
 * -----------------------------------------------------
 * ArrayList：数组结构，长度可变（原理：创建新数组+复制数组）
 *            增删较慢，查询很快。----》》》顺序表--牵一发而动全身
 *            List 接口的大小可变数组的实现。实现了所有可选列表操作，
 *            并允许包括 null 在内的所有元素。除了实现 List 接口外，
 *            此类还提供一些方法来操作内部用来存储列表的数组的大小。
 *            （此类大致上等同于 Vector 类，除了此类是不同步的。）
 *
 * <p>
 * 1.ArrayList 实现了基于动态数据的数据结构，LinedList 是基于链表的数据结构
 * 2.对于随机访问get和set，ArrayList优于LinkedList，因为ArrayList可以随机定位，而LinkedList要移动指针一步一步的移动到节点处。
 * 3.对于新增和删除操作add和remove，LinedList比较占优势，只需要对指针进行修改即可，而ArrayList要移动数据来填补被删除的对象的空间。
 * @Author:rd_jianbin_lin
 * @Date: 14:34 2017/9/22
 */
public class Test_ArrayList_LinkedList_Vector {

    public static final int N = 50000;
    public static List values;

    @Before
    public void before() {

        System.out.println("before ...");
        Integer vals[] = new Integer[N];
        Random r = new Random();
        for (int i = 0, currval = 0; i < N; i++) {
            vals[i] = new Integer(currval);
            currval += r.nextInt(100) + 1;
        }
        values = Arrays.asList(vals);

    }

    /**
     * @Description 测试搜索
     * @Author:rd_jianbin_lin
     * @Date: 14:45 2017/9/22
     */
    public static long timeList_Search(List lst) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            int index = Collections.binarySearch(lst, values.get(i)); // 二分查找法
            if (index != i)
                System.out.println("***错误***");
        }
        return System.currentTimeMillis() - start;
    }

    /**
     * @Description 测试插入
     * @Author:rd_jianbin_lin
     * @Date: 14:48 2017/9/22
     */
    public static long timeList_Insert(List list) {
        long start = System.currentTimeMillis();
        Object o = new Object();
        for (int i = 0; i < N; i++)
            list.add(0, o); // 插入
        return System.currentTimeMillis() - start;
    }

    @Test
    public void test_Search() throws Exception {
        System.out.println("ArrayList消耗时间：" + timeList_Search(new ArrayList(values))); // 9
        System.out.println("Vector消耗时间：" + timeList_Search(new Vector(values))); // 22
        System.out.println("LinkedList消耗时间：" + timeList_Search(new LinkedList(values))); // 3228
    }

    /**
     * @Description
     * 当一个元素被加到ArrayList的最开端时，所有已经存在的元素都会后移，这就意味着数据移动和复制上的开销。
     * 相反的，将一个元素加到LinkedList的最开端只是简单的为这个元素分配一个记录，然后调整两个连接。
     * 在LinkedList的开端增加一个元素的开销是固定的，而在ArrayList的开端增加一个元素的开销是与ArrayList的大小成比例的。
     *
     * ArrayList:  添加了 0.5 倍
     * int newCapacity = oldCapacity + (oldCapacity >> 1);
     * Vector :  添加了 1 倍
     * int newCapacity = oldCapacity + ((capacityIncrement > 0) ? capacityIncrement : oldCapacity);
     * 所以ArrayList比Vector效率高。
     * @Author:rd_jianbin_lin
     * @Date: 14:50 2017/9/22
     */
    @Test
    public void test_Insert() throws Exception{
        System.out.println("ArrayList消耗时间：" + timeList_Insert(new ArrayList())); // 245
        System.out.println("Vector消耗时间：" + timeList_Insert(new Vector())); // 269
        System.out.println("LinkedList消耗时间：" + timeList_Insert(new LinkedList())); // 3
    }


}
