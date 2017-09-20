package indi.sword.util.jdk8.lambda;


import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * @Description 测试Instant时间戳
 * 用于“时间戳”的运算。它是以Unix元年(传统的设定为UTC时区1970年1月1日午夜时分)开始所经历的描述进行运算
 *
 *
 * @Author:rd_jianbin_lin
 * @Date: 20:18 2017/9/20
 */
public class _012_TestInstant {

    @Test
    public void test_Duration(){
        Duration duration = Duration.ZERO;

    }

    @Test
    public void test_Instant() throws Exception{

        Instant instant  = Instant.now();
        System.out.println(instant);
        Thread.sleep(1000);
        Instant instant2  = Instant.now();

        // 计算间隔毫秒数
        System.out.println(Duration.between(instant,instant2).toMillis());
        System.out.println("--------------------------------------");

        // 解析时间
        Instant instant3 = Instant.parse("2007-12-03T10:15:30.00Z");

        // 转换时区  ZoneId里面没有Shanghai/China或者HongKong 歧视？
        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8)); // +8个小时，转换下时区
        System.out.println(offsetDateTime); // +8个小时，转换下时区

        System.out.println(instant.isAfter(instant3));

        Instant instant4 = instant.truncatedTo(ChronoUnit.DAYS);
        System.out.println(instant4); // 只留下天

    }

}
