package indi.sword.util.base.designpattern.observer.weather.intef;

/**
 * 被观察的对象接口
 */
public interface Subject {

    public void registerObserver(Observer o);

    public void removeObserver(Observer o);

    public void notifyObservers();
}
