package indi.sword.util.base.innerClass;

/**
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/8 20:23
 */
/*
    3.匿名内部类
    匿名内部类应该是平时我们编写代码时用得最多的，在编写事件监听的代码时使用匿名内部类不但方便，而且使代码更加容易维护。
 */
public class _03_Thread {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }
}

/*
    使用匿名内部类能够在实现父类或者接口中的方法,同时产生一个相应的对象，但是前提是这个父类或者接口必须先存在才能这样使用。
 */
