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
public class _16_02_TestThreadPool {
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

    public static void main(String[] args) {
        testUseFuture();
    }

    private static void testUseFuture() {
        int numThread = 5;
        ExecutorService executor = Executors.newFixedThreadPool(numThread);
        List<Future<String>> futureList = new ArrayList<Future<String>>();
        for (int i = 0; i < numThread; i++) {
            Future<String> future = executor.submit(new _16_02_TestThreadPool.Task(i));
            futureList.add(future);
        }

        while (numThread > 0) {
            for (Future<String> future : futureList) {
                String result = null;
                try {
                    result = future.get(0, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    //超时异常直接忽略
                }
                if (null != result) {
                    futureList.remove(future);
                    numThread--;
                    System.out.println(result);
                    //此处必须break，否则会抛出并发修改异常。（也可以通过将futureList声明为CopyOnWriteArrayList类型解决）
                    break;
                }
            }
        }
        executor.shutdown();
    }
}

