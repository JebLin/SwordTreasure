package indi.sword.util.jvm;

// 类加载器 ClassLoader
public class Test01 {
    public static void main(String[] args) throws Exception{
        Class clazz = Class.forName("java.lang.String");
        System.out.println(clazz.getClassLoader()); // null

        Class clazz2 = Class.forName("indi.sword.util.jvm.Test00");
        System.out.println(clazz2.getClassLoader()); // sun.misc.Launcher$AppClassLoader@14dad5dc
    }
}
