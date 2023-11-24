package indi.sword.util.jvm.base;

import indi.sword.util.jvm.UserClassLoader.MyClassLoader;

public class Test08 {
    public static void main(String[] args) {
        MyClassLoader loader1 = new MyClassLoader("loader1");
        MyClassLoader loader2 = new MyClassLoader(loader1,"loader2");
        System.out.println(loader2);
        System.out.println("---------------------------");
        ClassLoader classLoader = loader2;
        while( null != classLoader){
            classLoader = classLoader.getParent();
            System.out.println(classLoader);
        }



    }
}
