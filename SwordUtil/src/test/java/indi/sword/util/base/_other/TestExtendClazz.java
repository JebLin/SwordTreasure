package indi.sword.util.base._other;

import org.junit.Test;

/**
 * @Decription
 *
 *  Override是重写：方法名称、参数个数，类型，顺序，返回值类型都是必须和父类方法一致的。它的关系是父子关系
    Overload是重载:方法名称不变，其余的都是可以变更的。它的关系是同一个类，同一个方法名，不同的方法参数或返回值。
    备注：它们都是是Java多态性的不同表现

    ride ：骑到他爸爸头上，父子之间关系
    load：加载

    变量多态看左边，
    方法多态看右边，
    静态多态看左边。
 * @Author: rd_jianbin_lin
 * @Date : 2017/9/28 11:29
 */
public class TestExtendClazz {

    /**
     * @Decription 测试继承下的static方法
     *
     *
     *  "重写"只能适用于实例方法.不能用于静态方法.对于静态方法,只能隐藏（形式上被重写了，但是不符合的多态的特性），“重写”是用来实现多态性的，只有实例方法是可以实现多态，而静态方法无法实现多态。例如：
     *   Employee man = new Manager();
     *   man.test();
     *   实例化的这个对象中，声明的man变量是Employee类的，变量名存在栈中，而内存堆中为对象申请的空间却是按照Manager类来的，就是Employee类型的man变量的指针指向了一个Manager类的对象。如果对这个man调用方法，调用的是谁的？如果是非静态方法，编译时编译器以为是要调用Employee类的，可是实际运行时，解释器就从堆上开工了，实际上是从Manager类的那个对象上走的，所以调用的方法实际上是Manager类的方法。有这种结果关键在于man实际上指向了Manager类对象。现在用man来调用静态方法，实际上此时是Employee类在调用静态方法，Employee类本身肯定不会指向Manager类的对象，那么最终调用的是Employee类的方法。
     *   由此，只能说形式上静态方法的却可以被重写，实际上达不到重写的效果，从多态的角度可以认为子类实际上是写了一个新方法，从这个角度上说静态方法无法被重写。那么也就证明了重写和覆盖就是一回事。
     * @Author: rd_jianbin_lin
     * @Date : 2017/9/28 12:28
     */
    @Test
    public void test_static_method(){
        Person_ExtendsClazz person = new Person_ExtendsClazz();
        person.static_method(); // 基类_person

        Student_ExtendsClazz student = new Student_ExtendsClazz();
        student.static_method(); // 子类_student

        Person_ExtendsClazz person2 = new Student_ExtendsClazz();
        person2.static_method(); // 基类_person  WARNING !!! WARNING !!! WARNING !!!
    }

    /**
     * @Decription  测试继承下的构造方法
     * @Author: rd_jianbin_lin
     * @Date : 2017/9/28 12:28
     */
    @Test
    public void test_override(){
        Student_ExtendsClazz02 s1 = new Student_ExtendsClazz02();
        System.out.println();
        System.out.println("---------------------");
        Student_ExtendsClazz02 s2 = new Student_ExtendsClazz02(3);
    }

    /**
     * @Decription 测试继承下的 变量
     * java中变量不能重写
     * @Author: rd_jianbin_lin
     * @Date : 2017/9/28 12:35
     */
    @Test
    public void test_variable(){
        Person_ExtendsClazz person = new Student_ExtendsClazz();
        System.out.println(person.a);

    }
}

class Person_ExtendsClazz {
    public int a = 1;
    public static void static_method() {
        System.out.println("基类_person_static_method");
    }
}

class Student_ExtendsClazz extends Person_ExtendsClazz {
    public int a = 2;
    public static void static_method() {
        System.out.println("子类_student_static_method");
    }
}


class Person_ExtendsClazz02 {
    public Person_ExtendsClazz02(){
        method();
    }
    public void method(){
        System.out.print("1,");
    }
}

class Student_ExtendsClazz02 extends Person_ExtendsClazz02 {
    public Student_ExtendsClazz02(){
    }
    /*
        如果子类的构造方法中没有显式的调用父类构造方法，则系统默认调用父类无参数的构造方法。
        注意：如果子类的构造方法中既没有显式的调用父类构造方法，而父类中又没有默认无参的构造方法，则编译出错，
        所以，通常我们需要显式的用super(参数列表)，来调用父类有参数的构造函数。
      */
    public Student_ExtendsClazz02(int a){
//        super();
//        System.out.print(a);
    }
    public void method(){
        System.out.print("2,");
    }


}
