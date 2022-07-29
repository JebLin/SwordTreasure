package indi.sword.util.chaos;

/**
 * @author jeb_lin
 * 8:46 PM 2020/1/2
 */
public class TT {

    private static ThreadLocal<String> threadId;

    public TT(){
        handle();
    }


    public void handle(){
        threadId = ThreadLocal.withInitial(() -> Thread.currentThread().getName());
    }

    public static String getThreadId(){

        return Thread.currentThread().getName() + ",threadId is " + threadId.get();
    }
}
