package indi.sword.util.webService.helloworld.server.iservice;

import javax.jws.WebService;

/**
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/16 10:24
 */
@WebService
public interface HelloworldWsInterface {

    String sayHi(String name);
}
