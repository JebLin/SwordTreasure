package indi.sword.util.concurrent;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/11/20 12:17
 */
/*
    AtomicReference无法解决上述问题的根本是因为对象在修改过程中，丢失了状态信息。对象值本身与状态被画上了等号。因此，我们只要能够记录对象在修改过程中的状态值，就可以很好的解决对象被反复修改导致线程无法正确判断对象状态的问题。
    AtomicStampedReference正是这么做的。它内部不仅维护了对象值，还维护了一个时间戳（我这里把它称为时间戳，实际上它可以使任何一个整数，它使用整数来表示状态值）。当AtomicStampedReference对应的数值被修改时，除了更新数据本身外，还必须要更新时间戳。当AtomicStampedReference设置对象值时，对象值以及时间戳都必须满足期望值，写入才会成功。因此，即使对象值被反复读写，写回原值，只要时间戳发生变化，就能防止不恰当的写入。
    AtomicStampedReference的几个API在AtomicReference的基础上新增了有关时间戳的信息：

    有了AtomicStampedReference这个法宝，我们就再也不用担心对象被写坏啦！现在，就让我们使用AtomicStampedReference在修正那个贵宾卡充值的问题的：
 */
public class _02_03_TestAtomicStampedReference {

    static AtomicStampedReference<Integer> money=new AtomicStampedReference<Integer>(19,0);

    public static void main(String[] args) {
        //模拟多个线程同时更新后台数据库，为用户充值
        for(int i = 0 ; i < 3 ; i++) {
            final int timestamp=money.getStamp();
            new Thread() {
                public void run() {
                    while(true){
                        while(true){
                            Integer m=money.getReference();
                            if(m<20){
                                if(money.compareAndSet(m,m+20,timestamp,timestamp+1)){
                                    System.out.println("余额小于20元，充值成功，余额:"+money.getReference()+"元");
                                    break;
                                }
                            }else{
                                //System.out.println("余额大于20元，无需充值");
                                break ;
                            }
                        }
                    }
                }
            }.start();
        }

        //用户消费线程，模拟消费行为
        new Thread() {
            public void run() {
                for(int i=0;i<100;i++){
                    while(true){
                        int timestamp=money.getStamp();
                        Integer m=money.getReference();
                        if(m>10){
                            System.out.println("大于10元");
                            if(money.compareAndSet(m, m-10,timestamp,timestamp+1)){
                                System.out.println("成功消费10元，余额:"+money.getReference());
                                break;
                            }
                        }else{
                            System.out.println("没有足够的金额");
                            break;
                        }
                    }
                    try {Thread.sleep(100);} catch (InterruptedException e) {}
                }
            }
        }.start();
    }
}
