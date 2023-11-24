package indi.sword.util.concurrent;

/**
 * @author rd_jianbin_lin
 * @Descrption 模拟 CAS 算法
 * @Date Sep 2, 2017 7:31:43 PM
 */
/*
﻿*        1. volatile 保证内存可见性
 *        2. CAS（Compare-And-Swap） 算法保证数据变量的原子性
 *           CAS 算法是硬件对于并发操作的支持
 *           CAS 包含了三个操作数：
 *           ①内存值  V
 *           ②预估值  A
 *           ③更新值  B
 *           当且仅当 V == A 时， V = B; 否则，不会执行任何操作。
 */
public class _03_01_TestCAS {

    public static void main(String[] args) {

        final CAS cas = new CAS();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                int expectedV = cas.get();
                int newV = (int) (Math.random() * 101);
//                    boolean b = cas.compareAndSet(expectedV, newV);
                /* 设置不成功就继续去update该位置的值，也就是取最新值 */
                boolean insertSuccess = newV == cas.compareAndSwap(expectedV, newV);
                while (!insertSuccess) {
                    System.out.println(Thread.currentThread().getName() + " retry insert failed ... expectedV before = " + expectedV + ", newV = " + newV);
                    expectedV = cas.get();
                    System.out.println(Thread.currentThread().getName() + " retry insert failed ... expectedV after = " + expectedV + ", newV = " + newV);
                    insertSuccess = newV == cas.compareAndSwap(expectedV, newV);
                }
            }, "Thread-" + i).start();
        }

    }

}

class CAS {

    private int value;

    //获取内存值
    public synchronized int get() {
        return value;
    }

    //比较+设置
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        System.out.println(Thread.currentThread().getName() + ",expectedValue = " + expectedValue +
                ", this.value = " + this.value + " ,newValue -> " + newValue +
                ",this.value == expectedValue? -> " + String.valueOf(this.value == expectedValue));

        if (this.value == expectedValue) {
            this.value = newValue;
        }

        return this.value;
    }
}