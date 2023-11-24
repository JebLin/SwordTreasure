package indi.sword.util.basic.Thread;

import sun.jvm.hotspot.jdi.ThreadGroupReferenceImpl;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author jeb_lin
 * 11:39 AM 2019/10/2
 */
public class _06_01_TestThreadGroup {
    public static void main(String[] args) throws Exception {
//        testGroupListAndSize();
//        testInterrupte();
//        testCopy();
//        test01();
        testCatchException();
    }


    public static void test01() throws Exception {
        print();
        new Thread(() -> print(),"A1").start();
        new Thread(() -> new Thread(() -> print(),"B2").start(),"B1").start();
    }

    public static void print(){
        System.out.println("currentThread -> " + Thread.currentThread() + ",group -> " + Thread.currentThread().getThreadGroup());
    }
    public static void testGroupListAndSize() throws Exception {
        ThreadGroup group1 = new ThreadGroup("Group1");

        Thread t1 = new Thread(group1, () -> {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("点击任意键唤醒线程 ...");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("t1 被打断啦 ...");
            }
        });
        Thread t2 = new Thread(group1, () -> {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("点击任意键唤醒线程 ...");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("t2 被打断啦 ...");
            }

        });
        Thread t3 = new Thread(group1, () -> {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("点击任意键唤醒线程 ...");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("t3 被打断啦 ...");
            }
        });
        /*
            1. 线程未启动，未注册到 group 里面去
         */
        group1.list();
        System.out.println("group1.size ->  " + group1.activeCount());

        Thread.sleep(1000);
        System.out.println("----");

        /*
            2. 启动线程
         */
        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(1000);
        group1.list();
        System.out.println("group1.size ->  " + group1.activeCount());
        System.out.println("----");
        System.out.println("==== 启动 thread4，验证 activeCount 含义（只返回活跃数量） ====");

        /*
            启动 thread4，验证 activeCount 含义（只返回活跃数量）
         */
        Thread t4 = new Thread(group1, () -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("t4 被打断啦 ...");
            }
        });
        t4.start();
        group1.list();
        System.out.println("group1.size ->  " + group1.activeCount());
        System.out.println("----");
        Thread.sleep(2000);
        System.out.println(" === 2s 后 ，t4 已经down ===");
        group1.list();
        System.out.println("group1.size ->  " + group1.activeCount());
        System.out.println("----");

        Thread.sleep(1000);
        System.out.println("==== 创建子group ====");
        ThreadGroup group2 = new ThreadGroup(group1, "Group2");
        Thread t21 = new Thread(group2, () -> {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println("点击任意键唤醒线程 ...");
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("t21 被打断啦 ...");
            }
        });
        t21.start();
        Thread.sleep(1000);
        System.out.println("遍历下 group1 ,观察是否加入了...");
        group1.list();
        System.out.println("遍历下 group2 ...");
        group2.list();
        System.out.println("最后遍历下最牛逼的 group-main ...");
        Thread.currentThread().getThreadGroup().list();

        Thread.sleep(1000);
        /*
            interrupt
         */
        System.out.println("=== 最后打断全部线程 ===");
//        group1.destroy();
        group1.interrupt();
        /*
            sc.nextLine(); 太牛逼了，里面有死循环，听不到外面的打断
         */
        System.out.println("main thread end ...");
    }


    public static void testInterrupte() throws Exception {
        ThreadGroup group1 = new ThreadGroup("Group1");

        Thread t1 = new Thread(group1, () -> {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                System.out.println("t1 被打断啦 ...");
            }
        });
        Thread t2 = new Thread(group1, () -> {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                System.out.println("t2 被打断啦 ...");
            }

        });
        Thread t3 = new Thread(group1, () -> {
            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                System.out.println("t3 被打断啦 ...");
            }
        });

        /*
            2. 启动线程
         */
        t1.start();
        t2.start();
        t3.start();
        Thread.sleep(1000);
        group1.list();
        System.out.println("group1.size ->  " + group1.activeCount());
        System.out.println("----");
        System.out.println("==== 启动 thread4，验证 activeCount 含义（只返回活跃数量） ====");

        Thread.sleep(1000);
        System.out.println("==== 创建子group ====");
        ThreadGroup group2 = new ThreadGroup(group1, "Group2");
        Thread t21 = new Thread(group2, () -> {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("t21 被打断啦 ...");
            }
        });
        t21.start();
        Thread.sleep(1000);
        System.out.println("遍历下 group1 ,观察是否加入了...");
        group1.list();
        System.out.println("遍历下 group2 ...");
        group2.list();
        System.out.println("最后遍历下最牛逼的 group-main ...");
        Thread.currentThread().getThreadGroup().list();

        Thread.sleep(1000);
        /*
            interrupt
         */
        System.out.println("=== 最后打断全部线程 ===");
