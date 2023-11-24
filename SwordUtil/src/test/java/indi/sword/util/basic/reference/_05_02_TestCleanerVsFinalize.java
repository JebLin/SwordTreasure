package indi.sword.util.basic.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import sun.misc.Cleaner;

/**
 * @author jeb_lin
 * 3:36 PM 2019/9/26
 */
// 测试抛出异常
public class _05_02_TestCleanerVsFinalize {
    public static void main(String[] args) throws Exception {

//        testFinalizeException();
        testCleanerException();

    }

    private static void testCleanerException() throws Exception{
        int index = 0;
        while (true) {
            Thread.sleep(1000);
            // 提醒 GC 去进行垃圾收集了
            System.gc();

            // 该对象不断重新指向其他地方，那么原先指针指向的对象的就属于需要回收的数据
            Object obj = new Object();
            /*
                 增加 obj 的虚引用,定义清理的接口 CleanerTask
                 第一个参数：需要监控的堆内存对象
                 第二个参数：程序释放资源前的回调。
             */
            Cleaner.create(obj, new DoSomethingThread("thread_" + index++));
        }
    }

    private static void testFinalizeException() throws Exception{
        int index = 0;
        while (true) {
            Thread.sleep(1000);
            // 提醒 GC 去进行垃圾收集了
            System.gc();

            // 该对象不断重新指向其他地方，那么原先指针指向的对象的就属于需要回收的数据
            DemoObject obj = new DemoObject("demo" + index++);
            System.out.println(" running DoSomething ...");
            System.out.println(10 / 0);
            //这个地方死了，上面有异常，不走到这，可是确连个异常都不跑出来
            System.out.println(" running DoSomething End ...");
        }
    }

    @Data
    @AllArgsConstructor
    @ToString
    static class DemoObject {
        private String name;
        @Override
        protected void finalize() throws Throwable {
            System.out.println(name + " running DoSomething ...");
            System.out.println(10 / 0);
            //这个地方死了，上面有异常，不走到这，可是确连个异常都不打印到出来
            System.out.println(name + " running DoSomething End ...");
        }
    }

    static class DoSomethingThread implements Runnable {
        private String name;

        public DoSomethingThread(String name) {
            this.name = name;
        }

        // do something before gc
        @Override
        public void run() {
            System.out.println(name + " running DoSomething ...");
            System.out.println(10 / 0);
            System.out.println(name + " running DoSomething End ...");
        }
    }
}
