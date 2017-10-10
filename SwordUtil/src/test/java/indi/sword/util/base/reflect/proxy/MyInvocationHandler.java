package indi.sword.util.base.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理，相当于代理的总代理，减少了一大堆代理实现类XXXProxy。
 * （就是你实现了一个借口，我就能帮你动态产生一个实例）
 *
 * @Description
 * @Author rd_jianbin_lin
 * @Date 8:12 2017/10/10
 */
public class MyInvocationHandler implements InvocationHandler {

    private Object obj; //真实主题

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { //调用动态方法
        System.out.println("before calling " + method);

        Object object = method.invoke(obj, args);//调用方法，传入真实主题和参数

        System.out.println("after calling " + method);
        return object; //调用方法，传入真实主题和参数
    }

    public Object bind(Object obj){ //绑定真实主题
        this.obj = obj;
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(),obj.getClass().getInterfaces(),this);//取得代理对象
        //newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)
    }
}
