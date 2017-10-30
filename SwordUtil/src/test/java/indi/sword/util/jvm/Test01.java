package indi.sword.util.jvm;

public class Test01{
    public static void main(String[] args) {
        System.out.println(A.a);
        System.out.println(A.b);
    }
}

class A{
    public static A Singleton = new A();
    public static int a = 0;
    public static int b;
    //	public static A Singleton = new A();    //顺序不同将会导致结果不同
    private A(){
        a++;
        b++;
    }
}

