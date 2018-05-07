package indi.sword.util.jvm.gc;

import sun.jvmstat.perfdata.monitor.PerfStringVariableMonitor;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 12:28 PM 02/05/2018
 * @MODIFIED BY
 */
/*
    在发送minor gc之前，虚拟机会首先检查老年代最大可连续空间是否大于新生代所有对象总和，如果这个条件成立，可以确保这次minor gc是安全的，
如果不成立，虚拟机会查看HandlePromotionFailure设置值是否允许担保失败。如果允许，那么会继续检查老年代最大可连续空间是否大于历次晋升到老年代对象
的评价大小，如果大于，将尝试一次minor gc，尽管这次minor gc是有风险的；如果小于，或者HandlePromotionFailure设置不允许冒险，那么这时也要改为一次Full gc。
 在jdk1.6 update24之后，HandlePromotionFailure参数不会影响虚拟机空间分配担保策略，虚拟机改为，只要老年代最大连续空间大于新生代对象总和或者大于历次晋升平均大小，都将进行minor gc，否则将进行Full gc。
 */
public class _05_TestHandlePromotion {

    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        testHandlePromotion();
    }

    /**
     * VM参数：-Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:-HandlePromotionFailure
     */
    @SuppressWarnings("unused")
    public static void testHandlePromotion() {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6, allocation7;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation1 = null;
        allocation4 = new byte[2 * _1MB];
        allocation5 = new byte[2 * _1MB];
        allocation6 = new byte[2 * _1MB];
        allocation4 = null;
        allocation5 = null;
        allocation6 = null;
        allocation7 = new byte[2 * _1MB];

        /**
         *

         java.version = 1.6.0_37


         [GC [DefNew: 6487K->152K(9216K), 0.0040346 secs] 6487K->4248K(19456K), 0.0040639 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         [GC [DefNew: 6546K->152K(9216K), 0.0004896 secs] 10642K->4248K(19456K), 0.0005141 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         Heap
         def new generation   total 9216K, used 2364K [0x32750000, 0x33150000, 0x33150000)
         eden space 8192K,  27% used [0x32750000, 0x32978fe0, 0x32f50000)
         from space 1024K,  14% used [0x32f50000, 0x32f76108, 0x33050000)
         to   space 1024K,   0% used [0x33050000, 0x33050000, 0x33150000)
         tenured generation   total 10240K, used 4096K [0x33150000, 0x33b50000, 0x33b50000)
         the space 10240K,  40% used [0x33150000, 0x33550020, 0x33550200, 0x33b50000)
         compacting perm gen  total 12288K, used 377K [0x33b50000, 0x34750000, 0x37b50000)
         the space 12288K,   3% used [0x33b50000, 0x33bae758, 0x33bae800, 0x34750000)
         ro space 10240K,  55% used [0x37b50000, 0x380d1140, 0x380d1200, 0x38550000)
         rw space 12288K,  55% used [0x38550000, 0x38bf44c8, 0x38bf4600, 0x39150000)
         Warning: The flag -HandlePromotionFailure has been EOL'd as of 6.0_24 and will be ignored


         */
    }

}

