package indi.sword.util.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/11/20 12:15
 */

/*
    AtomicReference和AtomicInteger非常类似，不同之处就在于AtomicInteger是对整数的封装，而AtomicReference则对应普通的对象引用。
    也就是它可以保证你在修改对象引用时的线程安全性。在介绍AtomicReference的同时，我希望同时提出一个有关原子操作的逻辑上的不足。
   之前我们说过，线程判断被修改对象是否可以正确写入的条件是对象的当前值和期望是否一致。这个逻辑从一般意义上来说是正确的。
   但有可能出现一个小小的例外，就是当你获得对象当前数据后，在准备修改为新值前，对象的值被其他线程连续修改了2次，
   而经过这2次修改后，对象的值又恢复为旧值。这样，当前线程就无法正确判断这个对象究竟是否被修改过。如图4.2所示，显示了这种情况。
 */
/*
     一般来说，发生这种情况的概率很小。而且即使发生了，可能也不是什么大问题。比如，我们只是简单得要做一个数值加法，
     即使在我取得期望值后，这个数字被不断的修改，只要它最终改回了我的期望值，我的加法计算就不会出错。
     也就是说，当你修改的对象没有过程的状态信息，所有的信息都只保存于对象的数值本身。
    但是，在现实中，还可能存在另外一种场景。就是我们是否能修改对象的值，不仅取决于当前值，还和对象的过程变化有关，这时，AtomicReference就无能为力了。
    打一个比方，如果有一家蛋糕店，为了挽留客户，绝对为贵宾卡里余额小于20元的客户一次性赠送20元，刺激消费者充值和消费。但条件是，每一位客户只能被赠送一次。
    现在，我们就来模拟这个场景，为了演示AtomicReference，我在这里使用AtomicReference实现这个功能。首先，我们模拟用户账户余额。
 */
/*
    从这一段输出中，可以看到，这个账户被先后反复多次充值。其原因正是因为账户余额被反复修改，修改后的值等于原有的数值。使得CAS操作无法正确判断当前数据状态。
   虽然说这种情况出现的概率不大，但是依然是有可能的出现的。因此，当业务上确实可能出现这种情况时，我们也必须多加防范。
   体贴的JDK也已经为我们考虑到了这种情况，使用AtomicStampedReference就可以很好的解决这个问题。
 */
public class _02_02_TestAtomicReference {
    // 设置账户初始值小于20，显然这是一个需要被充值的账户
    static AtomicReference<Integer> money= new AtomicReference<Integer>(19);

    public static void main(String[] args) {
        //模拟多个线程同时更新后台数据库，为用户充值
        for(int i = 0 ; i < 3 ; i++) {
            new Thread(){
                public void run() {
                    while(true){
                        while(true){
                            Integer m=money.get();
                            if(m<20){
                                if(money.compareAndSet(m, m+20)){
                                    System.out.println("余额小于20元，充值成功，余额:"+money.get()+"元");
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
            public void run(){
                for(int i=0;i<100;i++){
                    while(true){
                        Integer m=money.get();
                        if(m>10){
                            System.out.println("大于10元");
                            if(money.compareAndSet(m, m-10)){
                                System.out.println("成功消费10元，余额:"+money.get());
                                break;
                            }
                        }else{
                            System.out.println("没有足够的金额");
                            break;
                        }
                    }
                    try{Thread.sleep(100);} catch (InterruptedException e) {}
                }
            }
        }.start();
    }


}
