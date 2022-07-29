package indi.sword.util.concurrent;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description
 * @Author jeb_lin
 */

// 3.6 变量在父线程的修改，影响了子线程(改了指针指向的对象内的内容）
public class _22_05_TestInheritableThreadLocal {
    // 加个lock 控制流程顺序
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    // 1.通过匿名内部类覆盖ThreadLocal的initialValue()方法，指定初始值
    private static ThreadLocal<Wallet> walletThreadLocal = new InheritableThreadLocal();

    public static void main(String[] args) throws Exception {

        // 1. 设置 balance
        walletThreadLocal.set(new Wallet(20));
        new Thread(new TaskDemo(),"A").start();

        // sleep 让子线程先走，看下效果
        Thread.sleep(2000);

        System.out.println(Thread.currentThread().getName() + walletThreadLocal.get());

        lock.lock();
        // 2. 修改 balance 的值，看下子线程的效果
//        walletThreadLocal.set(new Wallet(40));
        walletThreadLocal.get().setBalance(40);
        System.out.println(Thread.currentThread().getName() + " set New balance ===");
        condition.signal();
        lock.unlock();

        // sleep 让子线程先走，看下效果
        Thread.sleep(2000);
        System.out.println(Thread.currentThread().getName() + walletThreadLocal.get());
    }

    private static class TaskDemo implements Runnable {
        public void run() {
            // 看下父线程的变量能否继承袭来
            System.out.println(Thread.currentThread().getName() + walletThreadLocal.get());
            try {
                lock.lock();
                condition.await(); // 注意 await 本身会释放 lock

                // 看下父线程的修改对子类的影响
                System.out.println(Thread.currentThread().getName() + walletThreadLocal.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
    @Data
    @AllArgsConstructor
    private static class Wallet{
        private int balance;
        @Override
        public String toString(){
            return "Wallet.balance = [" + balance + "]";
        }

    }
}
