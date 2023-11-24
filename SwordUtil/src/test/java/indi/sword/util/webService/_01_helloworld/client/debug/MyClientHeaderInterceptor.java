package indi.sword.util.webService._01_helloworld.client.debug;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import java.util.List;

public class MyClientHeaderInterceptor extends AbstractPhaseInterceptor<SoapMessage> {

    private String userId;

    private String userPass;

    public MyClientHeaderInterceptor(String userId, String userPass) {
        super(Phase.PREPARE_SEND); // 在准备发送 SOAP 消息时启动该拦截器
        this.userId = userId;
        this.userPass = userPass;
    }

    @Override
    public void handleMessage(SoapMessage soapMessage) throws Fault {
        System.out.println("----------- Client Interceptor -------------");

        List<Header> headers = soapMessage.getHeaders();

        // 创建 Document 对象
        Document doc = DOMUtils.createDocument();
        Element authHeaderElement = doc.createElement("authHeader");

        // 此处创建的元素应该按照服务器那边的要求。
        Element idElement = doc.createElement("userId");
        idElement.setTextContent(userId);

        Element passElement = doc.createElement("userPass");
        passElement.setTextContent(userPass);

        authHeaderElement.appendChild(idElement);
        authHeaderElement.appendChild(passElement);

        /**
         * 上面代码生成了一个如 XML 文档片段：
         * <authHeader>
         *     <userId>aaa</userId>
         *     <userPass>bbbb</userPass>
         * </authHeader>
         */
        // 把 authHeaderElement 元素包装成 Header，并添加到 SOAP 消息的 Header 列表中。
        headers.add(new Header(new QName("authHeader"),authHeaderElement));

    }
}
