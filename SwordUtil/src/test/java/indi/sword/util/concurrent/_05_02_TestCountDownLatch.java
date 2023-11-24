package indi.sword.util.concurrent;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/*
 * @Description
 * CountDownLatch ：闭锁，在完成某些运算是，只有其他所有线程的运算全部完成，当前运算才继续执行.
 * 					可以用于计算数量，平均值等等等等
 *
 * @author rd_jianbin_lin
 * @Date Sep 2, 2017 7:20:01 PM
 */
public class _05_02_TestCountDownLatch {
    public static void main(String[] args) throws Exception {
        System.out.println(Thread.currentThread().getName());
        final CountDownLatch latch = new CountDownLatch(2);

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                Scanner sc = new Scanner(System.in);
                System.out.println("点击任意键终止线程 ...");
                sc.nextLine();
                latch.countDown();
            }, "A_" + i).start();
        }

        // sleep 保证上面先执行
        Thread.sleep(1000);

        // 多个线程await等待，模拟排队
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    latch.await();
                    System.out.println(Thread.currentThread().getName() + " be notified .");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },"C_" + i).start();
        }

        // sleep 保证上面先执行
        Thread.sleep(1000);
        System.out.println("Main latch.await()... ");
        // 这个地方 latch.await()； 目的是为了查看排队状态
        latch.await();
        System.out.println("main end");
    }
}