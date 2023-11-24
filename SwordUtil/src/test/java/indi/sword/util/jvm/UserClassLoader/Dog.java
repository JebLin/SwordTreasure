package indi.sword.util.jvm.UserClassLoader;

public class Dog{
    public Dog(){
        System.out.println("Dog is loaded by : " + this.getClass().getClassLoader());
    }
}