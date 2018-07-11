package indi.sword.util.guava.EventBus;

import com.google.common.eventbus.Subscribe;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 11:59 PM 10/07/2018
 * @MODIFIED BY
 */
public class MultiEventListener {

    @Subscribe
    public void listen(OrderEvent event){
        System.out.println("MultiEventListener receive msg: "+event.getMessage());
    }

    @Subscribe
    public void listen(String message){
        System.out.println("MultiEventListener receive msg: "+message);
    }
}
