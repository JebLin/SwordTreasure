package indi.sword.util.jvm.base;

/*
    假如类存在直接的父类，并且这个父类还没有被初始化，那就先初始化直接的父类。
    （但是！！！ 在初始化一个类时，不会初始化它所实现的接口。在初始化一个接口时，并不会先初始化它的父接口。
    因此，一个父接口并不会因为它的子接口或者实现类的初始化而初始化。
    只有当程序首次使用特定接口的静态变量时，才会导致该接口的初始化。）
 */
public class Test05 {
    static{
        System.out.println("Test05 static block");
    }

    public static void main(String[] args) {
        Parent2 parent2;
        System.out.println("----------------------");
        parent2 = new Parent2();

        System.out.println(Parent2.a);
        System.out.println(Child2.b);
    }
}

class Parent2{
    static int a = 3;
    static {
        System.out.println("Parent2 static block");
    }
}
class Child2 extends Parent2{
    static int b =4;
    static {
        System.out.println("Child2 static block");
    }
}
