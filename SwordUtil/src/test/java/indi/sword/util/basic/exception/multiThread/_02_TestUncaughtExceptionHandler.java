package indi.sword.util.basic.exception.multiThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 17:02 2018/2/4
 * @Modified By
 */
/*
    第一步：定义符合异常处理器规范的“异常处理器”
        处理器定义处理方式...
        实现 Thread.UncaughtExceptionHandler 规范。
 */
class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    /*
        Thread.UncaughtExceptionHandle.uncaughtException()
            会在线程因未捕获的异常而临近死亡时被调用
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        // 这里可以写 if else 处理各种各样的异常
        if(e instanceof RuntimeException){
            System.out.println("### MyUncaughtExceptionHandler catch " + e);
        }
    }
}

/*
    定义线程工厂
    线程工厂用来将任务附着给线程，并给该线程绑定一个异常处理器
    工厂就是用来产生线程
 */
class MyHandlerThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + " creating new Thread");
        Thread thread = new Thread(r);
        System.out.println("created " + thread);
        // 设定线程工程的异常处理器
        thread.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        System.out.println("eh = " + thread.getUncaughtExceptionHandler());
        return thread;
    }
}


/*
    第三步，定义一个会抛出unchecked异常的线程类
    我们的任务可能会抛出异常
    显示的抛出一个exception
 */
class ExceptionThreadDemo implements Runnable {

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("run by -> " + t);
        System.out.println("eh -> " + t.getUncaughtExceptionHandler());
        throw new RuntimeException("这是自定义的 RuntimeException ... ");
    }
}

public class _02_TestUncaughtExceptionHandler {

    /**
     * 必须注意一件事情，线程抛出异常之后，不会退出JVM，这点很危险，很容易耗光内存，
     * 接下来的测试类会说道 _03_TestExceptionInThreadCount
     *
     *
     * @param args
     */
    public static void main(String[] args) {
//        test01();
        test02();
    }
    /*
        这是第四步，使用线程工厂创建线程池，并调用其 execute 方法
     */
    public static void test01(){
        ExecutorService exec = Executors.newCachedThreadPool(new MyHandlerThreadFactory());
        exec.execute(new ExceptionThreadDemo());
        System.out.println( "--------"  + ((ThreadPoolExecutor)exec).getActiveCount());
        exec.shutdown();
    }

    /*
        拓展：
            如果你知道将要在代码中处处使用相同的异常处理器，那么更简单的方式是在Thread类中设置一个静态域，
        并将这个处理器设置为默认的未捕获处理器。
            这个处理器只有在不存在线程专有的未捕获异常处理器的情况下才会被调用。
     */
    public static void test02(){
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ExceptionThreadDemo());
        exec.shutdown();
    }
}