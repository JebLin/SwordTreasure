package indi.sword.util.basic.reflect.proxy;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 19:52 2017/10/8
 */
//真实角色：实现了Subject的request()方法
public class RealSubject implements Subject{

    public void request(){
        System.out.println("From real subject.");
    }
}
