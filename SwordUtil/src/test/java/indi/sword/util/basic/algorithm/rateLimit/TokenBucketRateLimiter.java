package indi.sword.util.basic.algorithm.rateLimit;


import javassist.bytecode.analysis.Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 令牌桶限流算法
 * 每 1000ms 生成2个令牌
 *
 * @author jeb_lin
 * 14:14 2023/11/19
 */
public class TokenBucketRateLimiter {

    private long interval; // 窗口的时间间隔
    private long rate; // 速率
    private AtomicLong tokenCounter; // 令牌的数量
    private final long bucketCapacity; // 桶的大小

    public TokenBucketRateLimiter(long interval, long rate ,long bucketCapacity) {
        this.interval = interval;
        this.rate = rate;
        this.tokenCounter = new AtomicLong(0);
        this.bucketCapacity = bucketCapacity;
        scheduledProduceToken();
    }

    private void scheduledProduceToken() {
        ExecutorService executorService = Executors.newScheduledThreadPool(1);
        ((ScheduledExecutorService) executorService).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // 桶里面放不下了
                if(tokenCounter.get() + rate > bucketCapacity){
                    System.out.println("bucket is full");
                    return;
                }
                long token = tokenCounter.addAndGet(rate);
                System.out.println("token -> " + token);
            }
        }, 0, interval, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) throws Exception {
        testNormal();
    }

    /**
     * 测试正常的情况
     */
    private static void testNormal() throws Exception{
        // 比如1000ms 允许通过10个请求
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(1000, 2,10);

        // 模拟瞬时流量，5秒前都没请求，5秒后瞬时流量上来了
        Thread.sleep(5000);
        for (int i = 0; i < 12; i++) {
            if (limiter.allow()) {
                System.out.println(i + " -> ok ");
            } else {
                System.out.println(i + " -> no");
            }
        }
    }

    private boolean allow() {
        if(tokenCounter.get() > 0){
            tokenCounter.getAndDecrement();
            return true;
        }

        return false;
    }
}
