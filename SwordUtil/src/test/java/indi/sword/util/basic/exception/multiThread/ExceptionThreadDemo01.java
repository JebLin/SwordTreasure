package indi.sword.util.basic.exception.multiThread;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 17:54 2018/2/4
 * @Modified By
 */
class ExceptionThreadDemo01 implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
        throw new RuntimeException("自定义的一个RuntimeException...");
    }

}
