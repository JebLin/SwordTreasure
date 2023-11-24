package indi.sword.util.basic.reference;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author jeb_lin
 * 12:27 PM 2019/9/23
 */
public class _01_00_TestWeakReference {
    public static void main(String[] args) throws Exception{
        WeakReference<String> weakReference = new WeakReference<>("A");
        System.out.println("hello world");
        System.out.println(weakReference.get());

        ReferenceQueue<String> stringReferenceQueue = new ReferenceQueue<>();
        WeakReference<String> weakReference2 = new WeakReference<>("A2",stringReferenceQueue);
        System.out.println("hello world");
        System.out.println(weakReference2.get());

        System.gc();
        Thread.sleep(50);
        System.out.println(stringReferenceQueue.poll());

    }
}
