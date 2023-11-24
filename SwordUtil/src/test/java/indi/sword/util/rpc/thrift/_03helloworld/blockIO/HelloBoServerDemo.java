package indi.sword.util.rpc.thrift._03helloworld.blockIO;


import java.util.concurrent.Executors;

import indi.sword.util.rpc.thrift._03helloworld.thrift.HelloWorldService;
import indi.sword.util.rpc.thrift._03helloworld.impl.HelloWorldServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;


public class HelloBoServerDemo {

    static {
        BasicConfigurator.configure();
    }

    /**
     * 日志
     */
    private static final Log LOGGER =LogFactory.getLog(HelloBoServerDemo.class);

    public static final int SERVER_PORT = 9111;

    public void startServer() {
        try {
            HelloBoServerDemo.LOGGER.info("看到这句就说明thrift服务端准备工作 ....");

            // 服务执行控制器（只要是调度服务的具体实现该如何运行）
            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());

            // 基于阻塞式同步IO模型的Thrift服务，正式生产环境不建议用这个
            TServerSocket serverTransport = new TServerSocket(HelloBoServerDemo.SERVER_PORT);

            // 为这个服务器设置对应的IO网络模型、设置使用的消息格式封装、设置线程池参数
            Args tArgs = new Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            tArgs.executorService(Executors.newFixedThreadPool(100));

            // 启动这个thrift服务
            TThreadPoolServer server = new TThreadPoolServer(tArgs);
            server.serve();
        } catch (Exception e) {
            HelloBoServerDemo.LOGGER.error(e);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        HelloBoServerDemo server = new HelloBoServerDemo();
        server.startServer();
    }
}

