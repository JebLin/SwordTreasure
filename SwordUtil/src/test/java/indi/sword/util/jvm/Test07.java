package indi.sword.util.jvm;

public class Test07 {
        public static void main(String[] args) throws Exception{

        // 获得系统类加载器
        ClassLoader loader = ClassLoader.getSystemClassLoader();

        // 调用 ClassLoader 类的 loadClass 方法加载一个类，并不是对类的主动使用，不会导致类的初始化。
        // 加载 连接 初始化。 只是加载
        Class<?> clazz = loader.loadClass("indi.sword.util.jvm.CL");
        System.out.println("--------------------");
        clazz = Class.forName("indi.sword.util.jvm.CL");
    }
}

class CL{
    static{
        System.out.println("class CL");
    }
}
