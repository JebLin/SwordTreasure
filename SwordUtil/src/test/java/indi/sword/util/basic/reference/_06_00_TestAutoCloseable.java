package indi.sword.util.basic.reference;



/**
 * @author jeb_lin
 * 3:55 PM 2019/9/27
 */
public class _06_00_TestAutoCloseable{
    public static void main(String[] args) {
        _06_00_TestAutoCloseable instance = new _06_00_TestAutoCloseable();
        try{
            instance.doIt();
        }finally {
            instance.close();
        }
    }

    public void doIt() { System.out.println("MyAutoClosable doing it!"); }
    public void close() { System.out.println("MyAutoClosable closed!"); }
}
