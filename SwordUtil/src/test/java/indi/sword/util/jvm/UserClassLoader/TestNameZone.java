package indi.sword.util.jvm.UserClassLoader;

/*
    启动类是 系统类加载器加载的，属于爸爸。 Sample类是 loader1加载的，属于儿子。
    下面的类在 cmd下跑会报错。java.lang.NoClassDefFoundError: Sample。因为爸爸看不到儿子。

    记住了，这个类是要连同 Sample MyClassLoader一起丢在 default包，
    然后编译后的 class 文件拿去 D:\myapp\syslib 目录下，打开 cmd跑的，
    (可能你在default包下可以运行成功，因为都是同一个命名空间，当然没问题）
    然后记得把 syslib 上不要放Sample类，不然不会用 loader1加载，切忌。
    不然你还真测试不出来不同命名空间的差异出来。
 */
public class TestNameZone {
    public static void main(String[] args) throws Exception{
        System.out.println("本启动类的类加载器 --> " + new TestNameZone().getClass().getClassLoader()); // 系统类加载器：sun.misc.Launcher$AppClassLoader@14dad5dc

        MyClassLoader loader1 = new MyClassLoader("loader1");
        loader1.setPath("d:\\myapp\\otherlib\\");
        Class clazz = loader1.loadClass("indi.sword.util.jvm.UserClassLoader.Sample"); // 创建一个 Sample 类对象。 loader1加载的。
//        Class clazz = loader1.loadClass("Sample"); // 创建一个 Sample 类对象。 loader1加载的。
//        Class clazz = loader1.getParent().loadClass("Sample"); // 如果让他爸爸加载，那就是在同一个命名空间，只不过你需要把Sample 和Dog都加目录去。
        Object object = clazz.newInstance();
        Sample sample = (Sample)object; // 这一句报错，为什么呢。 因为object的类命名空间在 loader1里面，启动类爸爸根本就看不到。
        System.out.println(sample.v1);
    }
}
/*
    运行结果：
        D:\myapp\syslib>java TestNameZone
        本启动类的类加载器 --> sun.misc.Launcher$AppClassLoader@4e0e2f2a
        Sample is loaded by : loader1 - sun.misc.Launcher$AppClassLoader
        Dog is loaded by : loader1 - sun.misc.Launcher$AppClassLoader
        Exception in thread "main" java.lang.NoClassDefFoundError: Sample
                at TestNameZone.main(TestNameZone.java:10)
        Caused by: java.lang.ClassNotFoundException: Sample
                at java.net.URLClassLoader.findClass(URLClassLoader.java:381)
                at java.lang.ClassLoader.loadClass(ClassLoader.java:424)
                at sun.misc.Launcher$AppClassLoader.loadClass(Launcher.java:331)
                at java.lang.ClassLoader.loadClass(ClassLoader.java:357)
                ... 1 more
 */
