package indi.sword.util.jvm;

/**
 * @Decription  测试类的 加载、连接、初始化
 * @Author: rd_jianbin_lin
 * @Date : 2017/11/3 12:17
 */
public class Test00{
    public static void main(String[] args) {
        System.out.println(A.a);
        System.out.println(A.b);

    }
}

class A{
    public static A Singleton = new A();
    public static int a = 0;
    public static int b;
    //	public static A Singleton = new A();    //顺序不同将会导致结果不同
    private A(){
        a++;
        b++;
    }
}

