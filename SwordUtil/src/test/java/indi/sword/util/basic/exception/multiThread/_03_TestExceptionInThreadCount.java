package indi.sword.util.basic.exception.multiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description 测试一下Exception之后，线程池中的异常线程有没有被剔除掉
 * @Author rd_jianbin_lin
 * @Date 17:52 2018/2/4
 * @Modified By
 */
public class _03_TestExceptionInThreadCount {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            Thread.sleep(200);
            exec.execute(new ExceptionThreadDemo01());
        }
        System.out.println("--------------------");
        Thread.sleep(1000);
        for (int i = 0; i < 5; i++) {
            Thread.sleep(200);
            exec.execute(new NormalThread());
        }
        exec.shutdown();
    }
}

class NormalThread implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(20);
            System.out.println(Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
