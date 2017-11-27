package indi.sword.util.rpc.thrift._02helloworld;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import indi.sword.util.rpc.thrift._03helloworld.NIO.HelloNonServerDemo;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.ServerContext;
import org.apache.thrift.server.TServerEventHandler;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

class MyTServerEventHandler implements TServerEventHandler
{
    /**
     * 服务成功启动后执行
     */
    public void preServe()
    {
        System.out.println("Start server on port " + HelloNonServerDemo.SERVER_PORT + " ...");
    }

    /**
     * 创建Context的时候，触发
     * 在server启动后，只会执行一次
     */
    public ServerContext createContext(TProtocol input, TProtocol output)
    {
        System.out.println("createContext ... ");
        return null;
    }

    /**
     * 删除Context的时候，触发
     * 在server启动后，只会执行一次
     */
    public void deleteContext(ServerContext serverContext, TProtocol input, TProtocol output)
    {
        System.out.println("deleteContext ... ");
    }

    /**
     * 调用RPC服务的时候触发
     * 每调用一次方法，就会触发一次
     */
    public void processContext(ServerContext serverContext, TTransport inputTransport, TTransport outputTransport)
    {
        /**
         * 把TTransport对象转换成TSocket，然后在TSocket里面获取Socket，就可以拿到客户端IP
         */
        System.out.println("processContext..method invoke ... ");
//        System.out.println(((TSocket) inputTransport).getSocket().getRemoteSocketAddress());

    }
}
