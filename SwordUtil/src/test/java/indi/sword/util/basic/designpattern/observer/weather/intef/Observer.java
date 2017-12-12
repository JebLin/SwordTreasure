package indi.sword.util.basic.designpattern.observer.weather.intef;

/**
 * 观察者对象接口
 */
public interface Observer {

    public void update(float temperature, float humidity, float pressure);
}