package indi.sword.util.concurrent;

import lombok.Data;
import lombok.ToString;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 12:59 2018/2/3
 * @Modified By
 */
public class _21_03_TestBlockingQueue_PriorityBlockingQueue {
    public static void main(String[] args) throws InterruptedException {


        PriorityBlockingQueue<PriorityElement> queue = new PriorityBlockingQueue<>();

        for (int i = 0; i < 5; i++) {
            Random random = new Random();
            PriorityElement element = new PriorityElement(random.nextInt(10));
            queue.put(element);
        }
        while (!queue.isEmpty()){
            System.out.println(queue.take());
        }

//        PriorityBlockingQueue<A> queue2 = new PriorityBlockingQueue<>();
//        queue2.put(new A());
    }

    private static class A{}
}


@Data
@ToString
class PriorityElement implements Comparable<PriorityElement>{

    private int priority;// 定义优先级

    public PriorityElement(int priority){
        // 初始化优先级
        this.priority = priority;
    }

    @Override
    public int compareTo(PriorityElement o) {
        //按照优先级大小进行排序
        return this.getPriority() >= o.getPriority() ? -1: 1;
    }
}