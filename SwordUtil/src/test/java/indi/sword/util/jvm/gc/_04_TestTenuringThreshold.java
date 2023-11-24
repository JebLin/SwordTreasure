package indi.sword.util.jvm.gc;

/**
 * @Description VM 参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * -XX:MaxTenuringThreshold=1 -XX:+PrintTenuringDistribution
 * <p>
 * 注意jdk版本，更换jdk为1.6.0_24
 * @Author jeb_lin
 * @Date Created in 11:02 AM 02/05/2018
 * @MODIFIED BY
 */
public class _04_TestTenuringThreshold {

    private static final int _1MB = 1024 * 1024;


    public static void main(String[] args) {
//        testTenuringThreshold01();
        testTenuringThreshold02();
    }

    // 这个代码会发生两次Minor GC:
    public static void testTenuringThreshold01() {
        byte[] allocation1, allocation2, allocation3;
        allocation1 = new byte[_1MB / 4];

        // 什么时候进入老年代取决于 XX:MaxTenuringThreshold 设置
        allocation2 = new byte[4 * _1MB];

        // 第一次GC:allocation3第一次创建byte数组时,因为eden只有8M左右,没有内存再分配给新对象,所有进行了第一次GC-----allocation2的索引对象进入了老年代.
        allocation3 = new byte[4 * _1MB];

        // 第二次:在allocation3 = null;执行以后,之前创建的对象已经"死亡",触发第二次GC回收死亡对象.
        // 与此同时,如果MaxTenuringThreshold的参数为1的话,在survivor中的allocation1的索引对象,
        // 因为age已经为1,所以会进入老年代.而当参数MaxTenuringThreshold大于1时,survivor中的allocation1的索引对象age+=1,不会进入老年代;
        allocation3 = null;
        allocation3 = new byte[4 * _1MB];

        /**
         *
         * XX:MaxTenuringThreshold =8
         [GC [DefNew
         Desired survivor size 524288 bytes, new threshold 8 (max 8)
         - age   1:     418144 bytes,     418144 total
         : 4695K->408K(9216K), 0.0036693 secs] 4695K->4504K(19456K), 0.0036983 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         [GC [DefNew
         Desired survivor size 524288 bytes, new threshold 8 (max 8)
         - age   1:        136 bytes,        136 total
         - age   2:     417936 bytes,     418072 total
         : 4668K->408K(9216K), 0.0010034 secs] 8764K->4504K(19456K), 0.0010296 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         Heap
         def new generation   total 9216K, used 4668K [0x32750000, 0x33150000, 0x33150000)
         eden space 8192K,  52% used [0x32750000, 0x32b78fe0, 0x32f50000)
         from space 1024K,  39% used [0x32f50000, 0x32fb6118, 0x33050000)
         to   space 1024K,   0% used [0x33050000, 0x33050000, 0x33150000)
         tenured generation   total 10240K, used 4096K [0x33150000, 0x33b50000, 0x33b50000)
         the space 10240K,  40% used [0x33150000, 0x33550010, 0x33550200, 0x33b50000)
         compacting perm gen  total 12288K, used 377K [0x33b50000, 0x34750000, 0x37b50000)
         the space 12288K,   3% used [0x33b50000, 0x33bae5b8, 0x33bae600, 0x34750000)
         ro space 10240K,  55% used [0x37b50000, 0x380d1140, 0x380d1200, 0x38550000)
         rw space 12288K,  55% used [0x38550000, 0x38bf44c8, 0x38bf4600, 0x39150000)


         */

        /**
         *
         * XX:MaxTenuringThreshold=1
         [GC [DefNew
         Desired survivor size 524288 bytes, new threshold 1 (max 1)
         - age   1:     418144 bytes,     418144 total
         : 4695K->408K(9216K), 0.0054252 secs] 4695K->4504K(19456K), 0.0054708 secs] [Times: user=0.02 sys=0.00, real=0.01 secs]
         [GC [DefNew
         Desired survivor size 524288 bytes, new threshold 1 (max 1)
         - age   1:        136 bytes,        136 total
         : 4668K->0K(9216K), 0.0013601 secs] 8764K->4504K(19456K), 0.0013867 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         Heap
         def new generation   total 9216K, used 4260K [0x32750000, 0x33150000, 0x33150000)
         eden space 8192K,  52% used [0x32750000, 0x32b78fe0, 0x32f50000)
         from space 1024K,   0% used [0x32f50000, 0x32f50088, 0x33050000)
         to   space 1024K,   0% used [0x33050000, 0x33050000, 0x33150000)
         tenured generation   total 10240K, used 4504K [0x33150000, 0x33b50000, 0x33b50000)
         the space 10240K,  43% used [0x33150000, 0x335b60a0, 0x335b6200, 0x33b50000)
         compacting perm gen  total 12288K, used 377K [0x33b50000, 0x34750000, 0x37b50000)
         the space 12288K,   3% used [0x33b50000, 0x33bae5c0, 0x33bae600, 0x34750000)
         ro space 10240K,  55% used [0x37b50000, 0x380d1140, 0x380d1200, 0x38550000)
         rw space 12288K,  55% used [0x38550000, 0x38bf44c8, 0x38bf4600, 0x39150000)

         解释

         - age 1: 136 bytes, 136 total- age 2: 417936 bytes, 418072 total

         - age 2: 417936 bytes, 418072 total

         表示 年龄为1 的对象有总共多大，以此类似。total 从上往下加。

         */
    }

    /**
     * @Description
     *      动态对象年龄判定
     * @Author jeb_lin
     * @Date 11:40 AM 02/05/2018
     * @MODIFIED BY
     */
    /*
        为了更好地适应不同程序的内存状况，虚拟机并不是永远地要求对象的年龄必须大袋NaxTenuringThreshold才能晋升老年代，
        如果在Survivor空间中，相同年龄所有对象大小的总和不小于Survivor空间的一半，年龄大于或等于该年龄的对象就可以直接进入老年代。
        无需等到MaxTenuringThreshold中要求的年龄。
     */
    public static void testTenuringThreshold02(){
        byte[] allocation1,allocation2,allocation3,allocation4;
        allocation1 = new byte[_1MB / 4];
        // allocation1 + allocation2 = _1MB / 2,大于survivor空间的一半。
        allocation2 = new byte[_1MB / 4];

        allocation3 = new byte[4 * _1MB];
        allocation4 = new byte[4 * _1MB];
        allocation4 = null;
        allocation4 = new byte[4 * _1MB];
    }

}
