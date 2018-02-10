package indi.sword.util.webService._01_helloworld.server.interceptor;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.List;

/**
 * 自定义拦截器
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/17 10:10
 */
// 通过PhaseInterceptor，可以指定拦截器在哪个阶段起作用
public class MyServerAuthInterceptor extends AbstractPhaseInterceptor<SoapMessage>{

    public MyServerAuthInterceptor() {

        // super 表示显示调用父类有参数的构造器
        // 显示调用弗雷构造器之，程序将不会隐式调用父类无参数的构造器。
        super(Phase.PRE_INVOKE); // 该拦截器将会 “调用之前” 拦截 SOAP 消息。
    }

    /*
        实现自己的拦截器时，需要实现 HandleMessage 方法。
        HandleMessage 方法中的形参就是被拦截到的 Soap 消息。
        一旦程序获取了 SOAP 消息，剩下的事情就可以解析 SOAP 消息，或者修改 SOAP 消息
     */
    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
//        System.out.println("--------------------------" + soapMessage);
        // 从上面就可以看出，我们已经拦截到了 SOAP 消息了
        // 上面的输出语句就足以说明拦截器能否生效了。下面是一些复杂的处理

        // 得到 SOAP 消息所有 Header
        List<Header> headers = soapMessage.getHeaders();

        if(headers == null || headers.size() < 1){
            throw new Fault(new IllegalArgumentException("根本没有 Header ，别想调用了"));
        }

        // 假如要求第一个 Header 携带了用户名、密码消息。
        Header authHeader = null;
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getName().getLocalPart().equals("authHeader")){ // Element 的名字
                authHeader = headers.get(i);
            }
        }

        if(null == authHeader){
            throw new Fault(new IllegalArgumentException("用户名、密码不正确！"));
        }


        Element ele = (Element)authHeader.getObject();

        NodeList userIds = ele.getElementsByTagName("userId");
        NodeList userPass = ele.getElementsByTagName("userPass");

        if(userIds.getLength() != 1){
            throw new Fault(new IllegalArgumentException("用户名格式不对！"));
        }

        if(userPass.getLength() != 1){
            throw new Fault(new IllegalArgumentException("密码格式不对！"));
        }

        // 得到第一个 userId 元素里的文本内容，以该内容作为用户名字
        String userIdStr = userIds.item(0).getTextContent();
        String userPassStr = userPass.item(0).getTextContent();

        // 实际项目中，应该去查询数据库，查看该用户名密码是否被授权调用该 Web Service
        if(!userIdStr.equals("ljb") || !userPassStr.equals("123456")){
            throw new Fault(new IllegalArgumentException("用户名、密码不正确！"));
        }
    }
}
