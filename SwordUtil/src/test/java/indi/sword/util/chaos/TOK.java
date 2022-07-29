package indi.sword.util.chaos;

import com.google.common.collect.Sets;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * @author jeb_lin
 * 2:40 PM 2020/2/20
 */
public class TOK {
    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis());
        Long timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        LocalDateTime time2 = LocalDateTime.ofEpochSecond(System.currentTimeMillis() / 1000, 0, ZoneOffset.ofHours(8));
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        /*
        2020/01/01 18:00:00
         */
        System.out.println(time2.format(format));

        String content = String.format("本次共选择:%d 个门店，总下架商品数为: %d \n 操作开始时间：%s \n 操作结束时间：%s",
                1, 2, time2.format(format), time2.format(format));

        System.out.println(content);

    }
}
