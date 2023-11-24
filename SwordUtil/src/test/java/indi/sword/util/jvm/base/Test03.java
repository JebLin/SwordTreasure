package indi.sword.util.jvm.base;

import java.util.Random;

public class Test03 {
    public static void main(String[] args) {
        System.out.println(FinalTest02.x);
    }
}
class FinalTest02{
    //这个属于运行时常量。加载链接初始化 的第三阶段 初始化才赋值的
    public static final int x = new Random().nextInt(100);

    static{
        System.out.println("FinalTest2 static block");
    }
}
