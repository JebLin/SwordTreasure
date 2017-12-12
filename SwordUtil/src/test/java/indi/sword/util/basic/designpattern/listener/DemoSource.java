package indi.sword.util.basic.designpattern.listener;

import java.util.Enumeration;
import java.util.Vector;
/**
 * 首要定义事件源对象（事件源相当于单击按钮事件当中的按钮对象、属于被监听者）：
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/11/26 12:48
 */
public class DemoSource {
    private Vector repository = new Vector();//监听自己的监听器队列
    public DemoSource(){}
    public void addDemoListener(DemoListenerInterface dl) {
        repository.addElement(dl);
    }
    public void notifyDemoEvent() {//通知所有的监听器
        Enumeration enumeration = repository.elements();
        while(enumeration.hasMoreElements()) {
            DemoListenerInterface dl = (DemoListenerInterface)enumeration.nextElement();
            dl.handleEvent(new DemoEvent(this));
        }
    }
}