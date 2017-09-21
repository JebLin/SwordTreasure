package indi.sword.util.jdk8.lambda;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

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

        System.out.println("-------------------------------------------");

        // 使用 of()
        LocalDate localDate2 = LocalDate.of(2017,9,20);
        LocalTime localTime2 = LocalTime.of(19,31,20,222);
        LocalDateTime localDateTime2 = LocalDateTime.of(2017,9,20,19,31,20,222);
        System.out.println(localDate2);      // 2017-09-20
        System.out.println(localTime2);      // 19:30:21.026
        System.out.println(localDateTime2);  // 2017-09-20T19:30:21.026

        System.out.println("-----------------------------------");


    }

    //5. DateTimeFormatter : 解析和格式化日期或时间
    @Test
    public void test_formatter(){
//		DateTimeFormatter dtf = DateTimeFormatter.ISO_LOCAL_DATE;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss SSS");
        LocalDateTime ldt = LocalDateTime.now();
        String strDate = ldt.format(dtf);

        System.out.println(strDate);

        LocalDateTime newLdt = ldt.parse(strDate, dtf);
        System.out.println(newLdt);
    }

    //6.ZonedDate、ZonedTime、ZonedDateTime ： 带时区的时间或日期
    @Test
    public void test_Zone(){
        LocalDateTime ldt = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(ldt);

        ZonedDateTime zdt = ZonedDateTime.now(ZoneId.of("US/Pacific")); // 结果带时区与偏移量
        System.out.println(zdt);
    }

    // 得到 地区数目
    @Test
    public void test_ZoneIds(){
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);
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

    //4. TemporalAdjuster : 时间校正器
    @Test
    public void test4(){
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ldt);

        LocalDateTime ldt2 = ldt.withDayOfMonth(10);
        System.out.println(ldt2);

        LocalDateTime ldt3 = ldt.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        System.out.println(ldt3);

        //自定义：下一个工作日
        LocalDateTime ldt5 = ldt.with((l) -> {
            LocalDateTime ldt4 = (LocalDateTime) l;

            DayOfWeek dow = ldt4.getDayOfWeek();

            if(dow.equals(DayOfWeek.FRIDAY)){
                return ldt4.plusDays(3);
            }else if(dow.equals(DayOfWeek.SATURDAY)){
                return ldt4.plusDays(2);
            }else{
                return ldt4.plusDays(1);
            }
        });

        System.out.println(ldt5);

    }

}
