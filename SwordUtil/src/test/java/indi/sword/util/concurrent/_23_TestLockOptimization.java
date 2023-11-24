package indi.sword.util.concurrent;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 11:52 AM 24/05/2018
 * @MODIFIED BY
 */
public class _23_TestLockOptimization {

    // =========== 锁细化 ===========

    public void elimination01() {
        Object object = new Object();
        synchronized (object) {
            System.out.println("hello world ...");
        }
    }

    // 锁消除： 执行效率是一样的，因为object锁是私有变量，不存在所得竞争关系。
    public void elimination02() {
        Object object = new Object();
        System.out.println("hello world ...");
    }

    public void elimination03() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("a");
        stringBuffer.append("b");
        stringBuffer.append("c");
    }

    // =========== 锁粗化 ===========
    public void coarsening01() {
        for (int i = 0; i < 100000; i++) {
            synchronized (this) {
                System.out.println("hello world ...");
            }
        }
    }

    // 锁粗化：通过扩大锁的范围，避免反复加锁和释放锁。
    public void coarsening02() {
        synchronized (this) {
            for (int i = 0; i < 100000; i++) {
                System.out.println("hello world ...");
            }
        }
    }

}
