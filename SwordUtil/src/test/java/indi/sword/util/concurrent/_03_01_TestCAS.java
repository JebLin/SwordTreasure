package indi.sword.util.concurrent;

/**
 * 
 * @Descrption 模拟 CAS 算法
 * @author rd_jianbin_lin
 * @Date Sep 2, 2017 7:31:43 PM
 */
public class _03_01_TestCAS {

    public static void main(String[] args) {
        final CAS cas = new CAS();

        for (int i = 0; i < 10; i++) {
//            try {
//                Thread.sleep(300);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int expectedValue = cas.get();
                    int newValue = (int)(Math.random() * 101);
                    System.out.println("expectedValue = " + expectedValue +  " ,newValue = " + newValue );
//                    boolean b = cas.compareAndSet(expectedValue, newValue);
                    while (!cas.compareAndSet(expectedValue, newValue)){ // 设置不成功就继续去update该位置的值，也就是取最新值
                        expectedValue = cas.get();
                    }
                }
            },"Thread-"+i).start();
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
            System.out.println(Thread.currentThread().getName() + " set value " + oldValue + " -> " + newValue);
            this.value = newValue;
        }

        return this.value;
    }

    //设置
    public boolean compareAndSet(int expectedValue, int newValue){ // 设置成功就返回true
        return newValue == compareAndSwap(expectedValue, newValue);
    }
}