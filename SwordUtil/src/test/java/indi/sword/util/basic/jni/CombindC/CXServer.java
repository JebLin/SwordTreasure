package indi.sword.util.basic.jni.CombindC;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 5:02 PM 14/06/2018
 * @MODIFIED BY
 */
public class CXServer {
    static {
        System.load("/tmp/testJNI/CXServerLib.so");
    }

    public static native String sayHi(String name);

    public static native int CXServerLibraryVersion(String str);

}
