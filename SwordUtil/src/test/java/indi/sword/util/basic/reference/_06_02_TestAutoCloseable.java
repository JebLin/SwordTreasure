package indi.sword.util.basic.reference;


import lombok.Cleanup;

/**
 * @author jeb_lin
 * 3:55 PM 2019/9/27
 */
public class _06_02_TestAutoCloseable implements AutoCloseable {

    public static void main(String[] args) {
        @Cleanup
        _06_01_TestAutoCloseable instance = new _06_01_TestAutoCloseable();
        instance.doIt();

    }
    public void doIt() { System.out.println("MyAutoClosable doing it!"); }

    @Override
    public void close() {
        System.out.println("MyAutoClosable closed!");
    }
}
