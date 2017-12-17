package indi.sword.util.webService._01_helloworld.client.wsdl2java;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.1.4
 * 2017-12-16T17:58:58.810+08:00
 * Generated source version: 3.1.4
 * 
 */
@WebServiceClient(name = "HelloworldWsImpl", 
                  wsdlLocation = "http://192.168.106.1/ljb?wsdl",
                  targetNamespace = "http://impl.server._01_helloworld.webService.util.sword.indi/") 
public class HelloworldWsImpl extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://impl.server._01_helloworld.webService.util.sword.indi/", "HelloworldWsImpl");
    public final static QName HelloworldWsImplPort = new QName("http://impl.server._01_helloworld.webService.util.sword.indi/", "HelloworldWsImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://192.168.106.1/ljb?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(HelloworldWsImpl.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://192.168.106.1/ljb?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public HelloworldWsImpl(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public HelloworldWsImpl(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HelloworldWsImpl() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public HelloworldWsImpl(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public HelloworldWsImpl(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public HelloworldWsImpl(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns HelloworldWsInterface
     */
    @WebEndpoint(name = "HelloworldWsImplPort")
    public HelloworldWsInterface getHelloworldWsImplPort() {
        return super.getPort(HelloworldWsImplPort, HelloworldWsInterface.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns HelloworldWsInterface
     */
    @WebEndpoint(name = "HelloworldWsImplPort")
    public HelloworldWsInterface getHelloworldWsImplPort(WebServiceFeature... features) {
        return super.getPort(HelloworldWsImplPort, HelloworldWsInterface.class, features);
    }

}
