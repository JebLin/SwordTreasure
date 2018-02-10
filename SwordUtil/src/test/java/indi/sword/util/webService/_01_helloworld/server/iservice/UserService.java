package indi.sword.util.webService._01_helloworld.server.iservice;

import indi.sword.util.webService._01_helloworld.server.bean.Cat;
import indi.sword.util.webService._01_helloworld.server.bean.User;

import java.util.List;

/**
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/16 15:21
 */
public interface UserService {

    List<Cat> getCatsByUser(User user);
}
