package indi.sword.util.rpc.thrift._03helloworld.NIO;

import indi.sword.util.rpc.thrift._03helloworld.impl.HelloWorldServiceImpl;
import indi.sword.util.rpc.thrift._03helloworld.thrift.HelloWorldService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.transport.TNonblockingServerSocket;

import java.nio.channels.Selector;
import java.util.concurrent.Executors;


public class HelloNonServerDemo {

    static {
        BasicConfigurator.configure();
    }

    /**
     * 日志
     */
    private static Log LOGGER = LogFactory.getLog(HelloNonServerDemo.class);

    public static final int SERVER_PORT = 8090;

    public void startServer() {
        try {
            // log4j日志，如果您工程里面没有加入log4j的支持，请待用system.out
            HelloNonServerDemo.LOGGER.info("HelloWorld HelloWorld TSimpleServer start ....");

            // 服务执行控制器（告诉apache thrift，实现了HelloWorldService.Iface接口的是具体的哪一个类）
            // HelloWorldServiceImpl类的代码，就不在赘述了，无论采用哪种通信模型，它的代码都不会变化
            TProcessor tprocessor = new HelloWorldService.Processor<HelloWorldService.Iface>(new HelloWorldServiceImpl());

            // 非阻塞异步通讯模型（服务器端）
            TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(HelloNonServerDemo.SERVER_PORT);
            // Selector这个类，是不是很熟悉。
            serverTransport.registerSelector(Selector.open());

            THsHaServer.Args tArgs = new THsHaServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            // 指定消息的封装格式（采用二进制流封装）
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            // 指定处理器的所使用的线程池。
            tArgs.executorService(Executors.newFixedThreadPool(100));

            // 启动服务
            THsHaServer server = new THsHaServer(tArgs);
            server.serve();
        } catch (Exception e) {
            HelloNonServerDemo.LOGGER.error(e);
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        HelloNonServerDemo server = new HelloNonServerDemo();
        server.startServer();
    }
}
