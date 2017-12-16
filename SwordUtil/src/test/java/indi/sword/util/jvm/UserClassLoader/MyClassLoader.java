package indi.sword.util.jvm.UserClassLoader;

import org.junit.Test;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    private String name; //类加载器的名字
    private String path = "d:\\"; // 加载类的路径
    private final String fileType = ".class"; // class 文件的扩展名

    public MyClassLoader(){
        super(); //让系统类加载器成为该类加载器的父加载器
    }

    public MyClassLoader(String name){
        super(); //让系统类加载器成为该类加载器的父加载器
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public String toString(){
        return this.name;
    }

    public MyClassLoader(ClassLoader parent,String name){
        super(parent); // 显式指定该类加载器的父加载器
        this.name = name;
    }

    /*
        所有用户自定义的类加载器应该继承 ClassLoader 类，然后覆盖它的 findClass(String name)方法即可，该方法依据参数指定的类的名字，返回对应的Class对象的应用。
        复写这个方法，因为 ClassLoader.loadClass(String,boolean) 方法会用到
        idea ： Crrl + Alt + B 这个方法就可以找到引用
     */
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;
        try {
            byte[] b = loadClassData(name);
            // Converts an _01_array of bytes into an instance of class
            if(b.length > 0){
                clazz = defineClass(name,b,0,b.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    /*
        加载二进制文件，把数据加载到内存中来
     */
    private byte[] loadClassData(String name){
        byte[] data = new byte[0];
        try {
            InputStream is = null;
            data = null;
            ByteArrayOutputStream baos = null;

            this.name = this.name.replace(".","\\");
            String fileName = path + name + fileType;
            is = new FileInputStream(new File(fileName));
            baos = new ByteArrayOutputStream();
            int ch = 0;
            while( -1 != (ch = is.read())){
                baos.write(ch);
            }
            data = baos.toByteArray();

            baos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void test(ClassLoader loader) throws Exception{
        /*
            注意：第三层的“应用类加载器”会自动加载环境变量classPath指定的文件路径。 .; 表示当前目录。

            当前编译完目录下肯定没有 Sample.class 文件，但是有 indi.sword.util.jvm.UserClassLoader.Sample.class 文件，请认清两者的区别
            每次加载都会从最上层加载器往下方加载，那么test(loader2);
            加载顺序就是  BootStrap根加载器 --> 扩展类加载器 --> 应用类加载器，前俩个加载的是系统的 Class 文件，所以从第三个看起就好了。
            很明显，“应用类加载器”找不到 Sample.class 文件，
            那么就会去找父加载器 loader1 指定的目录即"d:\\myapp\\serverlib\\" 下有没有 Sample.class 有的话就加载并逐层返回（子类就负责返回就行了，不用再去加载 Class 了），
            没有的话，就加载 loader2 指定的目录即"d:\\myapp\\clientlib\\" 下的Sample.class。找到的话，加载成功并且逐层返回。
            没有的话就报“ClassNotFoundException”，依此类推。

            那么如果是loader3的两层结果情况，那么根加载器bootstrap显然无法加载 Sample.class，
            那么就直接取找 loader3目录下的"d:\\myapp\\otherlib\\" Sample.class文件，
            找到的话，加载成功并且返回。没有的话就报“ClassNotFoundException”。
         */
        Class clazz1 = loader.loadClass("Sample");
        Object object1 = clazz1.newInstance(); // 小tip，newInstance()是对类的主动使用，会加载类


        /*
            下面这个类，可以被“应用类加载器”在当前目录下加载并逐层返回。
            但是 loader3 会报“ClassNotFoundException”
         */
//        Class clazz2 = loader.loadClass("indi.sword.util.jvm.UserClassLoader.Sample");
//        Object object2 = clazz2.newInstance();

    }


    public static void main(String[] args) throws Exception{

    }


}
