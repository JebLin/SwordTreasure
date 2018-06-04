package indi.sword.util.lombok;

import lombok.Getter;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 5:12 PM 04/06/2018
 * @MODIFIED BY
 */
public class TestGetterLazyExample {
    @Getter(lazy=true) private final double[] cached = expensive();

    private double[] expensive() {
        double[] result = new double[1000000];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.asin(i);
        }
        return result;
    }


}
/*

public class GetterLazyExample2 {
    private final java.util.concurrent.AtomicReference<java.lang.Object> cached = new java.util.concurrent.AtomicReference<java.lang.Object>();

    public double[] getCached() {
        java.lang.Object value = this.cached.get();
        if (value == null) {
            synchronized(this.cached) {
                value = this.cached.get();
                if (value == null) {
                    final double[] actualValue = expensive();
                    value = actualValue == null ? this.cached : actualValue;
                    this.cached.set(value);
                }
            }
        }
        return (double[])(value == this.cached ? null : value);
    }

    private double[] expensive() {
        double[] result = new double[1000000];
        for (int i = 0; i < result.length; i++) {
            result[i] = Math.asin(i);
        }
        return result;
    }
}*/
