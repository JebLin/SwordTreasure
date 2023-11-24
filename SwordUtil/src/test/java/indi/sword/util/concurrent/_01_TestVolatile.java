package indi.sword.util.concurrent;

/*
 * @Descrption
 *
 * 场景： main 线程中一直拿的是主存中的一开始的 false
 * 分线程 改成true后 main线程一直没拿到，所以没退出
 * 一、volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。
 *                   相较于 synchronized 是一种较为轻量级的同步策略。
 *
 *
 * 注意：
 * 1. volatile 不具备“互斥性”
 * 2. volatile 不能保证变量的“原子性”
 *
 * 一般情况下，对象是操作地址，值的话是操作 主存中值的副本，操作完成后再丢回去主存。
 * volatile是让线程每次去直接操作主存中的数据。
 * 效率是会降低，但是比 synchronized效率高
 *
 * 两种办法解决下列问题 ： 1、while里面加延迟
 * 				  2、加volatile
 *
 *
 *
 * @author rd_jianbin_lin
 * @Date Sep 2, 2017 7:20:01 PM
 */
public class _01_TestVolatile {


    public static void main(String[] args) throws Exception {
        MyThread_Volatile mt = new MyThread_Volatile();
//
        new Thread(mt).start();
        int i = 0;
        // while(true) 调用的是 计算机相对底层的代码，执行效率非常之高，高到main线程没机会 去主存获取新的数据
        // 但是如果加一段延迟的话，那就可以得到


        while (true) {
            Thread.sleep(100); //假如没有这个延迟，那么while会一直不去读主存中的值

//            System.out.println(mt.isFlag() + " -- " + i++);
            //加了 volatile之后，才会去直接取 主存中的值
            if (mt.isFlag()) {
                System.out.println("------------------");
                break;
            }
        }


    }
}

class MyThread_Volatile implements Runnable {


    	private boolean flag = false;
//    private volatile boolean flag = false;

    @Override
    public void run() {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;

        System.out.println("flag=" + isFlag());

    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        System.out.println("========== setFlag ====== " + flag);
        this.flag = flag;
    }

}