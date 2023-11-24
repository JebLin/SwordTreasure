package indi.sword.util.basic.Thread.Volatile;

/**
 * 测试可见性
 * @author jeb_lin
 * 5:23 PM 2022/7/29
 */
public class TestVolatileVisibility {
    public static /* volatile */int found = 0;
    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("A waiting money,Begin...");
            // 加上 volatile 能让每次都去内存读取数据到CPU cache
            while (0 == found){
            }
            System.out.println("A get the money, End ...");
        },"myThread-A").start();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("B send money");
            change();

        },"myThread-B").start();
    }
    public static void change(){
        found = 1;
    }
}
