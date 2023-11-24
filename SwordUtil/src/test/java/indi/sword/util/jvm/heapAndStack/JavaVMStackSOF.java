package indi.sword.util.jvm.heapAndStack;

/**
 * @Description
 *   VM args:   -Xss128k
 * @Author jeb_lin
 * @Date Created in 3:39 PM 01/05/2018
 * @MODIFIED BY
 */
/*
    stack over flow error
    关于虚拟机栈和本地方法栈，在java虚拟机规范中描述了两种异常：
        1、线程请求的栈深度大于虚拟机能提供的栈深度，将抛出StackOverFlowError 异常。
        2、如果虚拟机在扩展栈的过程中无法申请到如果的内存空间，将抛出 OutOfMemoryError 异常。
*/
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak(){
            stackLength++;
            stackLeak();
    }

    public static void main(String[] args) {
        JavaVMStackSOF oom = new JavaVMStackSOF();

        try {
            oom.stackLeak();
        } catch (Exception e) {
            System.out.println("stack length -> " + oom.stackLength);
            throw e;
        }
    }
}
