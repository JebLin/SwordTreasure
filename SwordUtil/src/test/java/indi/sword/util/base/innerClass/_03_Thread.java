package indi.sword.util.base.innerClass;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/8 20:23
 */
/*
    3.匿名内部类
    匿名内部类应该是平时我们编写代码时用得最多的，在编写事件监听的代码时使用匿名内部类不但方便，而且使代码更加容易维护。
 */
public class _03_Thread {

    public static int i = 5;
    public int b = 6;

    {  // 匿名内部类不仅仅可以写在方法里面，还可以写在代码块里面
        new Runnable(){
            @Override
            public void run() {

            }
        };

        int c = 5;
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 局部内部类和匿名内部类只能访问局部final变量
//                c++; //报错，必须是final
                i++;
                System.out.println(i);
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }
    public static void main(String[] args) throws Exception {
        int c = 6;
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 局部内部类和匿名内部类只能访问局部final变量
//                c++; //报错，必须是final
                i++;
                System.out.println(i);
                System.out.println(Thread.currentThread().getName());
            }
        }).start();


        FutureTask<Integer> result = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 1;
            }
        });
        new Thread(result).start();
        System.out.println(result.get());

        System.out.println("------------------");
        new _03_Thread().test();
    }

    // 这个属于局部匿名内部类
    public void test(){
        int b = 4;
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 局部内部类和匿名内部类只能访问局部final变量
//                b++; //报错，必须是final
                i++;
                System.out.println(i);
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }
}

/*
    使用匿名内部类能够在实现父类或者接口中的方法,同时产生一个相应的对象，但是前提是这个父类或者接口必须先存在才能这样使用。
    匿名内部类是唯一一种没有构造器的类。正因为其没有构造器，所以匿名内部类的使用范围非常有限，大部分匿名内部类用于接口回调。
    匿名内部类在编译的时候由系统自动起名为Outter$1.class。
    一般来说，匿名内部类用于继承其他类或是实现接口，并不需要增加额外的方法，只是对继承方法的实现或是重写。
 */
