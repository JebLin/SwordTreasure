package indi.sword.util.webService._01_helloworld.server.impl;

import indi.sword.util.webService._01_helloworld.server.bean.Cat;
import indi.sword.util.webService._01_helloworld.server.bean.User;
import indi.sword.util.webService._01_helloworld.server.iservice.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    // 用一个 HashMap 来模拟内存中的数据库
    static Map<User,List<Cat>> catDb = new HashMap<>();

    static{
        List<Cat> catList1 = new ArrayList<>();
        User user1 = new User(1,"User1","000000","深圳");
        catList1.add(new Cat(1,"grafield","橙色"));
        catList1.add(new Cat(2,"机器猫","蓝色"));
        catDb.put(user1,catList1);

        List<Cat> catList2 = new ArrayList<>();
        User user2 = new User(2,"User2","123456","北京");
        catList2.add(new Cat(3,"Kitty","咖啡色"));
        catList2.add(new Cat(4,"熊猫","黑白色"));
        catDb.put(user2,catList2);
    }

    @Override
    public List<Cat> getCatsByUser(User user) {
        List<Cat> cats = catDb.get(user);
        cats.forEach(System.out::println);
        return cats;
    }
}
