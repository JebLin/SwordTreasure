package indi.sword.util.basic.algorithm.rateLimit;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 滑动窗口限流算法
 * 比如1000ms 允许通过 2 个请求
 *
 * @author jeb_lin
 * 14:14 2023/11/19
 */
public class SlidingWindowRateLimiter {

    private long interval; // 窗口的时间间隔
    private long maxRequest; // 限制的调用次数
    private Map<Long, AtomicLong> millToCount = null; // 假如1秒切成1000个窗口，也就是1毫秒一个计数器
    private LinkedList<Long> timeStampList = null; // 出现请求的那个时间戳，需要记录下来，用于后期的删除
    private AtomicLong counter; // 计数器

    public SlidingWindowRateLimiter(long interval, long maxRequest) {
        this.interval = interval;
        this.maxRequest = maxRequest;
        this.millToCount = new HashMap<>();
        this.timeStampList = new LinkedList<>();
        this.counter = new AtomicLong(0);
    }

    public static void main(String[] args) throws Exception {
        testNormal();
    }


    /**
     * 测试正常的情况
     */
    private static void testNormal() {
        // 比如1000ms 允许通过10个请求
        SlidingWindowRateLimiter limiter = new SlidingWindowRateLimiter(1000, 2);

        for (int i = 0; i < 10; i++) {
            if (limiter.allow()) {
                System.out.println(i + " -> ok ");
            } else {
                System.out.println(i + " -> no");
            }
        }
    }

    private boolean allow() {
        long now = System.currentTimeMillis();

        // 剔除掉过期的窗口，比如现在是1001ms，那么1ms的窗口就需要剔除掉
        while (!timeStampList.isEmpty() && now - interval > timeStampList.getFirst()) {
            long timeStamp = timeStampList.poll();
            for (int i = 0; i < millToCount.getOrDefault(timeStamp,new AtomicLong(0)).get(); i++) {
                counter.decrementAndGet();
            }
            millToCount.remove(timeStamp);
        }

        if (counter.get() >= maxRequest) {
            return false;
        } else {
            timeStampList.add(now); // 当前出现成功请求，那么串上list

            AtomicLong timeStampCounter = millToCount.getOrDefault(now, new AtomicLong(0L));
            timeStampCounter.incrementAndGet();
            millToCount.put(now, timeStampCounter);
            counter.incrementAndGet();
            return true;
        }
    }
}
