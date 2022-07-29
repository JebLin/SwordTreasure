package indi.sword.util.concurrent;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author jeb_lin
 */
 // 3.9 变量若一开始父线程没有初始化，那么后续的 set 对子线程没影响。
public class _22_06_TestInheritableThreadLocal {
    // 加个lock 控制流程顺序
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    // 1.通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值
    private static ThreadLocal<Integer> balance = new InheritableThreadLocal();

    public static void main(String[] args) throws Exception {

        // 1. 设置 balance
        new TaskDemo().start();

        // sleep 让子线程先走，看下效果
        Thread.sleep(2000);

        System.out.println(Thread.currentThread().getName() + " --> balance["
                + balance.get() + "]");

        lock.lock();
        // 2. 修改 balance 的值，看下子线程的效果
        balance.set(40);
        System.out.println(Thread.currentThread().getName() + " set New balance ===");
        condition.signal();
        lock.unlock();

        // sleep 让子线程先走，看下效果
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + " --> balance["
                + balance.get() + "]");
    }

    private static class TaskDemo extends Thread {
        public void run() {
            // 看下父线程的变量能否继承袭来
            System.out.println(Thread.currentThread().getName() + " --> balance["
                    + balance.get() + "]");
            try {
                lock.lock();
                condition.await(); // 注意 await 本身会释放 lock

                // 看下父线程的修改对子类的影响
                System.out.println(Thread.currentThread().getName() + " --> balance["
                        + balance.get() + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
