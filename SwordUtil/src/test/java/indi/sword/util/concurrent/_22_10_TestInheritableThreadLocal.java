package indi.sword.util.concurrent;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author jeb_lin
 */

public class _22_10_TestInheritableThreadLocal {

    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    public static ThreadLocal<Integer> inheritableThreadLocal = new InheritableThreadLocal<>();
    public static ThreadLocal<User> mutableInheritableThreadLocal = new InheritableThreadLocal<>();
    public static ThreadLocal<User> mutableInheritableThreadLocalTo = new InheritableThreadLocal<>();
    public static ThreadLocal<String> immutableInheritableThreadLocal = new InheritableThreadLocal<>();
    public static ThreadLocal<String> immutableInheritableThreadLocalTo = new InheritableThreadLocal<>();

    public static void main(String args[]) throws InterruptedException {
        // 测试0.ThreadLocal普通测试;
        // 结论0: ThreadLocal下子线程获取不到父线程的值
        threadLocal.set(new Integer(123)); // 父线程初始化

        Thread thread = new MyThread();
        thread.start();

        TimeUnit.MILLISECONDS.sleep(100); // 睡眠, 以等待子线程执行完毕
        System.out.println("1. main = " + threadLocal.get());
        System.out.println();

        // 测试1.InheritableThreadLocal普通测试;
        // 结论1: InheritableThreadLocal下子线程可以获取父线程的值
        inheritableThreadLocal.set(new Integer(123)); // 父线程初始化

        Thread threads = new MyThread2();
        threads.start();

        TimeUnit.MILLISECONDS.sleep(100); // 睡眠, 以等待子线程执行完毕
        System.out.println("2. main = " + inheritableThreadLocal.get());
        System.out.println();

        // 测试2.父线程和子线程的传递关系测试: 可变对象, 父线程初始化;
        // 结论2: 父线程初始化, Thread Construct浅拷贝, 共用索引, 子线程先get()对象, 再修改对象的属性,
        // 父线程跟着变, 注意: 此处子线程如果没有先get()直接使用set()一个新对象, 父线程是不会跟着变的
        mutableInheritableThreadLocal.set(new User("user1"));// 2.1父线程初始化

        Thread TestThread = new TestThread(); // 2.2先初始化父线程再创建子线程, 确保子线程能继承到父线程的User
        TestThread.start(); // 开始执行子进程

        TimeUnit.MILLISECONDS.sleep(100); // 睡眠, 以等待子线程执行完毕
        System.out.println("3. main = " + mutableInheritableThreadLocal.get()); // 2.5此处输出值为子线程修改的值, 因此可得出上述结论2
        System.out.println();




        // 测试3.父线程和子线程的传递关系测试: 可变对象, 父线程不初始化;
        // 结论3: 父线程没有初始化, 子线程初始化, 无Thread Construct浅拷贝, 子线程和主线程都是单独引用, 不同对象,
        // 子线程修改父线程不跟着变
        mutableInheritableThreadLocalTo.set(new User("user11"));// 2.1父线程初始化
        Thread TestThreadTo = new TestThread2(); // 3.1创建子进程
        TestThreadTo.start();

        TimeUnit.MILLISECONDS.sleep(100); // 睡眠, 以等待子线程执行完毕
        System.out.println("4. main = " + mutableInheritableThreadLocalTo.get()); // 3.3 此处输出为null, 可得出上述结论3
        System.out.println();

        // 测试4.父线程和子线程的传递关系测试: 不可变对象, 父线程初始化;
        // 结论4: 父线程初始化, Thread Construct浅拷贝, 但由于不可变对象由于每次都是新对象, 所以互不影响
        immutableInheritableThreadLocal.set("user2");// 4.1父线程初始化

        Thread TestThreadTre = new TestThread3(); // 4.2先初始化父线程再创建子线程, 确保子线程能继承到父线程的值
        TestThreadTre.start(); // 执行子进程

        TimeUnit.MILLISECONDS.sleep(100); // 睡眠, 以等待子线程执行完毕
        System.out.println("5. main = " + immutableInheritableThreadLocal.get()); // 4.5此处输出为父线程的值, 因此可得出上述结论4
        System.out.println();

        // 测试5.父线程和子线程的传递关系测试: 不可变对象, 父线程不初始化;
        // 结论5: 父线程没有初始化, 子线程初始化, 无Thread Construct浅拷贝, 子线程和父线程操作不同对象, 互不影响
        Thread TestThreadFour = new TestThread4(); // 5.1创建子线程
        TestThreadFour.start();

        TimeUnit.MILLISECONDS.sleep(100); // 睡眠, 以等待子线程执行完毕
        System.out.println("6. main = " + immutableInheritableThreadLocalTo.get()); // 5.3此处输出为空, 因此可得出上述结论5
    }

    private static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread = " + threadLocal.get());
        }
    }

    private static class MyThread2 extends Thread {
        @Override
        public void run() {
            System.out.println("inheritableThreadLocal = " + inheritableThreadLocal.get());
        }
    }

    private static class TestThread extends Thread {
        @Override
        public void run() {
            // 2.3此处输出父线程的初始化对象值, 代表子线程确实继承了父线程的对象值
            System.out.println("TestThread.before = " + mutableInheritableThreadLocal.get());
            // 2.4子类拿到对象并修改
            mutableInheritableThreadLocal.get().setName("getAndSet1");
            System.out.println("mutableInheritableThreadLocal = " + mutableInheritableThreadLocal.get());
        }
    }

    private static class TestThread2 extends Thread {
        @Override
        public void run() {
            mutableInheritableThreadLocalTo.set(new User("directSet2"));// 3.2子线程调用set方法
            System.out.println("mutableInheritableThreadLocalTo = " + mutableInheritableThreadLocalTo.get());
        }
    }

    private static class TestThread3 extends Thread {
        @Override
        public void run() {
            // 4.3此处输出父线程初始化的值, 代表子线程确实继承了父线程的对象值
            System.out.println("TestThread3.before = " + immutableInheritableThreadLocal.get());
            // 4.4子线程调用set方法
            immutableInheritableThreadLocal.set("whee");
            System.out.println("immutableInheritableThreadLocal = " + immutableInheritableThreadLocal.get());
        }
    }

    private static class TestThread4 extends Thread {
        @Override
        public void run() {
            immutableInheritableThreadLocalTo.set("whee");// 5.2子线程调用set方法
            System.out.println("immutableInheritableThreadLocalTo = " + immutableInheritableThreadLocalTo.get());
        }
    }

    @Data
    @AllArgsConstructor
    private static class User {
        String name;

        @Override
        public String toString(){
            return "User[" + name + "]";
        }
    }
}
