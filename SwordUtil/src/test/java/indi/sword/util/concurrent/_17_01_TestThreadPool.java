package indi.sword.util.concurrent;

import java.util.concurrent.*;

public class _17_01_TestThreadPool {
    public static void main(String[] args) throws Exception {

        /** ===== SynchronousQueue ==== **/
        //1.  每次+3，塞满 core ，无法在 queue 排队， 然后 core 也忙，但是可以丢到 max 里面去执行。
        testQueue(6, 8, new SynchronousQueue<>());

        //2. 每次+3，塞满 core ，无法在 queue 排队， 然后 core 也忙，但是可以丢到 max 里面去执行。
//        testQueue(3, 6, new SynchronousQueue<>());

        //3. 每次+3，塞满 core ，无法在 queue 排队， 然后 core 也忙，但是可以丢到 max 里面去执行。max 满了，抛异常。
//        testQueue(3, 5, new SynchronousQueue<>());// RejectedExecutionException 会导致整个 executor 停止

        /** ===== LinkedBlockingDeque ==== **/
        // 1. 每次+3，塞满 core ，在 queue 里面排队， 由于 LinkedBlockingDeque 无限制数量，所以可以无限数量的 task 排队，然后core空闲了就去queue取task
//        testQueue(1, 3, new LinkedBlockingDeque<>());

        // 2. 每次+3，塞满 core ，在 queue 里面排队，queue满了，直接丢 max 去执行，后期回收 max 的位置
//        testQueue(1, 5, new LinkedBlockingDeque<>(1));

        // 3. 每次+3，塞满 core ，在 queue 里面排队， queue 满里，max 也满了 ，抛出异常
//        testQueue(1, 3, new LinkedBlockingDeque<>(1));
    }

    /**
     * 队列任务数永远是 0
     *
     * @author jeb_lin
     * 5:32 PM 2019/10/24
     */
    public static void testQueue(int corePoolSize,
                                 int maximumPoolSize,
                                 BlockingQueue<Runnable> workQueue) throws Exception {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 5, TimeUnit.SECONDS, workQueue);
        try {
            System.out.println("输入： corePoolSize -> " + corePoolSize + ", maximumPoolSize -> " + maximumPoolSize);
            executor.execute(new MyRunnable(1));
            executor.execute(new MyRunnable(2));
            executor.execute(new MyRunnable(3));
            System.out.println("---先开三个---");
            print(executor);
            executor.execute(new MyRunnable(4));
            executor.execute(new MyRunnable(5));
            executor.execute(new MyRunnable(6));
            System.out.println("---再开三个---");
            print(executor);
            Thread.sleep(8000);
            System.out.println("----8秒之后----");
            print(executor);
        } finally {
            executor.shutdown();

        }
    }

    private static void print(ThreadPoolExecutor executor) {
        System.out.println("核心线程数 -> " + executor.getCorePoolSize()
                + ", 线程池数 -> " + executor.getPoolSize()
                + ", 队列任务数 -> " + executor.getQueue().size()
                + ", queue -> " + executor.getQueue().toString());
    }


    private static class MyRunnable implements Runnable {
        private int index;
        public MyRunnable(int index){
            this.index = index;
        }
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
                System.out.println("thread-" + this.index + " run");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        public String toString(){
            return "thread-" + index;
        }

    }
}
