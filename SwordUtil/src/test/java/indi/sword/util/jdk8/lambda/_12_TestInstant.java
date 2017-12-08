package indi.sword.util.jdk8.lambda;


import org.junit.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * @Description 测试Instant时间戳
 * 用于“时间戳”的运算。它是以Unix元年(传统的设定为UTC时区1970年1月1日午夜时分)开始所经历的描述进行运算
 *
 *
 * @Author:rd_jianbin_lin
 * @Date: 20:18 2017/9/20
 */
public class _12_TestInstant {

    //3.
    //Duration : 用于计算两个“时间”间隔
    //Period : 用于计算两个“日期”间隔
    @Test
    public void test_Duration(){
        Instant ins1 = Instant.now();

        System.out.println("--------------------");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        Instant ins2 = Instant.now();

        System.out.println("所耗费时间为：" + Duration.between(ins1, ins2).getSeconds());

        System.out.println("----------------------------------");

        LocalDate ld1 = LocalDate.now();
        LocalDate ld2 = LocalDate.of(2011, 1, 1);

        Period pe = Period.between(ld2, ld1);
        System.out.println(pe.getYears());
        System.out.println(pe.getMonths());
        System.out.println(pe.getDays());
    }

    //2. Instant : 时间戳。 （使用 Unix 元年  1970年1月1日 00:00:00 所经历的毫秒值）
    @Test
    public void test_Instant() throws Exception{

        Instant instant  = Instant.now(); //默认使用 UTC 时区
        System.out.println(instant);

        // 计算间隔毫秒数
        System.out.println("--------------------------------------");

        // 解析时间
        Instant instant3 = Instant.parse("2007-12-03T10:15:30.00Z");

        OffsetDateTime offsetDateTime = instant.atOffset(ZoneOffset.ofHours(8)); // +8个小时，转换下时区
        System.out.println(offsetDateTime); // +8个小时，转换下时区

        System.out.println(instant.isAfter(instant3));

        Instant instant4 = instant.truncatedTo(ChronoUnit.DAYS);
        System.out.println(instant4); // 只留下天

        System.out.println("-------------------------------------");
        System.out.println(instant.getNano());

        Instant ins2 = Instant.ofEpochSecond(5); // 加了5秒
        System.out.println(ins2);

    }

}
