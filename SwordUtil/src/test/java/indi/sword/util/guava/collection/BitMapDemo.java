package indi.sword.util.guava.collection;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 10:57 AM 11/07/2018
 * @MODIFIED BY
 */
public class BitMapDemo {
    public static void main(String[] args) {
        BiMap<String,String> biMap = HashBiMap.create();
        biMap.put("sina","sina.com");
        biMap.put("qq","qq.com");
        biMap.put("sina","sina.cn"); //会覆盖原来的value

        System.out.println(biMap);
        /*
         在BiMap中,如果你想把键映射到已经存在的值，会抛出IllegalArgumentException异常
         如果对特定值,你想要强制替换它的键，请使用 BiMap.forcePut(key, value)
        */
//        biMap.put("tencent","qq.com");  //抛出异常

        System.out.println(biMap);
        biMap.forcePut("tencent","qq.com"); //强制替换key
        System.out.println(biMap);

        System.out.println(biMap.inverse().get("qq.com")); //通过value找key
        System.out.println(biMap.inverse().inverse() == biMap);

    }
}
