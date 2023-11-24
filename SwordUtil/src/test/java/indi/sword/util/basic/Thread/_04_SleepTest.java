package indi.sword.util.basic.Thread;

/**
 * @Description:
 * @Author: rd_jianbin_lin
 * @Date:16:03 2017/12/3
 */
/*
    sleep方法的作用是让当前线程暂停指定的时间（毫秒），sleep方法是最简单的方法，
    在上述的例子中也用到过，比较容易理解。唯一需要注意的是其与wait方法的区别。
    最简单的区别是，wait方法依赖于同步，而sleep方法可以直接调用。
    而更深层次的区别在于sleep方法只是暂时让出CPU的执行权，并不释放锁。而wait方法则需要释放锁。
 */
public class _04_SleepTest {
    /*
        这里加入 synchronized 的目的，就是为了看下 sleep 的时候，释放不释放锁
     */
    public synchronized void sleepMethod() throws Exception {
        System.out.println(Thread.currentThread().getName() + ",Sleep start-----");
        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName() + ",Sleep end-----");
    }

    public synchronized void waitMethod() throws Exception {
        System.out.println(Thread.currentThread().getName() + ",Wait start-----");
        wait(1000);
        System.out.println(Thread.currentThread().getName() + ",Wait end-----");
    }

    public static void main(String[] args) throws Exception {
        final _04_SleepTest test1 = new _04_SleepTest();

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    test1.sleepMethod();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(10000);//暂停十秒，等上面程序执行完成
        System.out.println("-----分割线-----");

        final _04_SleepTest test2 = new _04_SleepTest();

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    test2.waitMethod();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
/*
        这个结果的区别很明显，通过sleep方法实现的暂停，程序是顺序进入同步块的，
        只有当上一个线程执行完成的时候，下一个线程才能进入同步方法，sleep暂停期间一直持有monitor对象锁，
        其他线程是不能进入的。而wait方法则不同，当调用wait方法后，当前线程会释放持有的monitor对象锁，
        因此，其他线程还可以进入到同步方法，线程被唤醒后，需要竞争锁，获取到锁之后再继续执行。
 */