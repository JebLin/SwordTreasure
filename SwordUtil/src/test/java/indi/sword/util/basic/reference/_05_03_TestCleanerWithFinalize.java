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
public class _05_03_TestCleanerWithFinalize {
    public static void main(String[] args) throws Exception {
        int index = 0;
        while (true) {
            Thread.sleep(1000);
            // 提醒 GC 去进行垃圾收集了
            System.gc();

            // 该对象不断重新指向其他地方，那么原先指针指向的对象的就属于需要回收的数据
            DemoObject obj = new DemoObject("demo" + index++);
            Cleaner.create(obj, new CleanerTask("thread_" + index++));
        }
    }

    @Data
    @AllArgsConstructor
    @ToString
    static class DemoObject {
        private String name;

        @Override
        protected void finalize() throws Throwable {
            System.out.println("finalize running DoSomething ..." + name);
        }
    }

    static class CleanerTask implements Runnable {
        private String name;

        public CleanerTask(String name) {
            this.name = name;
        }

        // do something before gc
        @Override
        public void run() {
            System.out.println("CleanerTask running DoSomething ..." + name );
        }
    }
}
