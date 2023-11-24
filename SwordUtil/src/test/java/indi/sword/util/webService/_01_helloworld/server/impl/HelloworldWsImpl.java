package indi.sword.util.webService._01_helloworld.server.impl;

import indi.sword.util.webService._01_helloworld.server.bean.Cat;
import indi.sword.util.webService._01_helloworld.server.bean.User;
import indi.sword.util.webService._01_helloworld.server.iservice.HelloworldWsInterface;
import indi.sword.util.webService._01_helloworld.server.iservice.UserService;

import javax.jws.WebService;
import java.util.*;


/**
 * 重要！！
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/16 10:29
 */
@WebService(endpointInterface = "indi.sword.util.webService._01_helloworld.server.iservice.HelloworldWsInterface",
        serviceName = "HelloworldWsImpl")
public class HelloworldWsImpl implements HelloworldWsInterface {
    @Override
    public String sayHi(String name) {
        return name + ",您好，现在时间是：" + new Date();
    }

    @Override
    public List<Cat> getCatsByUser(User user) {

        // 在实际的项目中，Web Service组件自己不会去实现业务功能
        // 它只是调用业务逻辑组件的方法来暴露Web ServiceS
        UserService service = new UserServiceImpl();
        return service.getCatsByUser(user);
    }

    @Override
    public Map<String, Cat> getAllCats() {
        Map<String,Cat> stringCatMap = new HashMap<>();

        Collection<List<Cat>> coll = UserServiceImpl.catDb.values();
        coll.forEach(lists ->{
            lists.forEach(cat -> {
                stringCatMap.put(cat.getName(),cat);
            });
        });
        return stringCatMap;
    }
}

