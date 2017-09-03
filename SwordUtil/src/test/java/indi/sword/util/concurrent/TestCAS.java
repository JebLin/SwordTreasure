package indi.sword.util.concurrent;

/**
 * 
 * @Descrption 模拟 CAS 算法
 * @author rd_jianbin_lin
 * @Date Sep 2, 2017 7:31:43 PM
 */
public class TestCAS {

    public static void main(String[] args) {
        final CAS cas = new CAS();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    int expectedValue = cas.get();
                    boolean b = cas.compareAndSet(expectedValue, (int)(Math.random() * 101));
                    System.out.println(b);
                }
            }).start();
        }

    }

}

class CAS{
	
    private int value;

    //获取内存值
    public synchronized int get(){
        return value;
    }

    //比较
    public synchronized int compareAndSwap(int expectedValue, int newValue){
        int oldValue = value;

        if(oldValue == expectedValue){
            this.value = newValue;
        }

        return this.value;
    }

    //设置
    public synchronized boolean compareAndSet(int expectedValue, int newValue){
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}