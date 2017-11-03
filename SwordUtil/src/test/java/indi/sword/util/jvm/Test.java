package indi.sword.util.jvm;


public class Test {
    public static void main(String[] args) {
        System.out.println(FinalTest007.x);
        System.out.println("------------");
        System.out.println(FinalTest007.y);
    }
}

class FinalTest007{

    // 静态常量,编译时常量 -- final，不会再变的了
    public static final int x = 2;

    //静态常量，运行时常量 -- "加载链接初始化"的第三阶段 "初始化" 才赋值的
    public static final int y = "test".length();

    // 静态变量 -- 会变的量
    public static int z = 2;

    static{
        System.out.println("FinalTest007 static block");
    }
}
