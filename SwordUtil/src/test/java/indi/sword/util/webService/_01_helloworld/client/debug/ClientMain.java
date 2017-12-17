package indi.sword.util.webService._01_helloworld.client.debug;


import indi.sword.util.webService._01_helloworld.client.wsdl2java.*;

import java.util.List;

/**
 * @Decription 测试类，除了这个类，client的其他类都是 wsdl2java 命令生成的
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/16 13:58
 */
public class ClientMain {
    public static void main(String[] args) {

        HelloworldWsImpl factory = new HelloworldWsImpl();

        HelloworldWsInterface hw = factory.getHelloworldWsImplPort();
        System.out.println(hw.sayHi("许文强"));
        System.out.println("-------------------------------------");

        User user2 = new User();
        user2.setId(2);
        user2.setName("User2");
        user2.setPassword("123456");

        List<Cat> cats = hw.getCatsByUser(user2);
        System.out.println("cats.size() = " + cats.size());

        for (int i = 0; i < cats.size(); i++) {
            System.out.println(cats.get(i));
        }

        System.out.println("------------------------");
        StringCat stringCat = hw.getAllCats();
        System.out.println(stringCat == null);
        stringCat.getEntrySet().forEach(entry -> {
            System.out.println(entry.getKey() + "-" + entry.getValue());
        });


    }
}
