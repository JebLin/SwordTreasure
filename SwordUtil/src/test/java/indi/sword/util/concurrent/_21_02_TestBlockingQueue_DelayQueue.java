package indi.sword.util.concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 12:46 2018/2/3
 * @Modified By
 */
public class _21_02_TestBlockingQueue_DelayQueue {
    static final int STUDENT_SIZE = 30;

    public static void main(String[] args) throws InterruptedException {
        Random r = new Random();

        // 所有学生看做一个延迟队列
        DelayQueue<Student> students = new DelayQueue<>();

        // 构造一个线程池用来让学生们“做作业”
        ExecutorService executorService = Executors.newFixedThreadPool(STUDENT_SIZE);
        for (int i = 0; i < STUDENT_SIZE; i++) {
            students.put(new Student("学生" + (i+1),3000 + r.nextInt(10000)));
        }
        // 开始做题
        while(! students.isEmpty()){
            executorService.execute(students.take());
        }

        executorService.shutdown();


    }

}


class Student implements Runnable,Delayed{

    private String name; // 学生姓名
    private long costTime;// 该学生做题的时间,假设根据能力，我们事先推算出来的
    private long finishedTime; // 完成时间 costTime + System.currentTimeMills()

    public Student(String name,long costTime){
        this.name = name;
        this.costTime = costTime;
        finishedTime = costTime + System.currentTimeMillis();
    }

    @Override
    public void run() {
        System.out.println(name + "交卷，用时： " + costTime / 1000 + "s");
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return (finishedTime - System.currentTimeMillis());
    }

    @Override
    public int compareTo(Delayed o) {
        Student other = (Student)o;
        return costTime >= other.costTime ? 1: -1;
    }


}