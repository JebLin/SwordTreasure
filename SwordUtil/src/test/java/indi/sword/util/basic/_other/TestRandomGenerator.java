package indi.sword.util.basic._other;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Description 测试产生随机数据性能
 * <p>
 * java.util.Random
 * java.util.Random 从Java 1.0开始就存在了。它是一个线程安全类，理论上可以通过它同时在多个线程中获得互不相同的随机数。
 * 这样的线程安全是通过AtomicLong实现的。
 * Random 使用 AtomicLong CAS （compare-and-set）操作来更新它的seed，尽管很多非阻塞式算法中使用了非阻塞式原语，CAS在资源高度竞争时的表现依然糟糕。
 * <p>
 * java.util.concurrent.ThreadLocalRandom
 * Java 7增加了java.util.concurrent.ThreadLocalRandom 并企图将它与 java.util.Random 结合以克服所有的性能问题。
 * ThreadLocalRandom类继承自java.util.Random。
 * <p>
 * ThreadLocalRandom 的主要实现细节：
 * 它使用一个普通的 long 而不是使用 Random 中的 AtomicLong 作为seed。
 * 你不能自己创建ThreadLocalRandom实例，因为它的构造函数没有设置为public。可以使用它的静态工厂ThreadLocalRandom.current()，
 * 这个工厂方法调用了内置的ThreadLocal<ThreadLocalRandom>。
 * 它是CPU缓存感知式的，使用8个 long 虚拟域来填充64位L1高速缓存行。
 * <p>
 *
 * Shared java.util.Random
 * 第一个测试使用的是共享的java.util.Random实例。高争夺的CAS操作严重影响了它的性能。仅仅开两个线程都会受争夺的影响，然后现实中很少会发生这种争夺的情况。
 * “Shared” java.util.concurrent.ThreadLocalRandom
 * 接下来的测试使用第二个类——java.util.concurrent.ThreadLocalRandom。 如你所见，在程序运行的线程数低于CPU的线程数时性能没有下降，
 * 当程序运行的线程数超过CPU的线程数时性能才线性的降低。另一个要注意的重点是，单一线程执行的效率是第一个案例的3倍——无竞争的CAS操作仍然表现糟糕。
 * “Shared” ThreadLocal<java.util.Random>
 * 把java.util.Random实例装入ThreadLocal后执行的效率有些不一样，当线程数超过CPU核心数时性能就下降了——听起来像是CAS操作不能执行那么多单元。不过接下来的性能下降是线性的，和第二个案例很相似。
 * Array of java.util.Random
 * 最后我想要检查CPU缓存行对ThreadLocalRandom 的改善作用和模拟java.util.Random在缺乏这种功能下的情况。你需要做的就是创建可以被很多线程使用的java.util.Random实例，我用java.util.Random[]来实现此目的并用array[N]表示第N个线程。
 *
 *
 * 总结
 *
 * 任何情况下都不要在多个线程间共享一个java.util.Random实例，而该把它放入ThreadLocal之中。
 * Java7在所有情形下都更推荐使用java.util.concurrent.ThreadLocalRandom——它向下兼容已有的代码且运营成本更低。
 *
 * 推荐下面的  _04_testRandom_ThreadLocal
 * 注意： _02_testRandomArray(COUNT_THREADS, COUNT, 2);
 * public Random(long seed)  jdk内部存储了一堆无序序列表，seed = 1表明第一张表，=2 表示第二张表。所以如果 seed相同，那么多线程会产生相同的随机数序列
 * Waring!Waring!Waring!种子都是一样的，生成的随机表都是一样的
 *
 * @Author:rd_jianbin_lin
 * @Date: 10:53 2017/9/16
 */
public class TestRandomGenerator {

    private static final long COUNT = 50000000; // 迭代次数
    private static final int COUNT_THREADS = 3; // 线程数目


