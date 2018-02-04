package indi.sword.util.concurrent;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 9:23 2018/2/4
 * @Modified By
 */
/*
    Java 6的并发编程包中的SynchronousQueue是一个没有数据缓冲的BlockingQueue(队列只能存储一个元素)，
    生产者线程对其的插入操作put必须等待消费者的移除操作take，反过来也一样，消费者移除数据操作必须等待生产者的插入。

    不像ArrayBlockingQueue或LinkedListBlockingQueue，SynchronousQueue内部并没有数据缓存空间，
    你不能调用peek()方法来看队列中是否有数据元素，因为数据元素只有当你试着取走的时候才可能存在，
    不取走而只想偷窥一下是不行的，当然遍历这个队列的操作也是不允许的。队列头元素是第一个排队要插入数据的线程，
    而不是要交换的数据。数据是在配对的生产者和消费者线程之间直接传递的，并不会将数据缓冲数据到队列中。
    可以这样来理解：生产者和消费者互相等待对方，握手，然后一起离开。

    SynchronousQueue的一个使用场景是在线程池里。Executors.newCachedThreadPool()就使用了SynchronousQueue，
    这个线程池根据需要（新任务到来时）创建新的线程，如果有空闲线程则会重复使用，线程空闲了60秒后会被回收。

    add     take   offer
    remove  put    poll

 */
public class _21_04_TestBlockingQueue_SynchronousQueue {
    public static void main(String[] args) {
        // 如果为 true，则等待线程以 FIFO 的顺序竞争访问；否则顺序是未指定的。
        // SynchronousQueue<Integer> sc =new SynchronousQueue<>(true);//fair
        SynchronousQueue<Integer> sc = new SynchronousQueue<>(); // 默认不指定的话是false，不公平的
        new Thread(() ->{ // 生产者线程
            while (true){
                try {
                    //将指定元素添加到此队列，如有必要则等待另一个线程接收它。
//                    sc.put(new Random().nextInt(50));
                    // 如果另一个线程正在等待以便接收指定元素，则将指定元素插入到此队列。如果没有等待接受数据的线程则直接返回false
//                     System.out.println("sc.offer(new Random().nextInt(50)): "+sc.offer(new Random().nextInt(50)));
                    //如果没有等待的线程，则等待指定的时间。在等待时间还没有接受数据的线程的话，直接返回false
                    System.out.println("sc.offer(new Random().nextInt(50),5,TimeUnit.SECONDS) :" + sc.offer(new Random().nextInt(50),5,TimeUnit.SECONDS));



                    System.out.println("添加操作运行完毕..." ); //是操作完毕，并不是添加或获取元素成功!
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }).start();
        new Thread(() ->{ //消费者线程。
            while (true){
                try {
                    System.out.println("----------------> sc.take: " + sc.take());
                    System.out.println("----------------> 获取操作运行完毕..."); // 是操作完毕，并不是添加或者获取元素成功！
                    Thread.sleep(1000);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    @Test
    public void firstDemo(){
        SynchronousQueue<Integer> sc = new SynchronousQueue<>(); // 默认不指定的话是false，不公平锁
        sc.offer(2); // 没有线程等待获取元素的话，不阻塞在此处，如果该元素已添加到此队列，则返回 true；否则返回 false
        try {
            sc.offer(2,5, TimeUnit.SECONDS);// 没有线程等待获取元素的话，阻塞在此处等待指定时间，如果该元素已添加到此队列，则返回true；否则返回 false
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
