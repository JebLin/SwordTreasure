package indi.sword.util.webService.helloworld.server.imp;

import indi.sword.util.webService.helloworld.server.iservice.HelloworldWsInterface;

import javax.jws.WebService;
import java.util.Date;

/**
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/16 10:29
 */
@WebService(endpointInterface = "indi.sword.util.webService.helloworld.server.iservice.HelloworldWsInterface",
        serviceName = "HelloWorldWsImpl")
public class HelloWorldWsImpl implements HelloworldWsInterface {
    @Override
    public String sayHi(String name) {

        return  name + ",您好，现在时间是：" + new Date() ;
    }
}
