package indi.sword.util.concurrent;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * newCachedThreadPool 创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。 (线程可复用)
 * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
 * newScheduledThreadPoolSchedule 创建一个定长线程池，支持定时及周期性任务执行。
 * newSingleThreadExecutor 创建一个单线程化的线程池，它只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序(FIFO, LIFO, 优先级)执行。
 *
 * @author rd_jianbin_lin
 * @Descrption
 * @Date Sep 4, 2017 7:08:05 PM
 */
public class _17_00_TestThreadPool_4Common {
    public static void main(String[] args) throws Exception {
//        method_newCachedThreadPool();
//        method_newFixedThreadPool();
        newScheduledThreadPoolSchedule();
//		method_newSingleThreadExecutor();
//		testDelayScheduledThreadPool();
//        method_callable_fixedThread();
//        System.out.println("并发数：" + Runtime.getRuntime().availableProcessors());
    }


    public static void testDelayScheduledThreadPool() {
        ScheduledExecutorService imgUrlProducerThreadPool = Executors.newScheduledThreadPool(4);
        for (int i = 0; i < 4; i++) {
            imgUrlProducerThreadPool.scheduleAtFixedRate(new Runnable() {
                                                             public void run() {
                                                                 System.out.println(Thread.currentThread().getName() + "- begin - ");
                                                                 try {
                                                                     Thread.sleep(5000);
                                                                 } catch (InterruptedException e) {
                                                                     e.printStackTrace();
                                                                 }
                                                                 System.out.println(Thread.currentThread().getName() + "- end - ");

                                                             }
                                                         },
                    1, 1, TimeUnit.SECONDS);
        }

    }

    /**
     * newCachedThreadPool创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。
     * 线程池为无限大，当执行第二个任务时第一个任务已经完成，会复用执行第一个任务的线程，而不用每次新建线程。
     *
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Sep 4, 2017 7:32:33 PM
     */
    public static void method_newCachedThreadPool() {
        // 1、创建线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        // 2、分配任务
        for (int i = 0; i < 20; i++) {
            final int index = i;
            pool.execute(() -> {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "-" + index);
                    }
            );
        }
        // 3、关闭线程池
        pool.shutdown();
    }


    /**
     * newFixedThreadPool 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
     * 因为线程池大小为4，每个任务输出index后sleep 2秒，所以每两秒打印4个数字。
     * 定长线程池的大小最好根据系统资源进行设置。如Runtime.getRuntime().availableProcessors()
     *
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Sep 4, 2017 7:32:51 PM
     */
    public static void method_newFixedThreadPool() {
        // 1、创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(4);

        // 2、分配任务
        for (int i = 0; i < 10; i++) {
            final int index = i;
            pool.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "-" + index);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        // 3、关闭线程池
        pool.shutdown();
    }

    /**
     * 线程池配置，也就是说，当你有n个线程要跑的时候，
     * 规定线程池里面到底能同时跑几个线程，是固定线程数，然后剩余的排队等待，
     * 还是说，不上限，要几个有几个。等等一系列配置。
     * <p>
     * newScheduledThreadPoolSchedule 创建一个定长线程池，支持定时及周期性任务执行。
     * 表示延迟1秒后每2秒执行一次。
     *
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Sep 4, 2017 7:34:13 PM
     */
    public static void newScheduledThreadPoolSchedule() {
        // 1、创建线程池
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(3);

        // 2、分配任务
        // 启动了两个定时的任务，在JVM开启后的1分钟后，每隔2分钟，开始执行一次。
        LoopCount loopCount = new LoopCount();

        for (int i = 0; i < 2; i++) {
            final int index = i;
            pool.scheduleAtFixedRate(() -> System.out.println(Thread.currentThread().getName() + "-" + index + "_count_" + loopCount.getCount().incrementAndGet()),
                    1, 2, TimeUnit.SECONDS);
        }
    }

    @Data
    static class LoopCount{
        private AtomicInteger count = new AtomicInteger(0);
    }

    public static void method_newSingleThreadExecutor() {
        // 1、创建线程池
        ExecutorService pool = Executors.newSingleThreadExecutor();
        // 2、分配任务
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            final int index = i;
            pool.execute(() -> System.out.println(Thread.currentThread().getName() + "-" + index));
        }
        // 3、关闭线程池
        pool.shutdown();
    }

    public static void method_callable_fixedThread() {
        // 1、创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(4);

        final List<Callable<Integer>> partitions = new ArrayList<>();

        // 2、分配任务
        for (int i = 0; i < 4; i++) {
            final int index = i;
            partitions.add(new Callable<Integer>() {
                public Integer call() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "-" + index);
                    return 1;
                }
            });

        }
        final ExecutorService executorPool = Executors
                .newFixedThreadPool(4);
        try {
            final List<Future<Integer>> resultFromParts = executorPool.invokeAll(partitions);
            for (Future<Integer> future : resultFromParts) {
                System.out.println(future.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 3、关闭线程池
        pool.shutdown();
    }
}
