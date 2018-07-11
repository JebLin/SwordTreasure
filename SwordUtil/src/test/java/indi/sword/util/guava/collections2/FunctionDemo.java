package indi.sword.util.guava.collections2;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sun.istack.internal.Nullable;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 11:17 AM 11/07/2018
 * @MODIFIED BY
 */
/*
    transform（）：类型转换
 */
public class FunctionDemo {
    public static void main(String[] args) {
//        transferDate();
        multiFunction();
    }

    public static void transferDate() {
        Set<Long> times = Sets.newHashSet();
        times.add(91299990701L);
        times.add(9320001010L);
        times.add(9920170621L);
        times.add(0L);
//        times.add(null); // IllegalArgumentException
        Collection<String> timeStrCol = Collections2.transform(times, new Function<Long, String>() {
            @Nullable
            @Override
            public String apply(@Nullable Long input) {
                return new SimpleDateFormat("yyyy-MM-dd").format(input);
            }
        });
        System.out.println(timeStrCol); // [1970-01-01, 1970-04-19, 1970-04-26, 1972-11-23]
    }

    public static void multiFunction() {
        List<String> list = Lists.newArrayList("abcde", "good", "happiness");
        //确保容器中的字符串长度不超过5
        Function<String, String> f1 = new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String input) {
                return input.length() > 5 ? input.substring(0, 5) : input;
            }
        };
        //转成大写
        Function<String, String> f2 = new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String input) {
                return input.toUpperCase();
            }
        };
        Function<String, String> function = Functions.compose(f1, f2);
        Collection<String> results = Collections2.transform(list, function);
        System.out.println(results);
    }


}
