package indi.sword.util.basic.exception.multiThread;

import java.util.concurrent.*;

/**
 * @Description 测试一下Exception之后，线程池中的异常线程有没有被剔除掉
 * @Author rd_jianbin_lin
 * @Date 17:52 2018/2/4
 * @Modified By
 */
public class _03_TestExceptionInThreadCount {
    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        threadPoolExecutor.execute(() -> {
            throw new RuntimeException("aaa");
        });

        threadPoolExecutor.execute(() -> {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(threadPoolExecutor.getActiveCount());
        System.out.println(threadPoolExecutor.getTaskCount());

        threadPoolExecutor.shutdown();
    }
}

