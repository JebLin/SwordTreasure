package indi.sword.util.base.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 8:12 2017/10/10
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object obj; //真实主题

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { //调用动态方法
        return method.invoke(this.obj,args); //调用方法，传入真实主题和参数
    }

    public Object bind(Object obj){ //绑定真实主题
        this.obj = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);//取得代理对象
        //newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)
    }
}
