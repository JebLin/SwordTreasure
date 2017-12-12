package indi.sword.util.basic.designpattern.observer.weather.obj;


import indi.sword.util.basic.designpattern.observer.weather.intef.DisplayElement;
import indi.sword.util.basic.designpattern.observer.weather.intef.Observer;
import indi.sword.util.basic.designpattern.observer.weather.intef.Subject;

/**
 * 观察者：当前环境
 */
public class CurrentConditionsDisplay implements Observer, DisplayElement {

    private float temperature; // 温度
    private float humidity; // 湿度
    @SuppressWarnings("unused")
    private Subject weatherData = null;

    public CurrentConditionsDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + "% humidity");
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

}