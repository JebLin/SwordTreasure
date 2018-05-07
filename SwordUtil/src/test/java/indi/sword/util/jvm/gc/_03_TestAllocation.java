package indi.sword.util.jvm.gc;

/**
 * @Description
 *      VM 参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 * @Author jeb_lin
 * @Date Created in 12:00 PM 02/05/2018
 * @MODIFIED BY
 */
/*
    1、对象优先在 Eden中分配
    2、大对象直接进入老年代
 */
public class _03_TestAllocation {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {


//        testEdenAllocation_1();
        testOldAllocation_2_bigObj();

    }

    public static void testEdenAllocation_1(){
        byte[] allocation1,allocation2,allocation3,allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        /*
            给下面分配内存之前，出现一次 Monor GC 。因为eden已经放了6m了，只能放8m + 1m surivor from，
            你再加4m的话，会触发垃圾回收，然后由于survivor to也放不下，所以6m直接进入old区。
         */
        allocation4 = new byte[4 * _1MB];
    }


    //  -XX:PretenureSizeThreshold=314578 也就是3mb，这个参数不能像 -Xmx之类的参数一样直接写3MB
    public static void testOldAllocation_2_bigObj(){
        byte[] allocation;
        allocation = new byte[4 * _1MB]; // 直接分配在老年代
    }
}
