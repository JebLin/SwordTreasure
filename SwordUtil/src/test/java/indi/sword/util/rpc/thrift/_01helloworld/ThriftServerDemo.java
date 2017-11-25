package indi.sword.util.rpc.thrift._01helloworld;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.nio.channels.Selector;

public class ThriftServerDemo {
    private final static int DEFAULT_PORT = 30001;
    private static TServer server = null;

    public static void main(String[] args) throws IOException {
        try {
            // 服务执行控制器（告诉apache thrift，实现了TestQry.Iface接口的是具体的哪一个类）
            // HelloWorldServiceImpl类的代码，就不在赘述了，无论采用哪种通信模型，它的代码都不会变化
            TestQry.Processor processor = new TestQry.Processor(new QueryImp());

            // 非阻塞异步通讯模型（服务器端）
            TNonblockingServerSocket socket = new TNonblockingServerSocket(DEFAULT_PORT);

            // Selector这个类，是不是很熟悉。
            socket.registerSelector(Selector.open());

            TNonblockingServer.Args arg = new TNonblockingServer.Args(socket);
            // 指定消息的封装格式（采用二进制流封装）
            arg.protocolFactory(new TBinaryProtocol.Factory());
            arg.transportFactory(new TFramedTransport.Factory());
            arg.processorFactory(new TProcessorFactory(processor));

            server = new TNonblockingServer(arg);
            System.out.println("start server ... port : " + DEFAULT_PORT);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
