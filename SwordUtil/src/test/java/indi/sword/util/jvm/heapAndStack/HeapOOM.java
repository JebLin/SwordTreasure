package indi.sword.util.jvm.heapAndStack;

import java.util.ArrayList;
import java.util.List;

// Java堆:所有对象的实例分配都在Java堆上分配内存，堆大小由-Xmx和-Xms来调节，sample如下所示：
/*
    -Xms : 初始堆内存
    -Xmx：最大堆内存
    -Xmn：年轻代大小
    -Xss：每个线程的栈大小
    加上JVM参数 -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
    Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    并且能自动生成Dump。
 */
public class HeapOOM {
    static class OOMObject{}

    /**
     * @param args
     */
    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();

        while(true){
            list.add(new OOMObject());
        }
    }
}
