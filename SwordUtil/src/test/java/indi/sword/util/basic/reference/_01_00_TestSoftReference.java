package indi.sword.util.basic.reference;

import java.lang.ref.SoftReference;

/**
 * @author jeb_lin
 * 12:27 PM 2019/9/23
 */
public class _01_00_TestSoftReference {
    public static void main(String[] args) {
        SoftReference<String> softReference = new SoftReference<>("A");

        System.out.println("hello world");
        System.out.println(softReference.get());

    }
}
