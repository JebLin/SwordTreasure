package indi.sword.util.basic.innerClass;

/**
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/8 20:39
 */
/*
    　　4.静态内部类
            静态内部类也是定义在另一个类里面的类，只不过在类的前面多了一个关键字static。
            静态内部类是不需要依赖于外部类的，这点和类的静态成员属性有点类似，并且它不能使用外部类的非static成员变量或者方法，
            这点很好理解，因为在没有外部类的对象的情况下，可以创建静态内部类的对象，如果允许访问外部类的非static成员就会产生矛盾，
            因为外部类的非static成员必须依附于具体的对象。
 */
public class _04_staticInnerClass {
    public static void main(String[] args) {

    }
}
class Outter {
    int a = 5;
    static int b =6;
    public Outter() {

    }

    // 静态内部类可以当做静态变量或者静态方法来理解
    static class Inner {
        public Inner() {
//            System.out.println(a); //用不了a，因为是成员变量
            System.out.println(b);
        }
    }
}