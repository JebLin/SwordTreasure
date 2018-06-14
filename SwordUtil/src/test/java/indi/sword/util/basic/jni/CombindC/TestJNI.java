package indi.sword.util.basic.jni.CombindC;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 5:05 PM 14/06/2018
 * @MODIFIED BY
 */
public class TestJNI {
    public static void main(String[] args) {
        System.out.println(CXServer.sayHi("It's a result return by main.so file!! "));
        System.out.println(CXServer.CXServerLibraryVersion("return 1"));
    }
}
