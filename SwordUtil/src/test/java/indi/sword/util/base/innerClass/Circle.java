package indi.sword.util.base.innerClass;

/**
 * @Description:
 * @Author: rd_jianbin_lin
 * @Date:12:33 2017/12/8
 */
/*
    1.成员内部类
    成员内部类是最普通的内部类，它的定义为位于另一个类的内部.


 */
/*
    当成员内部类拥有和外部类同名的成员变量或者方法时，会发生隐藏现象，
    即默认情况下访问的是成员内部类的成员。如果要访问外部类的同名成员，需要以下面的形式进行访问：
        - 外部类.this.成员变量
        - 外部类.this.成员方法
 */
public class Circle {
    private double radius = 0;
    public static int count = 1;
    private double field01 = 2;

    public Circle(double radius) {
        this.radius = radius;
        /*
            虽然成员内部类可以无条件地访问外部类的成员，而外部类想访问成员内部类的成员却不是这么随心所欲了。
            在外部类中如果要访问成员内部类的成员，必须先创建一个成员内部类的对象，再通过指向这个对象的引用来访问：
         */
        getInnerInstance().drawshape();   //必须先创建成员内部类的对象，再进行访问
    }

    /*
        类Draw像是类Circle的一个成员，Circle称为外部类。
        成员内部类可以无条件访问外部类的所有成员属性和成员方法（包括private成员和静态成员）。
     */
    class Draw {     //内部类
        double field01 = 3;
        public void drawshape() {
            System.out.println(radius);  //外部类的private成员
            System.out.println(count);   //外部类的静态成员
            System.out.println(field01);   //当成员内部类拥有和外部类同名的成员变量或者方法时，会发生隐藏现象，即默认情况下访问的是成员内部类的成员。
            System.out.println(Circle.this.field01);   //外部类.this.成员变量
        }
    }

    private Draw getInnerInstance() {
        return new Draw();
    }

    public static void main(String[] args)  {
        //第一种方式：
        Circle outter = new Circle(3);
        System.out.println("--------------");
        Circle.Draw inner = outter.new Draw();  //必须通过Outter对象来创建

        //第二种方式：
        Circle.Draw inner1 = outter.getInnerInstance();
    }
}
