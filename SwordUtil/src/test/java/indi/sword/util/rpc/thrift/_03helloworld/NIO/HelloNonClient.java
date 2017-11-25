package indi.sword.util.rpc.thrift._03helloworld.NIO;

import indi.sword.util.rpc.thrift._03helloworld.thrift.HelloWorldService;
import indi.sword.util.rpc.thrift._03helloworld.thrift.Reponse;
import indi.sword.util.rpc.thrift._03helloworld.thrift.Request;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.async.TAsyncClientManager;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TNonblockingSocket;


public class HelloNonClient {

    static {
        BasicConfigurator.configure();
    }

    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(HelloNonClient.class);

    private static Object WAITOBJECT = new Object();

    public static final void main(String[] args) throws Exception {
        TNonblockingSocket transport = new TNonblockingSocket("127.0.0.1", 8090);
        TAsyncClientManager clientManager = new TAsyncClientManager();

        // 准备调用参数(这个testThrift.iface.Request，是我们通过IDL定义，并且生成的)
        Request request = new Request("{\"param\":\"field1\"}", "\\mySerivce\\queryService");
        // 这是客户端对非阻塞异步网络通信方式的支持。
        // 注意使用的消息封装格式，一定要和服务器端使用的一致
        HelloWorldService.AsyncClient asyncClient =
                new HelloWorldService.AsyncClient.Factory(clientManager, new TBinaryProtocol.Factory()).getAsyncClient(transport);

        // 既然是非阻塞异步模式，所以客户端一定是通过“事件回调”方式，接收到服务器的响应通知的
        asyncClient.send(request,new  AsyncMethodCallback<HelloWorldService.AsyncClient.send_call>() {
            /**
             * 当服务器正确响应了客户端的请求后，这个事件被触发
             */
            @Override
            public void onComplete(HelloWorldService.AsyncClient.send_call call) {
                HelloNonClient.LOGGER.info("client onComplete ...");
                Reponse response = null;
                try {
                    response = call.getResult();
                } catch (TException e) {
                    HelloNonClient.LOGGER.error(e);
                    return;
                }
                HelloNonClient.LOGGER.info("response = " + response);
            }

            /**
             * 当服务器没有正确响应了客户端的请求，或者其中过程中出现了不可控制的情况。
             * 那么这个事件会被触发
             */
            @Override
            public void onError(Exception exception) {
                HelloNonClient.LOGGER.info("exception = " + exception);
            }
        });

        //这段代码保证客户端在得到服务器回复前，应用程序本身不会终止
        synchronized (HelloNonClient.WAITOBJECT) {
            HelloNonClient.WAITOBJECT.wait();
        }
    }
}
