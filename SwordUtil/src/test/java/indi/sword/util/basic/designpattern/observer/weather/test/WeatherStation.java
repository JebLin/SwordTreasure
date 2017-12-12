package indi.sword.util.basic.designpattern.observer.weather.test;

import indi.sword.util.basic.designpattern.observer.weather.obj.CurrentConditionsDisplay;
import indi.sword.util.basic.designpattern.observer.weather.obj.ForecastDisplay;
import indi.sword.util.basic.designpattern.observer.weather.obj.StatisticsDisplay;
import indi.sword.util.basic.designpattern.observer.weather.sub.WeatherData;

public class WeatherStation {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

        weatherData.setMeasurements(80, 65, 30.4f);
        System.out.println();
        weatherData.setMeasurements(82, 70, 29.2f);
        System.out.println();
        weatherData.setMeasurements(78, 90, 29.2f);
        System.out.println();
    }

}