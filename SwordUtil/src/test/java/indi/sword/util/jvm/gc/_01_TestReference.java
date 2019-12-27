package indi.sword.util.jvm.gc;

import java.lang.ref.*;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 11:51 AM 27/04/2018
 * @MODIFIED BY
 */
/*
    + 强引用（Strong Reference） “Object obj = new Object（）”    只要强引用还存在GC 永远不回回收掉这部分被引用对象。
    + 软引用（Soft Reference），描述一些有用但并非必须的对象。放过你一次，第二次回收就吃掉你
    + 弱引用（Weak Reference），第一次回收，看到你就吃掉你
    + 虚引用（Phantom Reference），幽灵引用或者幻影引用，无法通过虚引用来取得一个对象实例。为一个对象设置虚引用关联的唯一目的就是为了这个对象被GC 回收的时候能收到一个系统通知。


    1、强引用

     强引用不会被GC回收，并且在java.lang.ref里也没有实际的对应类型，平时工作接触的最多的就是强引用。
     Object obj = new Object();这里的obj引用便是一个强引用。如果一个对象具有强引用，那就类似于必不可少的生活用品，垃圾回收器绝不会回收它。
     当内存空 间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，使程序异常终止，也不会靠随意回收具有强引用的对象来解决内存不足问题。
 */
public class _01_TestReference {


    public static void main(String[] args) throws Exception {
//        soft();
//        weak();
//        phantom();

        int a = 1;
        int b = 1;
        byte[] ab = String.valueOf(a).getBytes();
        byte[] abc = String.valueOf(b).getBytes();
        System.out.println(ab.equals(abc));
        System.out.println(ab == abc);

        System.out.println(new String(ab).equals(new String(abc)));
    }



    /*
        2、软引用
            如果一个对象只具有软引用，那就类似于可有可物的生活用品。
            如果内存空间足够，垃圾回收器就不会回收它，如果内存空间不足了，就会回收这些对象的内存。
            只要垃圾回收器没有回收它，该对象就可以被程序使用。软引用可用来实现内存敏感的高速缓存。
            软引用可以和一个引用队列（ReferenceQueue）联合使用，如果软引用所引用的对象被垃圾回收，
            Java虚拟机就会把这个软引用加入到与之关联的引用队列中。
     */
    /**
     * 只有当内存不够的时候，才回收这类内存，因此在内存足够的时候，它们通常不被回收
     *
     * <pre>
     * 无论是否发送GC,执行结果都是:
     * java.lang.Object@f9f9d8
     * null
     * java.lang.Object@f9f9d8
     * null
     * </pre>
     *
     * 可以看到:只有发送了GC,将对于从内存中释放的时候,JVM才会将reference加入引用队列
     */
    public static void soft() throws Exception
    {
        Object obj = new Object();

        // 垃圾回收后的对象都丢这个里面
        ReferenceQueue<Object> refQueue = new ReferenceQueue<Object>();
        SoftReference<Object> softRef = new SoftReference<Object>(obj, refQueue);
        System.out.println(softRef.get()); // java.lang.Object@f9f9d8
        System.out.println(refQueue.poll());// null

        // 清除强引用,触发GC
        obj = null;
        System.gc();

//        只有当内存不够的时候，才回收这类内存，因此在内存足够的时候，它们通常不被回收
        System.out.println(softRef.get()); // 并非null说明没被回收
        Thread.sleep(200);
        System.out.println(refQueue.poll());
/*
    这里有几点需要说明：
        1、System.gc()告诉JVM这是一个执行GC的好时机，但具体执不执行由JVM决定（事实上这段代码一般都会执行GC)
        2、Thread.sleep(200); 这是因为从对象被回收到JVM将引用加入refQueue队列，需要一定的时间。
        而且poll并不是一个阻塞方法，如果没有数据会返回null，所以我们选择等待一段时间。
 */
    }



