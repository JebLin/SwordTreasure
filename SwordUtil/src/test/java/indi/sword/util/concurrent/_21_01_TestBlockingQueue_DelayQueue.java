package indi.sword.util.concurrent;

import lombok.ToString;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 12:28 2018/2/3
 * @Modified By
 */
/*
    DelayQueue阻塞的是其内部元素，DelayQueue中的元素必须实现 java.util.concurrent.Delayed接口
    getDelay()方法的返回值就是队列元素被释放前的保持时间，如果返回0或者一个负值，就意味着该元素已经到期需要被释放，此时DelayedQueue会通过其take()方法释放此对象。
    从 Delayed 接口定义可以看到，它还继承了Comparable接口，这是因为DelayedQueue中的元素需要进行排序，一般情况，我们都是按元素过期时间的优先级进行排序。
 */
public class _21_01_TestBlockingQueue_DelayQueue {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayedElement> queue = new DelayQueue<>();
        DelayedElement ele = new DelayedElement("cache 3 seconds",3000);
        queue.put(ele);
        System.out.println("has put into queue ...");
        System.out.println(queue.take());
    }
}


@ToString
class DelayedElement implements Delayed{
    private long expired;
    private long delay;
    private String name;

    public DelayedElement(String elementName,long delay){
        this.name = elementName;
        this.delay = delay;
        expired = (delay + System.currentTimeMillis());
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedElement cached = (DelayedElement)o;

        return cached.getExpired() > this.getExpired() ? 1: -1;
    }

    @Override
    public long getDelay(TimeUnit unit) {

        return ( this.getExpired() -  System.currentTimeMillis());
    }

    public long getExpired() {
        return expired;
    }
}