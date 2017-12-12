package indi.sword.util.basic.Thread;

public class _02_01_WaitTest {
    public static void main(String[] args) {

        final MyWaitThreadDemo obj = new MyWaitThreadDemo();

        //final Object oo = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj) {
                    System.err.println(1);
                    try {
                        obj.wait(0); // 把obj的锁释放掉
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.err.println(2);
                }
            }
        }).start();

        obj.start();
    }

    static class MyWaitThreadDemo extends Thread {

        public MyWaitThreadDemo() {
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.err.println("MyLockThread");
        }

    }
}
