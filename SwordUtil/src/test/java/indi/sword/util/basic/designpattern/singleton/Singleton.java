package indi.sword.util.basic.designpattern.singleton;

/**
 * 懒汉式单例 （加载一个类的时候，默认不会加载内部类，因此为懒汉）
 *
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/8 14:36
 */

// 懒汉式单例类.在第一次调用的时候实例化自己
public class Singleton {
    private Singleton() {
    }

    private static Singleton single = null;

    //静态工厂方法
    public static Singleton getInstance() {
        if (single == null) {
            single = new Singleton();
        }
        return single;
    }
}
/*
    Singleton通过将构造方法限定为private避免了类在外部被实例化，在同一个虚拟机范围内，Singleton的唯一实例只能通过getInstance()方法访问。
    （事实上，通过Java反射机制是能够实例化构造方法为private的类的，那基本上会使所有的Java单例实现失效。此问题在此处不做讨论。）
    但是以上懒汉式单例的实现没有考虑线程安全问题，它是线程不安全的，并发环境下很可能出现多个Singleton实例，要实现线程安全，有以下三种方式，都是对getInstance这个方法改造，保证了懒汉式单例的线程安全，
 */

//1、在getInstance方法上加同步
class Singleton01 {
    private Singleton01() {
    }

    private static Singleton01 single = null;

    // 加synchronized
    public static synchronized Singleton01 getInstance() {
        if (single == null) {
            single = new Singleton01();
        }
        return single;
    }
}

// 2、双重检查锁定
class Singleton02 {
    private Singleton02() {
    }

    private static Singleton02 single = null;

    // 加synchronized
    public static synchronized Singleton02 getInstance() {
        if (single == null) {
            synchronized (Singleton.class) {
                if (single == null) {
                    single = new Singleton02();
                }
            }
        }
        return single;
    }
}

// 3、静态内部类 
class Singleton03 {
    private static class LazyHolder {
        static {
            System.out.println("LazyHolder");
        }
        private static final Singleton03 INSTANCE = new Singleton03();
    }

    private Singleton03() {
    }

    public static final Singleton03 getInstance() {
        return LazyHolder.INSTANCE;
    }

    public static void test() {
        System.out.println("test");
    }
}


