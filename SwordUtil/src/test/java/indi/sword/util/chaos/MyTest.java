package indi.sword.util.chaos;

import com.google.common.collect.Lists;
import org.apache.cxf.wsdl11.SOAPBindingUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class MyTest {
    private Object obj = new Object();

    private Object anotherObj = new Object();

    @Test
    public void produce() {
        synchronized (anotherObj) {
            try {
                anotherObj.notify();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws Exception {

//        test01();
//        test02();
//        test03();
//        test04();

        String s3 = new String("11");

        s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);

    }

    private static void test01() {
//        ArrayList<String> stringList = new ArrayList<>(1); // 162ms  VS 9000ms
        ArrayList<String> stringList = new ArrayList<>(1); // 162ms  VS 9000ms

        long begin = System.currentTimeMillis();

        for (int i = 0; i < 100000000; i++) {
            stringList.add("A");
        }

        long end = System.currentTimeMillis();
        System.out.println("ArrayList -> " + (end - begin));


//		LinkedList<String> stringLinkedLis = Lists.newLinkedList();
//		long begin2 = System.currentTimeMillis();
//
//		for (int i = 0; i < 10000000; i++) {
//			stringLinkedLis.add("A");
//		}
//
//		long end2 = System.currentTimeMillis();
//		System.out.println("LinkedList -> " + (end2 - begin2));
    }

    private static void test02() {
        List<A> list = Lists.newArrayList();
        list.add(new A());

        List<B> list2 = Lists.newArrayList();
        list2.add(new B());


        System.out.println(list.removeAll(list2));
        System.out.println(list.contains(new B()));
    }



    private static class A{}
    private static class B{}

    private static void test03(){
        Object[] a = new Object[10];

        a[0] = "A";
        a[1] = "B";

        Object[] b = new Object[10];

        System.arraycopy(a, 0,
                b, 0,
                2);

        b[1] = "X";
        System.out.println(a[1]);
        System.out.println(b[1]);

        Object[] c = a.clone();
        c[1] = "c1";
        System.out.println(a[1]);
    }


    private static void test04() throws Exception{
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue(2);

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println(Thread.currentThread().getName() + "--- put A");
                arrayBlockingQueue.put("A");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        System.out.println("first take ...");
        System.out.println(arrayBlockingQueue.take());
        System.out.println("end take ...");
    }
}
