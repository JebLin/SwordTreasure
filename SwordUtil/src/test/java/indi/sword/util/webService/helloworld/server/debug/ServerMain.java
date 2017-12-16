package indi.sword.util.webService.helloworld.server.debug;

import indi.sword.util.webService.helloworld.server.imp.HelloWorldWsImpl;
import indi.sword.util.webService.helloworld.server.iservice.HelloworldWsInterface;

import javax.xml.ws.Endpoint;

/**
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/16 9:54
 */
public class ServerMain {
    public static void main(String[] args) {

        HelloworldWsInterface hw = new HelloWorldWsImpl();

        // 调用 EndPoint 的 publish 方法发布 WebService
        Endpoint.publish("http://192.168.106.1/ljb",hw);

        System.out.println("Web Service 暴露成功！");
    }
}
