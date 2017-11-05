package indi.sword.util.jvm.UserClassLoader;

/*
    当 Sample 类被加载、连接、初始化后，它的生命周期就开始了。当代表 Sample 类的 Class 对象不再被引用，即不可触及时，
    Class 对象就会结束生命周期， Sample 类在方法区内的数据也会被卸载，从而结束 Sample 类的生命周期。
    由此可见，一个类合适结束生命周期，取决于代表它的 Class 对象何时结束生命周期。
    由 Java 虚拟机自带的类加载器所加载的类，在虚拟机的生命周期中，始终不会被卸载。
    那么Java虚拟机自带的类包括，bootstrap根类加载器、extend扩展类加载器、System系统类加载器。Java虚拟机本身会始终引用这些类加载器，而这些类加载器则会始终引用它们所加载的类的 Class 对象，因此这些 Class 对象始终是可触及的。
 */
//  由用户自定义的类加载器所加载的类是可以被卸载的！！！
public class TestUnloadClassLoader {
    public static void main(String[] args) throws Exception{

        /*
            为什么直接从根加载器加载呢？
            因为我不受 应用类加载器的影响，如果你直接 new MyClassLoader("loader1"); 的话，
            它会从应用类加载器加载，那么由于应用类加载器不会被卸载，所以待会 Sample 类都是由应用类（系统类）加载器加载的，
            那么不会二次加载 Sample。
            由于我们是要测试卸载自定义类，所以需要直接继承根类加载器，或者！你把这个编译好的类拉到 d:\\myapp\\syslib 下面去跑。
            （因为那个目录下没有 Sample类，那么就是应用类加载器在当前目录 .; 没法加载，会让子类 loader1 去对应目录下加载。
         */
//        MyClassLoader loader1 = new MyClassLoader("loader1");
        MyClassLoader loader1 = new MyClassLoader(null,"loader1");
        loader1.setPath("D:\\myapp\\otherlib\\"); // 记得在这个目录下丢一个 Sample 类

        Class clazz = loader1.loadClass("Sample");
        System.out.println(clazz.hashCode());
        Object obj = clazz.newInstance();

        obj = null;
        clazz = null;
        loader1 = null;
        /*
            loader1.getParent() = null; // 这个是编译会报错的。
            因为 ： public final ClassLoader getParent(); final 方法，
            保护了应用类加载器以及头上的 ext加载器 与 bootstrap加载器不会被修改。
         */
//        loader1 = new MyClassLoader("loader1");
        loader1 = new MyClassLoader(null,"loader1");

        loader1.setPath("D:\\myapp\\otherlib\\");
        clazz = loader1.loadClass("Sample");
        System.out.println(clazz.hashCode()); // hashCode 不一样说明就是不同的对象。
    }
}
