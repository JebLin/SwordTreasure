package indi.sword.util.base.reflect;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Description 测试Class类
 * @Author rd_jianbin_lin
 * @Date 21:35 2017/10/8
 */
public class ClazzMainMethod {
    public static void main(String[] args){
//        X x = new X();
//        Class clazz = x.getClass();
        Class<?> clazz = null;
        try {
            clazz = Class.forName("indi.sword.util.base.reflect.Y"); // 传入完整的“包.类”名称实例化Class对象
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("clazz.getName() -> " + clazz.getName()); // 得到一个类完整的“包.类”名称 indi.sword.util.base.reflect.X
        System.out.println("clazz.getConstructors().length -> " + clazz.getConstructors().length); // 得到本类中的全部构造方法
        System.out.println("clazz.getDeclaredFields().length -> " + clazz.getDeclaredFields().length); // 得到本类中单独定义的全部属性
        System.out.println("clazz.getFields().length -> " + clazz.getFields().length); // 取得本类继承而来的全部属性
        System.out.println("clazz.getMethods().length -> " + clazz.getMethods().length) ; //得到本类中的全部方法
        String methods = Arrays.stream(clazz.getMethods()).map(method -> method.getName()).collect(Collectors.joining(",","---","---"));
        System.out.println("clazz.getMethods() -> " + methods);
        System.out.println("--------------------------------");
        System.out.println("clazz.getInterfaces().length -> " + clazz.getInterfaces().length); // 得到一个类中所实现的全部接口
        System.out.println("clazz.getPackage() -> " + clazz.getPackage()); // 得到一个类的包
        System.out.println("clazz.getSuperclass() -> " + clazz.getSuperclass()); // 得到父类
        System.out.println("clazz.isArray() -> " + clazz.isArray()); // 是否是一个数组


        System.out.println("---------------------------------");
        System.out.println("Object.class.getMethods().length -> " + Object.class.getMethods().length);
        Arrays.stream(Object.class.getMethods()).map(method -> method.getName()).forEach(name -> {
            System.out.print(name + ",");
        });
    }
}

class X{
    int x = 0;
    public X(){}
    public void hello(){}
}

class Y extends X{

}