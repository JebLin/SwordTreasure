package indi.sword.util.basic.collection;

import java.util.Random;

/**
 * @Decription 测试hashMap的hash方法
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/5 20:40
 */
/*
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
 */
/*
    把一个数右移16位即丢弃低16为，就是任何小于216的数，右移16后结果都为0（2的16次方再右移刚好就是1）。
    任何一个数，与0按位异或的结果都是这个数本身（很好验证）。

    所以这个hash()函数对于非null的hash值，仅在其大于等于2的16次方 的时候才会重新调整其值。
    但是调整后又什么好处呢？
        在寻找桶位的时候，这个hash值为与上table的zise-1，初始为16，我们就拿16来举例.
        以为算法是hashValue & size - 1 ,此时size-1=15的二进制为 1 1 1 1 ，也就是任意类似16进制0x?0（二进制最后四位为0000）的hash值，
        都会被存储到位置为0的桶位上，一个桶中的元素太多，就一定会降低其性能，但是我们来看看这样的hash值经过上面的函数处理过后的结果：下面例子
 */
public class TestHashCodeMethod {

    public static void main(String args[]) throws Exception {
        final int max = Integer.MAX_VALUE >>> 4 ;
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 20; i++) {
            int hash = random.nextInt(max) << 4;
            int betterHash = hash ^ (hash >>> 16);
            System.out.print(toBinaryString(hash));
            System.out.println("-->" + toBinaryString(betterHash));
        }
    }

    //将整数转换为二进制字符串，高位补0
    final static char[] digits = {'0', '1'};

    static String toBinaryString(int i) {
        char[] buf = new char[32];
        int pos = 32;
        int mask = 1;
        do {
            buf[--pos] = digits[i & mask];
            i >>>= 1;
        } while (pos > 0);
        return new String(buf, pos, 32);
    }
}
/*
    是不是发现情况都发生了好转，原来一大批会被放到“0”桶位的hash值，现在几乎都被更佳合理的分配到了其他桶位。
    我们知道hashMap中的桶位都是以oldCap<<1（即原容量*2）来增长的，所以最终这个hash值要存放的时候，都是跟一连串二进制的“1"作与运算的，
    而容量定义为int类型，java中int类型为4字节，即32位，但是Integer.MAX为0x7fffffff，也就是231 - 1 那么大（因为最高位被用作符号位），
    而取16算是一种折衷的办法。而另一个原因，也许是跟对象本身的hash值（当然也为int）有关。
    那么这个方法就介绍这么多了，近期准备将HashMap整个源码解读一下，并分享出来，并在最终整体介绍一下Java的容器体系。
 */
/*
    JDK8之前的方法是：
    h ^= (h >>> 20) ^ (h >>> 12);
    return h ^ (h >>> 7) ^ (h >>> 4);
 */
