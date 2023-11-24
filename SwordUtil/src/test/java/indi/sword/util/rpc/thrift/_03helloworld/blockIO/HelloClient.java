package indi.sword.util.rpc.thrift._03helloworld.blockIO;


import indi.sword.util.rpc.thrift._03helloworld.thrift.HelloWorldService;
import indi.sword.util.rpc.thrift._03helloworld.thrift.Reponse;
import indi.sword.util.rpc.thrift._03helloworld.thrift.Request;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;


/**
 * 同样是基于同步阻塞模型的thrift client。
 * @author yinwenjie
 */
public class HelloClient {

    static {
        BasicConfigurator.configure();
    }

    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(HelloClient.class);

    public static final void main(String[] args) throws Exception {
        // 服务器所在的IP和端口
        TSocket transport = new TSocket("127.0.0.1", 9111);
        TProtocol protocol = new TBinaryProtocol(transport);

        // 准备调用参数
        Request request = new Request("{\"param\":\"field1\"}", "\\mySerivce\\queryService");
        HelloWorldService.Client client = new HelloWorldService.Client(protocol);

        // 准备传输
        transport.open();
        // 正式调用接口
        Reponse reponse = client.send(request);
        // 一定要记住关闭
        transport.close();

        HelloClient.LOGGER.info("response = " + reponse);
    }
}
