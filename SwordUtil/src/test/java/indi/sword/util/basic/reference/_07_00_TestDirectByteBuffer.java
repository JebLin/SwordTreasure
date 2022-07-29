package indi.sword.util.basic.reference;

import java.nio.ByteBuffer;

/**
 * @VM args:-XX:MaxDirectMemorySize=40m -verbose:gc -XX:+PrintGCDetails
 * -XX:+DisableExplicitGC //增加此参数一会儿就会内存溢出java.lang.OutOfMemoryError: Direct buffer memory
 */
public class _07_00_TestDirectByteBuffer {
    public static void main(String[] args) {

        while(true) {
            ByteBuffer buffer = ByteBuffer.allocateDirect(1 * 1024 * 1024);
        }



    }
}
