package indi.sword.util.concurrent;


/**
 * @Description  测试一下 synchronized 的父类可重入
 * 子类也是可以通过可重入锁调用父类的同步方法。
 * 注意由于synchronized是基于monitor实现的，因此每次重入，monitor中的计数器仍会加1。
 * @Author rd_jianbin_lin
 * @Date 20:12 2018/2/11
 * @Modified By
 */
public class _08_03_TestSynchronized {

    public static void main(String[] args) throws InterruptedException {
        Child child = new Child();
        child.print();
    }
}

class Father{
    public synchronized void doAdd(){
        System.out.println("father do add");
        System.out.println("father unlock do add");
    }
}
class Child extends Father{

    public synchronized void print() {
        System.out.println("do print");
        doAdd();
        System.out.println("unlock do print");
    }
}


