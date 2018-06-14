package indi.sword.util.basic.jni;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 11:34 AM 16/04/2018
 * @MODIFIED BY
 */
public class HelloJNI {

    // 声明 so 库中的方法
    public native static String sayHi(String name);

    // 载入 so 动态链接库
    static {
        //﻿你可以将 /home/username/ 更换为任意你想要使用的工作目录，但是必须保证的是工作目录下能通过java类的完整包名找到该类
        System.load("/main.so");
    }

    // java 类入口函数
    public static void main(String[] args) {
        System.out.println(sayHi("It's a result return by main.so file!!"));
    }

}

/*
    ﻿我们在 so 动态链接库中需要实现的函数为 sayHI(String name)，该函数会把我们传入的字符串直接返回给我们，功能就这么简单.
    ﻿a、System.load("/home/username/main.so");// 这种方式需要提供 so 文件所在的绝对路径
     b、SysteX要把生成的 so 动态链接库复制到 java 的 lib 加载路径中，即 LD_LIBRARY_PATH 对应的路径
 */