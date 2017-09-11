package indi.sword.util.base;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @Description
 * 这个实例是为了演示UUID.randomUUID()的生成的数据会不会重复
 * 当然这个列子因为太弱了，还不能检测出发生重复的UUID,但是UUID是由可能发生重复的，只是几率很小很小很小<br/>
 * 关于UUID的介绍：
 * 设计思路：
 * 1. 后台开启20个线程(放在一个线程池里)<br/>
 * 2.开启1000个FutureTask(这是一个异步任务，其结合Callable可以实现从子线程里获得线程执行的结果，弥补了Thead和Runnable拿不到结果的不足)<br/>
 * 3.每个FutureTask里的Callable产生1000个UUID，然后把这1000个UUID放大Set里
 * 程序运行结束如果Set.size()==1000*1000,则没有产生重复的UUID，但依然不能说明UUID.randomUUID()在多线程下不会重复。
 * @Author:rd_jianbin_lin
 * @Date: 19:27 2017/9/11
 */
public class TestUUId_Concurrent {
    public static void main(String[] args) {
        List<FutureTask<Set<String>>> futureTasks = new ArrayList<FutureTask<Set<String>>>();//为了后面统计这些FutureTask的结果准备的
        ExecutorService pool = Executors.newFixedThreadPool(20);//创建一个拥有20个线程的线程池，其大小起码要小于等于futureTasks的吧，否则不就浪费了吗
        for (int i = 0; i < 1000; i++) {
            FutureTask<Set<String>> futureTask = new FutureTask<Set<String>>(new GenUUId());//可以跳到GenUUId的类，看其实现
            futureTasks.add(futureTask);//便于下面的for循环统计结果
            pool.submit(futureTask);//将FutureTask提交到线程池中，有子线程执行
        }

        Set<String> sets = new HashSet<String>();
        for (FutureTask<Set<String>> futureTask : futureTasks) {
            try {
                sets.addAll(futureTask.get());//统计结果
            } catch (Exception e) {
                //TODO
            }
        }
        pool.shutdown();
        for (String str : sets) {
            System.out.println(str);
        }
        System.out.println("多线程计算后的总结果是:" + sets.size());
    }

}


class GenUUId implements Callable { //实现Callable接口

    public String genUuid(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    @Override
    public Object call() throws Exception {//覆盖call()方法，并将结果返回，该结果就是FutureTask的get()获得值
        Set<String> sets = new HashSet<String>();
        for(int i=0;i<1000;i++){
            sets.add(genUuid());
        }
        return sets;
    }
}