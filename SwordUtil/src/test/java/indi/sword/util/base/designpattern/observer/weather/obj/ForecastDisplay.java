package indi.sword.util.base.designpattern.observer.weather.obj;

import indi.sword.util.base.designpattern.observer.weather.intef.DisplayElement;
import indi.sword.util.base.designpattern.observer.weather.intef.Observer;
import indi.sword.util.base.designpattern.observer.weather.intef.Subject;

/**
 * 观察者：森林环境
 */
public class ForecastDisplay implements Observer, DisplayElement {

    private float temperature; // 温度
    private float humidity; // 湿度
    @SuppressWarnings("unused")
    private Subject weatherData = null;

    public ForecastDisplay(Subject weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    @Override
    public void display() {
        System.out.println("Forecast: " + temperature + "F degrees and " + humidity + "% humidity");
    }

    @Override
    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();
    }

}
