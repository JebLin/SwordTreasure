package indi.sword.util.basic.Thread;

public class _02_01_WaitTest {
    public static void main(String[] args) {

        final MyWaitThreadDemo obj = new MyWaitThreadDemo();

        //final Object oo = new Object();
        new Thread(() -> {
            synchronized (obj) {
                System.out.println(1);
                System.out.println(Thread.currentThread().getName() + "," + System.currentTimeMillis());
                try {
                    Thread.sleep(10000);
                    System.out.println(Thread.currentThread().getName() + " begin to wait ." );
                    obj.wait(0); // 把obj的锁释放掉
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(2);
            }
        },"new").start();

        obj.start();
    }

    static class MyWaitThreadDemo extends Thread {

        public MyWaitThreadDemo() {
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread().getName() + "," + System.currentTimeMillis());
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("MyLockThread");
        }

    }
}
