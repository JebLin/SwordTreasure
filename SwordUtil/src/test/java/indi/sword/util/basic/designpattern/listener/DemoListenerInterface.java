package indi.sword.util.basic.designpattern.listener;
/**
 * 定义我们的事件侦听器接口
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/11/26 12:45
 */

import java.util.EventListener;

public interface DemoListenerInterface extends EventListener {
    //EventListener是所有事件侦听器接口必须扩展的标记接口、因为它是无内容的标记接口、
    //所以事件处理方法由我们自己声明如下：
    public void handleEvent(DemoEvent dm);
}
