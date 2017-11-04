package indi.sword.util.jvm.UserClassLoader;

import java.io.*;

public class MyClassLoader extends ClassLoader {

    private String name; //类加载器的名字
    private String path = "d:\\"; // 加载类的路径
    private final String fileType = ".class"; // class 文件的扩展名

    public MyClassLoader(String name){
        super(); //让系统类加载器成为该类加载器的父加载器
        this.name = name;
    }

    public MyClassLoader(ClassLoader parent,String name){
        super(parent); // 显式指定该类加载器的父加载器
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
        return this.name + " - " +  this.getClass().getClassLoader().getClass().getName();
    }

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = null;
        try {
            byte[] b = loadClassData(name);
            clazz = defineClass(name,b,0,b.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    private byte[] loadClassData(String name) throws Exception{
        InputStream is = null;
        byte[] data = null;
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

        return data;
    }

    public static void main(String[] args) throws Exception{
        MyClassLoader loader1 = new MyClassLoader("loader1");
        loader1.setPath("d:\\myapp\\serverlib\\");

        MyClassLoader loader2 = new MyClassLoader(loader1,"loader2");
        loader2.setPath("d:\\myapp\\clientlib\\");

        MyClassLoader loader3 = new MyClassLoader(null,"loader3");
        loader3.setPath("d:\\myapp\\otherlib\\");

        test(loader2);
        test(loader3);
    }
    public static void test(ClassLoader loader) throws Exception{
        Class clazz = loader.loadClass("Sample");
        Object object = clazz.newInstance();
    }
}
