package indi.sword.util.basic.reflect.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 19:52 2017/10/8
 */
//实现了InvocationHandler
public class DynamicSubject implements InvocationHandler
{
    private Object obj;//这是动态代理的好处，被封装的对象是Object类型，接受任意类型的对象

    public DynamicSubject()
    {
    }

    public DynamicSubject(Object obj)
    {
        this.obj = obj;
    }

    //这个方法不是我们显示的去调用
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        System.out.println("before calling " + method);

        method.invoke(obj, args);

        System.out.println("after calling " + method);

        return null;
    }

}