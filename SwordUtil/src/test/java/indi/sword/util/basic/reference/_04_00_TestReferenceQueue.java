package indi.sword.util.basic.reference;

import java.lang.ref.ReferenceQueue;

/**
 * @author jeb_lin
 * 10:47 AM 2019/9/24
 */
public class _04_00_TestReferenceQueue {
    public static void main(String[] args) throws Exception{
        ReferenceQueue<String> stringReferenceQueue = new ReferenceQueue<>();
        stringReferenceQueue.remove();
        System.out.println("end");



    }
}
