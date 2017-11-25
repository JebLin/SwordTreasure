package indi.sword.util.rpc.thrift._03helloworld.impl;


import indi.sword.util.rpc.thrift._03helloworld.thrift.HelloWorldService;
import indi.sword.util.rpc.thrift._03helloworld.thrift.RESCODE;
import indi.sword.util.rpc.thrift._03helloworld.thrift.Reponse;
import indi.sword.util.rpc.thrift._03helloworld.thrift.Request;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;


/**
 * 我们定义了一个HelloWorldService.Iface接口的具体实现。<br>
 * 注意，这个父级接口：HelloWorldService.Iface，是由thrift的代码生成工具生成的<br>
 * 要运行这段代码，请导入maven-log4j的支持。否则修改LOGGER.info方法
 * @author yinwenjie
 */
public class HelloWorldServiceImpl implements HelloWorldService.Iface {
    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(HelloWorldServiceImpl.class);

    /**
     * 在接口定义中，只有一个方法需要实现。<br>
     * HelloWorldServiceImpl.send(Request request) throws TException <br>
     * 您可以理解成这个接口的方法接受客户端的一个Request对象，并且在处理完成后向客户端返回一个Reponse对象<br>
     * Request对象和Reponse对象都是由IDL定义的结构，并通过“代码生成工具”生成相应的JAVA代码。
     */
    @Override
    public Reponse send(Request request) throws TException {
        /*
         * 这里就是进行具体的业务处理了。
         * */
        String json = request.getParamJSON();
        String serviceName = request.getServiceName();
        HelloWorldServiceImpl.LOGGER.info("得到的json：" + json + " ；得到的serviceName: " + serviceName);

        // 构造返回信息
        Reponse response = new Reponse();
        response.setResponeCode(RESCODE._200);
        response.setResponseJSON("{\"user\":\"yinwenjie\"}");
        return response;
    }
}
