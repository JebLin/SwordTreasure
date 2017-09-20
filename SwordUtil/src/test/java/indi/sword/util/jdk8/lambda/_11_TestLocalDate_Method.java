package indi.sword.util.jdk8.lambda;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * jdk1.8
 * 的 LocalDate、LocalTime、LocalDateTime类的实例是不可变对象，
 * 分别表示使用ISO-8601日历系统的日期、时间、日期、时间。
 *
 * @Description 测试一些常用的jdk1.8类 LocalDate的方法
 * @Author:rd_jianbin_lin
 * @Date: 19:27 2017/9/20
 */
public class _11_TestLocalDate_Method {

    // 初始化日期
    @Test
    public void test_format() throws Exception{

        //使用 now()
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        LocalDateTime localDateTime = LocalDateTime.now();

        System.out.println(localDate);      // 2017-09-20
        System.out.println(localTime);      // 19:30:21.026
        System.out.println(localDateTime);  // 2017-09-20T19:30:21.026

        // 使用 of()
        LocalDate localDate2 = LocalDate.of(2017,9,20);
        LocalTime localTime2 = LocalTime.of(19,31,20,222);
        LocalDateTime localDateTime2 = LocalDateTime.of(2017,9,20,19,31,20,222);
        System.out.println(localDate2);      // 2017-09-20
        System.out.println(localTime2);      // 19:30:21.026
        System.out.println(localDateTime2);  // 2017-09-20T19:30:21.026

    }

    // 操作日期
    @Test
    public void test_operate() throws Exception{
        LocalDateTime localDateTime = LocalDateTime.of(2017,9,20,20,20,20,200);
        System.out.println(localDateTime);

        // 向当前LocalDate对象添加几天、几周、几个月、几年 相对于 minus
        localDateTime = localDateTime.plusYears(2).plusMonths(2).plusDays(2).plusHours(2).plusMinutes(2).plusSeconds(2).plusNanos(22);
        System.out.println(localDateTime);

        localDateTime = localDateTime.withMonth(2); //将月份天数、年份天数、月份、年份修改为指定的值并返回新的LocalDate对象
        System.out.println(localDateTime);

        System.out.println(localDateTime.getDayOfYear());
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println("--------------------------------------");


        LocalDateTime localDateTime2 = LocalDateTime.of(2017,9,20,20,20,20,200);
        long duration = localDateTime2.until(localDateTime, ChronoUnit.DAYS);  // 得出两个日期的间隔
        System.out.println(duration);
        System.out.println("--------------------------------------");


        // 判断日期谁先谁后
        System.out.println(localDateTime2.isAfter(localDateTime));
        System.out.println(localDateTime2.isEqual(localDateTime));
        System.out.println(localDateTime2.isBefore(localDateTime));

        System.out.println("--------------------------------------");


        // 判断是否是闰年
        System.out.println(localDateTime.toLocalDate().isLeapYear());
        System.out.println(localDateTime2.toLocalDate().isLeapYear());

    }

}
