package indi.sword.util.concurrent;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 20:43 2018/2/11
 * @Modified By
 */

public class _08_05_SynchronizedBlocked implements Runnable{
    public synchronized void test() {
        System.out.println(Thread.currentThread() + " test begin ");
        while(true){
            if(Thread.currentThread().isInterrupted()){
                Thread.yield(); // yield 也是不释放锁的
                System.out.println(Thread.currentThread() + " is Interrupted .. but do not release lock .");
            }else {
                System.out.println(Thread.currentThread() + "normal ");
            }
        }
    }

    public void run() {
        System.out.println(Thread.currentThread() + " enter run ");
        test();
    }

    public static void main(String[] args) throws InterruptedException {
        _08_05_SynchronizedBlocked sync = new _08_05_SynchronizedBlocked();
        Thread t1 = new Thread(sync,"AA");
        Thread t2 = new Thread(sync,"BB");
        //启动后调用f()方法,无法获取当前实例锁处于等待状态
        t1.start();
        t2.start();
        Thread.sleep(1);
        //中断线程,无法生效
        t1.interrupt();
        // 终止线程，可以生效，但是不利于安全
        t1.stop();
        System.out.println("t1.interrupt() send ...");
    }
}
