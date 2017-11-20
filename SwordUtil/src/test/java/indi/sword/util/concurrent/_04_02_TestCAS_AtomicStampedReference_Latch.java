package indi.sword.util.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicStampedReference;

public class _04_02_TestCAS_AtomicStampedReference_Latch {

    public static void main(String[] args) {

        final CountDownLatch latch = new CountDownLatch(10);
        AtomicStampedReference<Integer> money = new AtomicStampedReference<Integer>(500,0);

        long startTime = System.currentTimeMillis();

        for(int i = 0; i < 10;i++){
            new Thread(new LatchThread_AtomicStampedReference(latch,money)).start();
        }

        try{
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("it cost time : " + (endTime - startTime) + "millisecond");
    }

}

class LatchThread_AtomicStampedReference implements Runnable{

    private CountDownLatch latch;

    // initialRef 是一个Integer的初始值，initialStamp相当于一个版本号
    private AtomicStampedReference<Integer> money;

    public LatchThread_AtomicStampedReference(CountDownLatch latch,AtomicStampedReference<Integer> money){
        this.latch = latch;
        this.money = money;
    }

    @Override
    public void run() {
        try {
            // 消费失败就继续进去
            while(true){
                int stamp = money.getStamp();
                Integer balance =  money.getReference();

                if(balance - 100 < 0){
                    System.out.println("当前余额 = " + balance + "，小于 100 ，不够消费。");
                    break;
                }else{
                    //是否消费成功
                    // 一次取100块钱出来，直到账号枯竭
                    if(money.compareAndSet(balance,balance - 100,stamp ,stamp + 1)){
                        System.out.println("消费成功，当前余额 = " + balance + ",当前版本号 = " + stamp);
                        break;
                    }
                }
                Thread.sleep(200);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            latch.countDown();  //要减掉1
        }

    }
}
