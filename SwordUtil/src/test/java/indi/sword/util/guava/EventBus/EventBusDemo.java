package indi.sword.util.guava.EventBus;

import com.google.common.eventbus.EventBus;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 11:57 PM 10/07/2018
 * @MODIFIED BY
 */
public class EventBusDemo {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus("JebLin");

        eventBus.register(new EventListener());
        eventBus.register(new MultiEventListener());
        eventBus.register(new DeadEventListener());

        eventBus.post(new OrderEvent("hello "));
        eventBus.post(new OrderEvent("baby "));
        eventBus.post(new OrderEvent("! "));
    }
}
