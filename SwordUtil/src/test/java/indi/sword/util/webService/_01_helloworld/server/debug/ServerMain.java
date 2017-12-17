package indi.sword.util.webService._01_helloworld.server.debug;


import indi.sword.util.webService._01_helloworld.server.impl.HelloworldWsImpl;
import indi.sword.util.webService._01_helloworld.server.interceptor.MyServerAuthInterceptor;
import indi.sword.util.webService._01_helloworld.server.iservice.HelloworldWsInterface;
import org.apache.cxf.jaxws.EndpointImpl;

import javax.xml.ws.Endpoint;

/**
 * 重要！！
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/16 9:54
 */
public class ServerMain {
    public static void main(String[] args) {

        /*
            1、基础
                暴露端口
         */
        HelloworldWsInterface hw = new HelloworldWsImpl();
        // 调用 EndPoint 的 publish 方法发布 WebService
        //去浏览器输入：http://192.168.106.1:5202/ljb?wsdl
        EndpointImpl ep = (EndpointImpl) Endpoint.publish("http://192.168.106.1:5202/ljb",hw);

        System.out.println("------------------------------");
        /*
            2、进阶
                a.添加拦截器（cxf自带日志拦截器）
         */
//        ep.getInInterceptors().add(new LoggingInInterceptor(new PrintWriter(System.out))); // 服务器端 In 拦截器，输出到控制台
//        ep.getOutInterceptors().add(new LoggingOutInterceptor(new PrintWriter(System.out))); // 服务器端 Out 拦截器，输出到控制台

        /*
                b.添加自定义拦截器
         */
        ep.getInInterceptors().add(new MyServerAuthInterceptor()); // 服务器端 In 拦截器，输出到控制台
        ep.getOutInterceptors().add(new MyServerAuthInterceptor()); // 服务器端 Out 拦截器，输出到控制台

        System.out.println("Web Service 暴露成功！");
    }
}
