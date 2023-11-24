package indi.sword.util.basic.Thread;

public class _01_02_JoinTest implements Runnable {

    Thread thread;

    public _01_02_JoinTest(Thread thread) {
        this.thread = thread;
    }

    public void run() {
        synchronized (thread) {
            System.out.println("getObjectLock");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("ReleaseObjectLock");
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new _01_01_JoinTest("Three"));
        Thread getLockThread = new Thread(new _01_02_JoinTest(thread));

        getLockThread.start();
        thread.start();

        try {
            thread.join();
            getLockThread.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Main finished!");
    }

}
