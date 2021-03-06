package indi.sword.util.basic.reflect.proxy;

import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 19:54 2017/10/8
 */
//客户端：生成代理实例，并调用了request()方法
public class Test_Client {

    @Test
    public void test_DynaProxyDemo(){
        MyInvocationHandler handler = new MyInvocationHandler();
        Subject subject = (Subject)handler.bind(new RealSubject());
        subject.request();
    }


/*
    PS：下面结果的信息非常重要，至少对我来说。因为我在动态代理犯晕的根源就在于将上面的 subject.request()理解错了，至少是被表面所迷惑，没有发现这个subject和Proxy之间的联系，一度纠结于最后调用的这个 request()是怎么和invoke()联系上的，而invoke又是怎么知道request存在的。其实上面的true和class $Proxy0就能解决很多的疑问，再加上下面将要说的$Proxy0的源码，完全可以解决动态代理的疑惑了。
    从以上代码和结果可以看出，我们并没有显示的调用invoke()方法，但是这个方法确实执行了。下面就整个的过程进行分析一下：
 */
    @Test
    public void testClient(){

        Subject rs=new RealSubject();//这里指定被代理类
        InvocationHandler ds=new DynamicSubject(rs);
        Class<?> cls=rs.getClass();

        //以下是一次性生成代理

        Subject subject=(Subject) Proxy.newProxyInstance(
                cls.getClassLoader(),cls.getInterfaces(), ds);

        //这里可以通过运行结果证明subject是Proxy的一个实例，这个实例实现了Subject接口
        System.out.println(subject instanceof Proxy);

        //这里可以看出subject的Class类是$Proxy0,这个$Proxy0类继承了Proxy，实现了Subject接口
        System.out.println("subject的Class类是："+subject.getClass().toString());

        System.out.print("subject中的属性有：");

        Field[] field=subject.getClass().getDeclaredFields();
        for(Field f:field){
            System.out.print(f.getName()+", ");
        }

        System.out.print("\n"+"subject中的方法有：");

        Method[] method=subject.getClass().getDeclaredMethods();

        for(Method m:method){
            System.out.print(m.getName()+", ");
        }

        System.out.println("\n"+"subject的父类是："+subject.getClass().getSuperclass());

        System.out.print("\n"+"subject实现的接口是：");

        Class<?>[] interfaces=subject.getClass().getInterfaces();

        for(Class<?> i:interfaces){
            System.out.print(i.getName()+", ");
        }

        System.out.println("\n\n"+"运行结果为：");
        subject.request();
    }

    /**
     * JAVA中有三种类加载器：
     * BootStrap ClassLoader : 此加载器由C++编写，一般开发中是看不到的。
     * Extension ClassLoader : 用来进行扩展类的加载，一般对应的是 jre\lib\ext目录中的类。
     * AppClassLoader : 加载classPath指定的类，是最常使用的一种加载器
     *
     * @Description
     * @Author rd_jianbin_lin
     * @Date 8:30 2017/10/10
     */

    @Test
    public void testClassLoader(){
        RealSubject subject = new RealSubject();
        ClassLoader classLoader = subject.getClass().getClassLoader(); //得到类加载器
        System.out.println("类加载器：" + classLoader.getClass().getName()); // sun.misc.Launcher$AppClassLoader
    }

}