//        group1.destroy();
        group1.interrupt();

        System.out.println("main thread end ...");
    }


    public static void testCopy() throws Exception {
        ThreadGroup group1 = new ThreadGroup("Group1");
        System.out.println("----");


        Thread t1 = new Thread(group1, () -> {
            Scanner sc = new Scanner(System.in);
            System.out.println("点击任意键唤醒线程 ...");
            sc.nextLine();
        });
        Thread t2 = new Thread(group1, () -> {
            Scanner sc = new Scanner(System.in);
            System.out.println("点击任意键唤醒线程 ...");
            sc.nextLine();

        });
        Thread t3 = new Thread(group1, () -> {
            Scanner sc = new Scanner(System.in);
            System.out.println("点击任意键唤醒线程 ...");
            sc.nextLine();
        });

        group1.list();
        System.out.println("group1.size ->  " + group1.activeCount());

        Thread.sleep(1000);
        System.out.println("----");

        t1.start();
        t2.start();
        t3.start();

        /*
            2. 开始 copy ，保留原 group
            复制threads
         */
        System.out.println("=== 2. begin to copy === ");
        Thread[] threads = new Thread[group1.activeCount()];
        group1.enumerate(threads);
        // 上面仅仅是复制，没有启动。
        System.out.println("group1.size ->  " + group1.activeCount());
        System.out.println("----");
        Arrays.stream(threads).forEach(System.out::println);

        Thread.sleep(1000);

        System.out.println(threads[0] == t1);
        System.out.println(threads[1] == t2);
        System.out.println(threads[2] == t3);
        System.out.println("threads 复制 enumerate 仅仅是浅复制 ...");
        System.out.println("group1.size ->  " + group1.activeCount());
        System.out.println("----");

        Thread.sleep(1000);

        /*
            复制 group
         */
        System.out.println("=== 第二波复制 ===");
        System.out.println("Thread.currentThread().getThreadGroup().activeGroupCount() -> " +
                Thread.currentThread().getThreadGroup().activeGroupCount());
        Thread.currentThread().getThreadGroup().list();
        ThreadGroup[] groups = new ThreadGroup[Thread.currentThread().getThreadGroup().activeGroupCount()];

        Thread.currentThread().getThreadGroup().enumerate(groups);
        // 上面仅仅是复制，没有启动。
        System.out.println("----");

        Thread.sleep(1000);
        System.out.println("=== 复制完了 ===");
        for (ThreadGroup group :
                groups) {
            group.list();
        }

        System.out.println("groups[0] == group1 ? " + (groups[0] == group1));
        System.out.println("group 复制 enumerate 仅仅是浅复制 ...");
        System.out.println("group1.size ->  " + group1.activeCount());
        System.out.println("----");
    }


    public static void testCatchException() throws Exception{
        ThreadGroup g1 = new ThreadGroup("ThreadGroup");
        Thread t1 = new Thread(g1, () -> { throw new RuntimeException(Thread.currentThread() + "自定义的一个RuntimeException...");});
        t1.start();

        Thread.sleep(1000);

        CatchExceptinoThreadGroup g2 = new CatchExceptinoThreadGroup("CatchExceptionThreadGroup");
        Thread t2 = new Thread(g2, () -> { throw new RuntimeException(Thread.currentThread() + "自定义的一个RuntimeException...");});
        t2.start();
    }

    public static class CatchExceptinoThreadGroup extends ThreadGroup{
        public CatchExceptinoThreadGroup(String name) {
            super(name);
        }
        public CatchExceptinoThreadGroup(ThreadGroup parent, String name) {
            super(parent, name);
        }
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            // 这里可以写 if else 处理各种各样的异常
            if(e instanceof RuntimeException){
                System.out.println("### CatchExceptinoThreadGroup catch " + e);
            }
        }
    }

}
