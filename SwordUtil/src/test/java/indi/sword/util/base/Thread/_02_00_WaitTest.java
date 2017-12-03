package indi.sword.util.base.Thread;


/**
 * @Description:
 * @Author: rd_jianbin_lin
 * @Date:15:16 2017/12/3
 */
/*
    必须加synchronized :
        wait方法的使用必须在同步的范围内，否则就会抛出 IllegalMonitorStateException 异常，
        wait方法的作用就是阻塞当前线程等待notify/notifyAll方法的唤醒，或等待超时后自动唤醒。
    IllegalMonitorStateException：
         * Thrown to indicate that a thread has attempted to wait on an
         * object's monitor or to notify other threads waiting on an object's
         * monitor without owning the specified monitor.
        线程试图等待对象的监视器或者试图通知其他正在等待对象监视器的线程，但本身没有对应的监视器的所有权。
        wait方法是一个本地方法，其底层是通过一个叫做监视器锁的对象来完成的。
        所以上面之所以会抛出异常，是因为在调用wait方式时没有获取到monitor对象的所有权.
 */
public class _02_00_WaitTest {

    public synchronized void testWait(){
        System.out.println("Start-----");
        try {
            wait(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("End-------");
    }

    public static void main(String[] args) {
        final _02_00_WaitTest test = new _02_00_WaitTest();
        System.out.println("test begin...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                test.testWait(); // thread调用wait方法
            }
        }).start();
        System.out.println("test end...");
    }
}
