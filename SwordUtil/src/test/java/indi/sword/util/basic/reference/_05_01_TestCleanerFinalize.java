package indi.sword.util.basic.reference;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import sun.misc.Cleaner;

import java.util.List;

/**
 * @author jeb_lin
 * 3:36 PM 2019/9/26
 */
public class _05_01_TestCleanerFinalize {
    public static void main(String[] args) throws Exception {
        int index = 0;
        while (true) {
            Thread.sleep(1000);
            // 提醒 GC 去进行垃圾收集了
            System.gc();

            // 该对象不断重新指向其他地方，那么原先指针指向的对象的就属于需要回收的数据
            DemoObject obj = new DemoObject("demo" + index++);
        }
    }

    @Data
    @AllArgsConstructor
    @ToString
    static class DemoObject {
        private String name;
//        private static final List<Object> objectList = Lists.newArrayList();

        @Override
        protected void finalize() throws Throwable {
            System.out.println(name + " running DoSomething ...");
//            objectList.add(this);
//            System.out.println(objectList.size());
//            System.out.println(objectList);
        }
    }
}
