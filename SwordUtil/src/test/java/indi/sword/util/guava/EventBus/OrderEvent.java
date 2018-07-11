package indi.sword.util.guava.EventBus;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 11:55 PM 10/07/2018
 * @MODIFIED BY
 */
//Guava 发布-订阅模式中传递的事件,是一个普通的POJO类
public class OrderEvent {  //事件
    private String message;

    public OrderEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}