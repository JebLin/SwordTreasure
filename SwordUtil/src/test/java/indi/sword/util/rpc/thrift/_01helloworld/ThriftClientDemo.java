package indi.sword.util.rpc.thrift._01helloworld;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftClientDemo {
    private final static int DEFAULT_QRY_CODE = 0;
    private final static int DEFAULT_PORT = 30001;
    public static void main(String[] args) {
        try {
            TTransport tTransport = getTTransport();

            // 注意使用的消息封装格式，一定要和服务器端使用的一致
            TProtocol protocol = new TBinaryProtocol(tTransport);
            // 这是客户端对非阻塞异步网络通信方式的支持。
            TestQry.Client client = new TestQry.Client(protocol);

            // 准备调用参数(这个QryResult，是我们通过IDL定义，并且生成的)
            QryResult result = client.qryTest(DEFAULT_QRY_CODE);
            System.out.println("code=" + result.code + " msg=" + result.msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static TTransport getTTransport() throws Exception{
        try{
            TTransport tTransport = getTTransport("127.0.0.1", DEFAULT_PORT, 5000);
            if(!tTransport.isOpen()){
                tTransport.open();
            }
            return tTransport;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    private static TTransport getTTransport(String host, int port, int timeout) {
        final TSocket tSocket = new TSocket(host, port, timeout);
        final TTransport transport = new TFramedTransport(tSocket);
        return transport;
    }
}