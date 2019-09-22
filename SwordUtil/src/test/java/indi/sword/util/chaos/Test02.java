package indi.sword.util.chaos;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jeb_lin
 * 11:13 AM 2019/9/20
 */
public class Test02 {
    private final int threadLocalHashCode = nextHashCode();

    /**
     * The next hash code to be given out. Updated atomically. Starts at
     * zero.
     */
    private static AtomicInteger nextHashCode =
            new AtomicInteger();

    /**
     * The difference between successively generated hash codes - turns
     * implicit sequential thread-local IDs into near-optimally spread
     * multiplicative hash values for power-of-two-sized tables.
     */
    private static final int HASH_INCREMENT = 0x61c88647;

    /**
     * Returns the next hash code.
     */
    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }



    public static void main(String[] args) {
        Test02 test02 = new Test02();


        new Thread(() -> {System.out.println(Thread.currentThread().getName() + " _ " + test02.threadLocalHashCode);}).start();
        new Thread(() -> {System.out.println(Thread.currentThread().getName() + " _ " + test02.threadLocalHashCode);}).start();
        new Thread(() -> {System.out.println(Thread.currentThread().getName() + " _ " + test02.threadLocalHashCode);}).start();



    }
}
