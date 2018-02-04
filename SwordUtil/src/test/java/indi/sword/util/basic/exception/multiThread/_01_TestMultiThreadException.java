package indi.sword.util.basic.exception.multiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 16:04 2018/2/4
 * @Modified By
 */
public class _01_TestMultiThreadException {
    // 现象：控制台打印出异常信息，并运行一段时间后才停止
    public static void main(String[] args) {
        // 就算把线程的执行语句放到 try-catch 块中也无济于事
        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ExceptionThread01());
            exec.shutdown();
        } catch (Exception e) {
            System.out.println("Exception has been handled!");
        }
        /*
            结论：多线程运行不能按照顺序执行过程中捕获异常的方式来处理异常，
            异常会被直接抛出到控制台（由于线程的本质，使得你不能捕获从线程中逃逸的异常。
            一旦异常逃逸出任务的run方法，它就会向外传播到控制台，除非你采用特殊的形式捕获这种异常。）
            这样会让你很头疼，无法捕捉到异常就无法处理异常而引发的问题。
         */

    }
}


class ExceptionThread01 implements Runnable{
    @Override
    public void run() {
        throw new RuntimeException("自定义的一个RuntimeException...");
    }

}
