package indi.sword.util.basic.Thread;

/**
 * @author jeb_lin
 * 12:06 PM 2019/10/2
 */
public class _06_02_TestThreadGroup {
    public static void main(String[] args) {

        // Start two threads
        MyThread mt = new MyThread();
        mt.setName("A");
        mt.start();
        mt = new MyThread();
        mt.setName("B");
        mt.start();

        // Wait 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Interrupt all methods in the same thread group as the main thread
        Thread.currentThread().getThreadGroup().interrupt();
    }


    //一个启动以后进入等待，直到被interrupt的线程
    static class MyThread extends Thread {
        public void run() {
            synchronized ("A") {
                System.out.println(getName() + " about to wait.");
                try {
                    "A".wait();
                } catch (InterruptedException e) {
                    System.out.println(getName() + " interrupted.");
                }
                System.out.println(getName() + " terminating.");
            }
        }
    }
}
