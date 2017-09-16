package indi.sword.util.base;

import org.junit.Test;

import java.util.Random;
import java.util.stream.Stream;

/**
 * @Description 测试产生随机数据
 * @Author:rd_jianbin_lin
 * @Date: 10:53 2017/9/16
 */
public class TestRandomGenerator {

    @Test
    public void test01(){
        Random random = new Random();

//        Stream<Double> stream1 = Stream.generate(random::nextDouble).limit(3);
//        stream1.forEach(System.out::println);


        for (int i = 0;i < 100;i++){
            System.out.println((int)(random.nextDouble() * 10000));
        }

    }

}
