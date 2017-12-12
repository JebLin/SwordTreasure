package indi.sword.util.basic.designpattern.listener;

/**
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/11/26 12:51
 */
/*
    总结
    监听器模式是观察者模式的另一种形态，同样基于事件驱动模型。监听器模式更加灵活，可以对不同事件作出相应。
    但也是付出了系统的复杂性作为代价的，因为我们要为每一个事件源定制一个监听器以及事件，这会增加系统的负担。
 */
public class TestDemo {
    DemoSource ds;

    public TestDemo(){
        try{
            ds = new DemoSource();
            //将监听器在事件源对象中登记：
            DemoListenerImpl listener1 = new DemoListenerImpl();
            ds.addDemoListener(listener1);
            ds.addDemoListener(new DemoListenerInterface() {
                public void handleEvent(DemoEvent event) {
                    System.out.println("Method come from 匿名类...");
                }
            });
            ds.notifyDemoEvent();//触发事件、通知监听器
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void main(String args[]) {
        new TestDemo();
    }
}
