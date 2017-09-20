package indi.sword.util.jdk8.lambda;

import org.junit.After;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * @Description
 * @Author:rd_jianbin_lin
 * @Date: 14:27 2017/9/20
 */
public class _10_TestSimpleDateFormat {

    ExecutorService pool = Executors.newFixedThreadPool(10);
    List<Future<Date>> results = new ArrayList<>();

    /*
     *  DateFormat 这个会报错
     */
    @Test
    public void test_simple() throws Exception {

        Task_DateFormat task = new Task_DateFormat();

        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }

        for (Future<Date> future : results) {
            System.out.println(future.get());
        }
    }

    /*
     *  使用ThreadLocal<DateFormat>
     */
    @Test
    public void test_DateFormatThreadLocal() throws Exception {

        Task_DateFormatThreadLocal task = new Task_DateFormatThreadLocal();

        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(task));
        }

        for (Future<Date> future : results) {
            System.out.println(future.get());
        }

    }

    @Test
    public void test_LocalDate() throws Exception {
        List<Future<LocalDate>> results_localDate = new ArrayList<>();
        Task_LocalDate task = new Task_LocalDate();

        for(int i = 0;i < 10;i++){
            results_localDate.add(pool.submit(task));
        }

        for(Future<LocalDate> future:results_localDate){
            System.out.println(future.get());
        }
    }


    // 关闭线程池
    @After
    public void closePool() {
        pool.shutdown();
    }
}

/**
 * Date formats are not synchronized.
 * It is recommended to create separate format instances for each thread.
 * If multiple threads access a format concurrently, it must be synchronized
 * externally.
 */
class Task_DateFormat implements Callable<Date> {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public Date call() throws Exception {
        return sdf.parse("20170920");
    }
}

class Task_DateFormatThreadLocal implements Callable<Date> {

    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {

        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
        ;
    };

    @Override
    public Date call() throws Exception {
        return df.get().parse("20170920");
    }
}

class Task_LocalDate implements Callable<LocalDate> {

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public LocalDate call() throws Exception {
        return LocalDate.parse("20170920",dtf);
    }
}