    /**
     * 弱引用: 当发生GC的时候,Weak引用对象总是会内回收回收。因此Weak引用对象会更容易、更快被GC回收。
     * Weak引用对象常常用于Map数据结构中，引用占用内存空间较大的对象
     *
     * <pre>
     * 如果不发生垃圾回收：
     * java.lang.Object@f9f9d8
     * null
     * java.lang.Object@f9f9d8
     * null
     *
     * 如果发生垃圾回收:
     * java.lang.Object@f9f9d8
     * null
     * null
     * java.lang.ref.WeakReference@422ede
     *
     * <pre>
     */
    public static void weak() throws Exception
    {
        Object obj = new Object();
        ReferenceQueue<Object> refQueue = new ReferenceQueue<Object>();
        WeakReference<Object> weakRef = new WeakReference<Object>(obj, refQueue);
        System.out.println(weakRef.get()); // java.lang.Object@f9f9d8
        System.out.println(refQueue.poll());// null

        // 清除强引用,触发GC
        obj = null;
        System.gc();

        System.out.println(weakRef.get());

        // 这里特别注意:poll是非阻塞的,remove是阻塞的.
        // JVM将弱引用放入引用队列需要一定的时间,所以这里先睡眠一会儿
        // System.out.println(refQueue.poll());// 这里有可能是null

        Thread.sleep(200);
        System.out.println(refQueue.poll());
        // System.out.println(refQueue.poll());//这里一定是null,因为已经从队列中移除

        // System.out.println(refQueue.remove());
        /*
            1、remove这是一个阻塞方法，类似于J.U.C并发包下的阻塞队列，如果没有队列没有数据，那么当前线程一直等待。
            2、如果队列有数据，那么remove和pool都会将第一个元素出队。
         */
    }



/*
    4、幽灵引用(虚引用)
        虚引用主要用来跟踪对象被垃圾回收器回收的活动。虚引用与软引用和弱引用的一个区别在于：虚引用必须和引用队列 （ReferenceQueue）联合使用。
        当垃圾回收器回收一个对象时，如果发现它还有虚引用，就会把这个虚引用加入到与之关联的引用队列中。
        程序可以通过判断引用队列中是否已经加入了虚引用，来了解被引用的对象是否将要被垃圾回收。
        如果程序发现某个虚引用已经被加入到引用队列，那么就可以在所引用的对象的内存被回收之前采取必要的行动。
        由于Object.finalize()方法的不安全性、低效性，常常使用虚引用完成对象回收前的资源释放工作。
 */
    /**
     * 当GC一但发现了虚引用对象，将会将PhantomReference对象插入ReferenceQueue队列.
     * 而此时PhantomReference所指向的对象并没有被GC回收，而是要等到ReferenceQueue被你真正的处理后才会被回收.
     *
     * <pre>
     * 不发生GC执行结果是:
     * null
     * null
     * null
     * null
     *
     * 发生GC执行结果是:
     * null
     * null
     * null
     * java.lang.ref.PhantomReference@87816d
     * </pre>
     *
     * 虚引用在实现一个对象被回收之前必须做清理操作是很有用的,比finalize()方法更灵活
     */
    public static void phantom() throws Exception
    {
        Object obj = new Object();
        ReferenceQueue<Object> refQueue = new ReferenceQueue<Object>();
        PhantomReference<Object> phantom = new PhantomReference<Object>(obj,
                refQueue);
        System.out.println(phantom.get()); // java.lang.Object@f9f9d8
        System.out.println(refQueue.poll());// null

        obj = null;
        System.out.println(phantom.get());
        System.gc();

        // 调用phanRef.get()不管在什么情况下会一直返回null
        System.out.println(phantom.get());

        // 当GC发现了虚引用，GC会将phanRef插入进我们之前创建时传入的refQueue队列
        // 注意，此时phanRef所引用的obj对象，并没有被GC回收，在我们显式地调用refQueue.poll返回phanRef之后
        // 当GC第二次发现虚引用，而此时JVM将phanRef插入到refQueue会插入失败，此时GC才会对obj进行回收
        Thread.sleep(200);
        System.out.println(refQueue.poll());
    }


}
