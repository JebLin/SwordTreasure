package indi.sword.util.jdk8.lambda;

import org.junit.Test;

/**
 * Created by rd_Jianbin_lin on 2017/9/21.
 *
 * 测试JDK 8中的默认接口
 * 在jdk8之前，interface之中可以定义变量和方法，变量必须是public、static、final的，方法必须是public、abstract的。
 *
 * 静态方法，只能通过接口名调用，不可以通过实现类的类名或者实现类的对象调用。default方法，只能通过接口实现类的对象来调用。
 */
public class _13_TestDefaultInteface {

    @Test
    public void test01(){
        SubClass sc = new SubClass();
        System.out.println(sc.getName_defautlMethod());

        // static方法必须通过接口类调用
        MyInterface.staticMethod();
    }
}

class SubClass implements MyInterface,MyInterface2{

    // 当然如果接口中的默认方法不能满足某个实现类需要，那么实现类可以覆盖默认方法。
    @Override
    public String getName_defautlMethod() { // 因为有两个相同的方法，所以要指定实现哪一个
        return MyInterface.super.getName_defautlMethod();
    }

}

interface MyInterface {

    default String getName_defautlMethod(){
        return "MyInterface 接口中的默认方法";
    }

    public static void staticMethod(){
        System.out.println("接口中的静态方法");
    }
}

interface MyInterface2 {

    default String getName(){
        return "MyInterface2 接口中的默认方法";
    }

}