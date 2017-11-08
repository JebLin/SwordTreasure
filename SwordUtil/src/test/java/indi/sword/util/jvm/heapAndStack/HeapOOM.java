package indi.sword.util.jvm.heapAndStack;

import java.util.ArrayList;
import java.util.List;

// Java堆:所有对象的实例分配都在Java堆上分配内存，堆大小由-Xmx和-Xms来调节，sample如下所示：
/*
    加上JVM参数-verbose:gc -Xms10M -Xmx10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError，就能很快报出OOM：
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
/*
    Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        at java.util.Arrays.copyOf(Arrays.java:3210)
        at java.util.Arrays.copyOf(Arrays.java:3181)
        at java.util.ArrayList.grow(ArrayList.java:261)
        at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:235)
        at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:227)
        at java.util.ArrayList.add(ArrayList.java:458)
        at indi.sword.util.jvm.heapAndStack.HeapOOM.main(HeapOOM.java:16)
 */
