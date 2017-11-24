package indi.sword.util.rpc.thrift._02helloworld;

import org.apache.thrift.TProcessor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class HelloServiceServer {
    private final static int DEFAULT_PORT = 7911;
    private static TServer server = null;
    /**
     * 启动 Thrift 服务器
     * @param args
     */
    public static void main(String[] args) {
        try {
            // 设置服务端口为 7911
            TServerSocket serverTransport = new TServerSocket(DEFAULT_PORT);

            // 关联处理器与 Hello 服务的实现
            TProcessor processor = new Hello.Processor(new HelloServiceImpl());

            TThreadPoolServer.Args arg = new TThreadPoolServer.Args(serverTransport);
            arg.processorFactory(new TProcessorFactory(processor));
            arg.transportFactory(new TFramedTransport.Factory());
            arg.protocolFactory(new TBinaryProtocol.Factory()); // 设置协议工厂为 TBinaryProtocol.Factory

            server = new TThreadPoolServer(arg);
            System.out.println("start server ... port : " + DEFAULT_PORT);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
