package indi.sword.util.jvm.UserClassLoader;

import org.junit.Test;

import java.io.ObjectInputStream;

public class TestMyClassLoader {

    @Test
    public void test(){
        // 根加载器null --> 扩展类加载器Extends --> 应用类加载器 --> loader1
        MyClassLoader loader1 = new MyClassLoader("loader1");
        loader1.setPath("d:\\myapp\\serverlib\\");

        // 根加载器null --> 扩展类加载器Extends --> 应用类加载器 --> loader1 --> loader2
        // 参数 loader1 将作为 loader2 的父加载器（loader2 包装了 loader1 ，具体看构造方法）
        MyClassLoader loader2 = new MyClassLoader(loader1,"loader2");
        loader2.setPath("d:\\myapp\\clientlib\\");

        // 根加载器null --> loader3
        MyClassLoader loader3 = new MyClassLoader(null,"loader3");
        loader3.setPath("d:\\myapp\\otherlib\\");

        try {
            MyClassLoader.test(loader2);
            System.out.println("---------------");
            MyClassLoader.test(loader3);
            System.out.println("---------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParent(){
        MyClassLoader loader2 = new MyClassLoader(new MyClassLoader("loader1"),"loader2");
        System.out.println(loader2);
        System.out.println("---------------------------");
        ClassLoader classLoader = loader2;
        while( null != classLoader){
            classLoader = classLoader.getParent();
            System.out.println(classLoader);
        }
    }

    @Test
    public void testNameZone() throws Exception{

    }
}
