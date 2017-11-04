package indi.sword.util.jvm.UserClassLoader;

public class Sample{
    public int v1 = 1;
    public Sample(){
        System.out.println("Sample is loaded by : " + this.getClass().getClassLoader().toString());
        new Dog();
    }
}