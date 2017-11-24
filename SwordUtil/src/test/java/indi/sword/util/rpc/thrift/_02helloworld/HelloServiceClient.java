package indi.sword.util.rpc.thrift._02helloworld;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class HelloServiceClient {
    private final static int DEFAULT_PORT = 7911;
    /**
     * 调用 Hello 服务
     * @param args
     */
    public static void main(String[] args) {
        try {
            // 设置调用的服务地址为本地，端口为 7911
            TTransport transport = getTTransport();
            // 设置传输协议为 TBinaryProtocol
            TProtocol protocol = new TBinaryProtocol(transport);
            Hello.Client client = new Hello.Client(protocol);
            // 调用服务的 helloVoid 方法
//            client.helloVoid();
            String result = client.helloString("ggg");
            System.out.println(result);
            transport.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static TTransport getTTransport() throws Exception{
        try{
            TTransport tTransport = getTTransport("localhost", DEFAULT_PORT, 5000);
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
