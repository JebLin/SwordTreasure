package indi.sword.util.concurrent;

/**
 * @Description
 * @Author jeb_lin
 */
//  3.1 实现数值的复制传递
public class _22_02_TestInheritableThreadLocal {

    // 1.通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值
    private static ThreadLocal<Integer> balance = new InheritableThreadLocal();
//            InheritableThreadLocal.withInitial(() -> 100); // 假设初始账户有100块钱


    public static void main(String[] args) throws Exception {
        balance.set(20);

        new Thread(new TaskDemo(), "A").start();

        // 让子线程先走
        Thread.sleep(2000);

        System.out.println(Thread.currentThread().getName() + " --> balance["
                + balance.get() + "]");
    }

    private static class TaskDemo implements Runnable {
        public void run() {
            System.out.println(Thread.currentThread().getName() + " --> balance["
                    + balance.get() + "]");
        }
    }
}
