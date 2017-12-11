package indi.sword.util.base.designpattern.singleton;

public class Test {
    public static void main(String[] args) {

        Singleton03.test();
        System.out.println("----------------------");
        System.out.println("调用一个类，不会初始化他的内部类");
        System.out.println("----------------------");
        Singleton03.getInstance();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Singleton singleton = Singleton.getInstance();
                System.out.println(singleton);
            }).start();
        }


    }
}
