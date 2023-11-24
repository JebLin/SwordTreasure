package indi.sword.util.basic.Thread.Volatile;

/**
 * @author jeb_lin
 * 5:27 PM 2022/7/29
 */
public class TestCpuCache {
    // 每个缓存行CacheLine 占用64个字节
    public static volatile long[] arr1 = new long[2]; // 每个long占用8个字节
    public static volatile long[] arr2 = new long[16]; // 每个long占用8个字节

    public static void main(String[] args) throws InterruptedException {
//        testCacheLine1();
        testCacheLine2();
    }


    public static void testCacheLine1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 20_0000_0000L; i++) {
                arr1[0] = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 20_0000_0000L; i++) {
                arr1[1] = i; //注意这里的区别
            }
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - start) / 100_0000 + " ms");
    }

    public static void testCacheLine2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 20_0000_0000L; i++) {
                arr2[0] = i;
            }
        });
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 20_0000_0000L; i++) {
                arr2[8] = i; //注意这里的区别， 每个long占用8个字节，所以下标8表示第9个元素，也就是与上面的下标0的元素不处于同一缓存行
            }
        });

        final long start = System.nanoTime();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println((System.nanoTime() - start) / 100_0000 + " ms");
    }


}
