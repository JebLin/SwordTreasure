package indi.sword.util.concurrent;


import sun.misc.Lock;


/**
 * 可重入锁，也叫做递归锁，指的是同一线程 外层函数获得锁之后 ，内层递归函数仍然有获取该锁的代码，但不受影响。
    在JAVA环境下 ReentrantLock 和synchronized 都是 可重入锁

    可重入锁：意思是，外层方法已经获取锁了，里层方法如果需要锁的话，
    可以直接用了，不然一个等着另一个会死锁，像下面那个print与add
 */
public class _08_00_TestLock {
//    Lock lock = new ReentrantLock();
    Lock lock = new Lock();
    public void print() throws InterruptedException {
        System.out.println("do print");
        lock.lock();
        doAdd();
        System.out.println("unlock do print");
        lock.unlock();
    }
    public void doAdd() throws InterruptedException {
        System.out.println("do add");
        lock.lock();
        System.out.println("unlock do add");
        //do something
        lock.unlock();
    }
    public static void main(String[] args) throws InterruptedException {
        _08_00_TestLock testLock = new _08_00_TestLock();
        testLock.print();
    }
}
