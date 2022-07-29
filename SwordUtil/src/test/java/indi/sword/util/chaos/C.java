package indi.sword.util.chaos;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author jeb_lin
 * 8:46 PM 2020/1/2
 */
@Component
public class C implements FactoryBean<B> , InitializingBean, ApplicationListener<ApplicationEvent> {

    @Override
    public B getObject() throws Exception {
        return new B();
    }

    @Override
    public Class<? extends B> getObjectType() {
        return null;

    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {

    }
}
