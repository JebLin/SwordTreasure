package indi.sword.util.jvm.base;


public class Test04 {
    static{
        System.out.println("Test04 static block");
    }
    public static void main(String[] args) {
        // 只有当程序访问的静态变量或静态方法确实在当前类或当前接口中定义时，才可以认为是对类或接口的主动使用。
        System.out.println(child.a);
    }
}
class Parent{
    static int a = 3;
    static {
        System.out.println("Parent static block");
    }
}
class child extends Parent{
    static{
        System.out.println("Child static block");
    }
}