    public static void main(String[] args) {


        /**
         *  jdk 1.2
         *  使用share的Random进行随机数生成
         *  一个单独的java.util.Random被N个线程共享
         */

//        _01_testRandom_shared(COUNT_THREADS, COUNT);   // 5 7 7

        /**
         * jdk 1.2
         * RandomArray 一个线程一个Random实例！！！Waring!Waring!Waring!种子都是一样的，生成的随机表都是一样的
         */
//        _02_testRandomArray(COUNT_THREADS, COUNT, 1); // 0.61 0.66 0.70
//        _02_testRandomArray(COUNT_THREADS, COUNT, 2); // 0.59 0.67 0.72

        /**
         *  jdk 1.2
         *  使用java.lang.ThreadLocal<T>  进行随机数生成
         */
//        _03_testRandom_ThreadLocalRandom(COUNT_THREADS, COUNT); // 0.587 0.714 0.74


        /**
         * jdk 1.7
         * 使用java.util.concurrent.ThreadLocalRandom   highly recommend !!!
         * ThreadLocalRandom.current()
         */
//        _04_testRandom_ThreadLocal(COUNT_THREADS, COUNT); // 0.118 0.132 0.123


    }

    // runner for all tests
    private static class RandomTask implements Runnable {
        private final Random random;
        protected final int id;
        private final long count;
        private final CountDownLatch latch;

        private RandomTask(Random random, int id, long count, CountDownLatch latch) {
            this.random = random;
            this.id = id;
            this.count = count;
            this.latch = latch;
        }

        protected Random getRandom() {
            return random;
        }

        @Override
        public void run() {
            try {
                final Random random = getRandom();
                latch.countDown();
                latch.await();
                final long start = System.currentTimeMillis();
                int sum = 0;
                for (long j = 0; j < count; ++j) {
                    sum += random.nextInt();
                }
                final long time = System.currentTimeMillis() - start;
                System.out.println("Thread #" + id + " Time = " + time / 1000.0
                        + " sec, sum = " + sum);
            } catch (Exception e) {

            }
        }
    }

    /**
     * @Description shared的Random
     * @Author:rd_jianbin_lin
     * @Date: 15:07 2017/9/16
     */
    private static void _01_testRandom_shared(final int threads, final long count) {
        System.out.println("Shared Random");
        // 可以保证所有线程同时开始执行
        final CountDownLatch latch = new CountDownLatch(threads);
        final Random random = new Random(100);

        for (int i = 0; i < threads; ++i) {
            final Thread thread = new Thread(new RandomTask(random, i, count, latch));
            thread.start();
        }
    }


    /**
     * @Description 一个线程一个Random实例 注意，种子都是一样的，生成的随机表都是一样的
     * @Author:rd_jianbin_lin
     * @Date: 15:17 2017/9/16
     */
    private static void _02_testRandomArray(final int threads, final long count,
                                            final int padding) {
        System.out.println("Shared Random[] with padding -> " + padding);
        final CountDownLatch latch = new CountDownLatch(threads);
        final Random[] randoms = new Random[threads * padding];
        for (int i = 0; i < threads * padding; ++i)
            // allocate together
            randoms[i] = new Random(i);
        for (int i = 0; i < threads; ++i) {
            final Thread thread = new Thread(new RandomTask(randoms[i * padding],
                    i, count, latch));
            thread.start();
        }
    }

    /**
     * @Description java.lang.ThreadLocal<Random>
     * @Author:rd_jianbin_lin
     * @Date: 15:04 2017/9/16
     */
    private static void _03_testRandom_ThreadLocalRandom(final int threads, final long count) {
        System.out.println("java.lang.ThreadLocal<Random>");
        final CountDownLatch latch = new CountDownLatch(threads);
        final ThreadLocal<Random> randomThreadLocal = new ThreadLocal<Random>() {
            @Override
            protected Random initialValue() {
                return new Random(100);
            }
        };
        for (int i = 0; i < threads; ++i) {
            final Thread thread = new Thread(
                    new RandomTask(null, i, count, latch) {
                        @Override
                        protected Random getRandom() {
                            return randomThreadLocal.get();
                        }
                    });
            thread.start();
        }
    }

    /**
     * @Description java.util.concurrent.ThreadLocalRandom    highly recommend !!!
     * @Author:rd_jianbin_lin
     * @Date: 15:04 2017/9/16
     */
    private static void _04_testRandom_ThreadLocal(final int threads, final long count) {
        System.out.println("java.util.concurrent.ThreadLocalRandom");
        final CountDownLatch latch = new CountDownLatch(threads);
        for (int i = 0; i < threads; ++i) {
            final Thread thread = new Thread(
                    new RandomTask(null, i, count, latch) {
                        // 改变RandowTask中获取Random的方式
                        @Override
                        protected Random getRandom() {
                            return ThreadLocalRandom.current();  // UNSAFE.getInt
                        }
                    });
            thread.start();
        }
    }


}
