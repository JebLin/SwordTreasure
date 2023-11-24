package indi.sword.util.jvm.base;



public class Test02 {
    public static void main(String[] args) {
        System.out.println(FinalTest.x); // 引用静态的常量，不属于类的主动使用
        System.out.println("---------------");
        System.out.println(FinalTest.y);
    }
}

class FinalTest{
    public static final int x = 2; // 静态常量 -- final，不会再变的了
    public static int y = 2; // 静态变量

    static{
        System.out.println("FinalTest static block");
    }
}