package indi.sword.util.basic.algorithm.rateLimit;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 漏桶算法
 * 比如 1秒只能消费2个请求
 *
 * @author jeb_lin
 * 15:55 2023/11/19
 */
public class LeakyBucketRateLimiter {

    private int consumerRate; // 消费速度
    private Long interval; // 时间间隔，比如1000ms
    private int bucketCapacity; // 桶的容量
    private AtomicLong water; // 桶里面水滴数量

    public LeakyBucketRateLimiter(int consumerRate, Long interval, int bucketCapacity) {
        this.consumerRate = consumerRate;
        this.interval = interval;
        this.bucketCapacity = bucketCapacity;
        this.water = new AtomicLong(0);
        scheduledTask();
    }

    // 周期任务，比如每1000ms消费2个请求
    private void scheduledTask() {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate((Runnable) () -> {
            for (int i = 0; i < consumerRate && water.get() > 0; i++) {
                this.water.decrementAndGet();
            }
            System.out.println("water -> " + this.water.get());
        }, 0, interval, TimeUnit.MILLISECONDS);
    }

    public static void main(String[] args) {
        // 1000毫秒消费2个请求
        LeakyBucketRateLimiter limiter = new LeakyBucketRateLimiter(2, 1000L, 10);

        for (int i = 0; i < 10; i++) {
            if (limiter.allow()) {
                System.out.println(i + "-> ok");
            } else {
                System.out.println(i + "-> no");
            }
        }

    }

    private boolean allow() {
        if (bucketCapacity < water.get() + 1) {
            return false;
        } else {
            water.incrementAndGet();
            return true;
        }
    }
}
