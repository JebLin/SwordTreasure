package indi.sword.util.base.Thread;

/**
 * Synchronized 方法出现死锁的例子
 * @Description:
 * @Author: rd_jianbin_lin
 * @Date:20:36 2017/12/4
 */
public class SynchronizedDeadLock {
    public static void main(String[] args) {
        final PersonDemo person1 = new PersonDemo();
        final PersonDemo person2 = new PersonDemo();

        new Thread(()->{
            synchronized (person1){
                System.out.println(Thread.currentThread().getName() + " lock person1");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " --------------");
                synchronized (person2){
                    try {
                        System.out.println(Thread.currentThread().getName() + " lock person2");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " unlock person2");
                }
                System.out.println(Thread.currentThread().getName() + " unlock person1");
            }
        },"thread1").start();
        new Thread(()->{
            synchronized (person2){
                System.out.println(Thread.currentThread().getName() + " lock person2");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " --------------");
                synchronized (person1){
                    try {
                        System.out.println(Thread.currentThread().getName() + " lock person1");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " unlock person1");
                }
                System.out.println(Thread.currentThread().getName() + " unlock person2");
            }
        },"thread2").start();



    }
}

class PersonDemo{ }

