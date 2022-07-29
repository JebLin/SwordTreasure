package indi.sword.util.chaos;

/**
 * @author jeb_lin
 * 10:48 PM 2020/2/18
 */
public class KK {
    public static void main(String[] args) {

        TT aTT = new TT();
        TT bTT = new TT();
        // 3.  3个线程共享wallet，各自消费
        new Thread(new TTreadDemo(aTT), "A1").start();
        new Thread(new TTreadDemo(aTT), "A2").start();
        new Thread(new TTreadDemo(aTT), "A3").start();
        new Thread(new TTreadDemo(bTT), "B").start();
        new Thread(new TTreadDemo(aTT), "C").start();
        new Thread(new TTreadDemo(bTT), "D").start();
    }
}
