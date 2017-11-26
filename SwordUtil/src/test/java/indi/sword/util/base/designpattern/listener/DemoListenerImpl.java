package indi.sword.util.base.designpattern.listener;

/**
 * 监听器实现类
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/11/26 12:49
 */
public class DemoListenerImpl implements DemoListenerInterface {
    public void handleEvent(DemoEvent de) {
        System.out.println("Inside listener1...");
        de.say();//回调
    }
}
