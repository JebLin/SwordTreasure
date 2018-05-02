package indi.sword.util.jvm.heapAndStack;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Description
 *      -Xmx20M -XX:MaxDirectMemorySize=10M -XX:+PrintGCDetails
 * @Author jeb_lin
 * @Date Created in 10:02 AM 02/05/2018
 * @MODIFIED BY
 */
/*
    代码越过了DirectByteBuffer类，直接通过反射获取Unsafe实例进行内存分配（Unsafe类的getUnsafe()方法限制了只有引导类加载器才会返回实例，
    也就是设计者系统只有rt.jar中的类才能使用Unsafe功能）因为，虽然使用DirectByteBuffer分配内存也会抛出内存溢出异常，
    但它抛异常的时候没有真正像操作系统申请分配内存，而是通过计算得知内存无法分配，于是手动抛异常，真正申请分配内存的方法是
    unsafe.allocateMemroy()

    OutOfMemory

 */
public class DirectMemory {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws Exception{
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe)unsafeField.get(null);
        while (true){
            unsafe.allocateMemory(_1MB);
        }

        /*
            由DirectMemory导致的内存溢出，一个明显的特征是在Heap Dump文件中不会看见明显的异常。
            如果读者发现OOM之后Dump文件很小，而程序中又直接或间接地使用了NIO，那就可以考虑一下是不是这方面的原因了。
         */

    }
}
