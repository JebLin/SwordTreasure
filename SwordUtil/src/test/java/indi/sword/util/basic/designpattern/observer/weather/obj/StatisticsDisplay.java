package indi.sword.util.basic.designpattern.observer.weather.obj;

import indi.sword.util.basic.designpattern.observer.weather.intef.DisplayElement;
import indi.sword.util.basic.designpattern.observer.weather.intef.Observer;
import indi.sword.util.basic.designpattern.observer.weather.intef.Subject;

/**
 * 观察者：统计数据
 *
 * Blog : http://blog.csdn.net/lemon_tree12138
 * GitHub : https://github.com/William-Hai
 */
public class StatisticsDisplay implements Observer, DisplayElement {

    private float temperature; // 温度
    private float humidity; // 湿度
    @SuppressWarnings("unused")
    private Subject weatherData = null;

    public StatisticsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Statistics: " + temperature + "F degrees and " + humidity + "% humidity");
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }
}
