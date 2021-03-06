package indi.sword.util.basic.Thread;

/**
 * @Description:
 * @Author: rd_jianbin_lin
 * @Date:16:08 2017/12/3
 */
/*
    yield方法的作用是暂停当前线程，以便其他线程有机会执行，不过不能指定暂停的时间，
    并且也不能保证当前线程马上停止。yield方法只是将Running状态转变为Runnable状态。
 */
public class _05_02_YieldTest {
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 3; i++) {
            System.out.println("Main " + Thread.currentThread() + ": " + i);
            Thread.yield();
        }

    }
}
/*
    这个例子就是通过yield方法来实现两个线程的交替执行。
    不过请注意：这种交替并不一定能得到保证，源码中也对这个问题进行说明：
    主要说明了三个问题：
    　　调度器可能会忽略该方法。
    　　使用的时候要仔细分析和测试，确保能达到预期的效果。
    　　很少有场景要用到该方法，主要使用的地方是调试和测试。　　
 */