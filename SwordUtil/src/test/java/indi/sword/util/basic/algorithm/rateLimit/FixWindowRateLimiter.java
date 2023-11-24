package indi.sword.util.basic.algorithm.rateLimit;


import java.util.concurrent.atomic.AtomicLong;

/**
 * 固定窗口限流算法
 * 比如1000ms 允许通过10个请求
 * @author jeb_lin
 * 14:14 2023/11/19
 */
public class FixWindowRateLimiter {

    private long interval; // 窗口的时间间隔
    private long rate; // 限制的调用次数
    private long lastTimeStamp; // 上次请求来的时间戳
    private AtomicLong counter; // 计数器

    public FixWindowRateLimiter(long interval,long rate){
        this.interval = interval;
        this.rate = rate;
        this.lastTimeStamp = System.currentTimeMillis();
        this.counter = new AtomicLong(0);
    }

    public static void main(String[] args) throws Exception{
//        testNormal();
        testUnNormal();
    }

    /**
     * 测试正常的情况
     */
    private static void testNormal(){
        // 比如1000ms 允许通过10个请求
        FixWindowRateLimiter limiter = new FixWindowRateLimiter(1000,2);

        for (int i = 0; i < 4; i++) {
            if(limiter.allow()){
                System.out.println(i + " -> ok ");
            } else {
                System.out.println(i + " -> no");
            }
        }
    }

    /**
     * 测试不正常的情况
     */
    private static void testUnNormal() throws Exception{
        // 比如1000ms 允许通过10个请求
        FixWindowRateLimiter limiter = new FixWindowRateLimiter(1000,2);

        Thread.sleep(500);
        for (int i = 0; i < 4; i++) {
            if(limiter.allow()){
                System.out.println(i + " -> ok ");
            } else {
                System.out.println(i + " -> no");
            }
            Thread.sleep(250);
        }
    }

    private boolean allow() {
        long now = System.currentTimeMillis();
        if(now - lastTimeStamp > interval){
            counter.set(0L);
            lastTimeStamp = now;
        }

        if(counter.get() >= rate){
            return false;
        } else {
            counter.incrementAndGet();
            return true;
        }
    }
}
