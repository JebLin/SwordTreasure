package indi.sword.util.concurrent;

/**
 * 通常将 CAS 用于同步的方式是从地址V读取值A，执行多步计算获得新值B，
 * 然后用CAS将V的值从 A改为B，如果 V处的值尚未同时更改，则CAS操作成功
 * 
 * CAS有效说明了“我认为位置 V应该包含值A了；如果包含该值，则将B放到这个位置；
 * 否则，不要更改该位置，只告诉我这个位置现在的值即可；
 * 
 * eg. SVN提交的时候，如果 位置V 是你预想的值，也就是在你之前没人改过的话，那么就可以 commit
 * 如果被改过了，告诉我那个位置，我先 update 再去commit
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Aug 30, 2017 10:35:39 AM
 */
public class CommonCAS {
    private int value;
    public synchronized int getValue() {
        return value;
    }
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        if (value == expectedValue) {
            value = newValue;
        }
        return value;
    }
}
