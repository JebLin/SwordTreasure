package indi.sword.util.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 一、线程池：提供了一个线程队列，队列中保存着所有等待状态的线程。避免了创建与销毁额外开销，提高了相应速度。
 * <p>
 * 二、线程池的体系结构：
 * Executor:
 * The {@code Executor} implementations provided in this package
 * implement {@link ExecutorService}, which is a more extensive
 * intef.  The {@link ThreadPoolExecutor} class provides an
 * extensible thread pool implementation. The {@link Executors} class
 * provides convenient factory methods for these Executors.
 * <p>
 * java.util.concurrent.Executor : 负责线程的使用与调度的根接口
 * ||--**ExecutorService 子接口：线程池的主要接口
 * ||--ThreadPoolExecutor 线程池的实现类
 * ||--** ScheduledExecutorService 子接口：负责线程的调度
 * ||-- ScheduledThreadPoolExecutor ：extends ThreadPoolExecutor，implements ScheduledExecutorService
 * <p>
 * 三、工具类 ： Executors
 * <ul>
 * <li> Methods that create and return an {@link ExecutorService}
 * set up with commonly useful configuration settings.
 * <li> Methods that create and return a {@link ScheduledExecutorService}
 * set up with commonly useful configuration settings.
 * <li> Methods that create and return a "wrapped" ExecutorService, that
 * disables reconfiguration by making implementation-specific methods
 * inaccessible.
 * <li> Methods that create and return a {@link ThreadFactory}
 * that sets newly created threads to a known state.
 * <li> Methods that create and return a {@link Callable}
 * out of _other closure-like forms, so they can be used
 * in execution methods requiring {@code Callable}.
 * </ul>
 * <p>
 * 使用最多的就是 上面的<li> 1
 * newCachedThreadPool 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
 * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 * newScheduledThreadPoolSchedule 创建一个定长线程池，支持定时及周期性任务执行。
 * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 *
 * @author rd_jianbin_lin
 * @Descrption
 * @Date Sep 4, 2017 4:20:01 PM
 */
public class _16_03_TestThreadPool {
    static class Task implements Callable<String> {
        private int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(i * 1000);
            return Thread.currentThread().getName() + "执行完任务：" + i;
        }
    }

    public static void main (String[] args)  throws Exception{
        testUseFuture();
    }

    private static void testUseFuture() throws Exception{
        int numThread = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        CompletionService<String> completionService = new ExecutorCompletionService<String>(executor);
        for(int i = 0;i<numThread;i++ ){
            completionService.submit(new _16_03_TestThreadPool.Task(i));
        }

        for(int i = 0;i<numThread;i++ ){
        System.out.println(completionService.take().get());
    }
        executor.shutdown();
    }
}

