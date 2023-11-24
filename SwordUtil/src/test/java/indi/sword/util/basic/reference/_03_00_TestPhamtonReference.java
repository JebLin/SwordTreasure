package indi.sword.util.basic.reference;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * @author jeb_lin
 * 12:22 PM 2019/9/23
 */
public class _03_00_TestPhamtonReference {
    public static void main(String[] args) throws Exception {
        ReferenceQueue<String> referenceQueue = new ReferenceQueue<>();
        PhantomReference<String> phantomReference = new PhantomReference<>("hello",referenceQueue);
        System.out.println(phantomReference.get());

        synchronized (phantomReference){
            Thread.currentThread().wait();
        }
    }
}
