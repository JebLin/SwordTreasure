package indi.sword.util.basic.innerClass;

/**
 * @Decription
 * @Author: rd_jianbin_lin
 * @Date : 2017/12/8 20:16
 */
/*
    2.局部内部类
    局部内部类是定义在一个方法或者一个作用域里面的类，它和成员内部类的区别在于局部内部类的访问仅限于方法内或者该作用域内。
 */
public class _02_People {
    public static void main(String[] args) {
        People people = new Man().getWoman();
        people.getAge();
    }
}

class People{
    public People() {

    }
    public int getAge() {
        System.out.println(3);
        return 3;
    }
}

class Man{
    public static final int field = 1;
    public Man(){

    }

    /*
        方法名可以定义为 static ，方法内的变量不可以，是因为类加载的时候，不会去调用这个方法，
        但是你里面假如有静态变量，那么就必须要加载这个方法。所以矛盾了
      */
    public People getWoman(){
        int i = 5;
        // 局部内部类就像是方法里面的一个局部变量一样，是不能有public、protected、private以及static修饰符的。
        class Woman extends People{   //局部内部类，此作用域在getWoman()方法内,所以可以用到方法内的变量
            int age = 0;

            public int getAge() {
                // 局部内部类和匿名内部类只能访问局部final变量
//                i++;
                age = i + age;
                System.out.println(age);
                return age;
            }
        }
        return new Woman();
    }
}
