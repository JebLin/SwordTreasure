package indi.sword.util.base.reflect;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author rd_jianbin_lin
 * @Date 19:27 2017/10/9
 */
public class Test_base {

    static Class<?> clazz = null;
    static Class<?> XClazz = null;

    static {

        try {
            clazz = Class.forName("indi.sword.util.base.reflect.BasePerson"); // 传入完整的“包.类”名称实例化Class对象
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
//            X x = new X(); //普通情况下的实例化方法
//            Class clazz = x.getClass();
            XClazz = Class.forName("indi.sword.util.base.reflect.X"); // 传入完整的“包.类”名称实例化Class对象
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 测试Class类的一些基本方法
     */
    @Test
    public void test_base() {
        System.out.println("clazz.getName() -> " + clazz.getName()); // 得到一个类完整的“包.类”名称 indi.sword.util.base.reflect.X
        System.out.println("clazz.getPackage() -> " + clazz.getPackage()); // 得到一个类的包
        System.out.println("clazz.getSuperclass() -> " + clazz.getSuperclass()); // 得到父类
        System.out.println("clazz.isArray() -> " + clazz.isArray()); // 是否是一个数组
    }

    @Test
    public void test_getInterfaces() {

        /**
         * 注意：仅仅这个object，不包括父类
         * Determines the interfaces implemented by the class or intef
         * represented by this object.
         */
        Class<?>[] XInterfaces = XClazz.getInterfaces();
        System.out.println("clazz.getInterfaces().length -> " + XInterfaces.length); // 得到一个类中所实现的全部接口
        String interfacesStr = Arrays.stream(XInterfaces).map(args -> args.getName()).collect(Collectors.joining(",","---","---"));
        System.out.println(interfacesStr);

        System.out.println("-----------------------------------");
        Class<?>[] personInterfaces = clazz.getInterfaces();
        System.out.println("clazz.getInterfaces().length -> " + personInterfaces.length); // 得到一个类中所实现的全部接口
        String personInterfacesStr = Arrays.stream(personInterfaces).map(args -> args.getName()).collect(Collectors.joining(",","---","---"));
        System.out.println(personInterfacesStr);
    }

    @Test
    public void test_Method() {
        Class<?> clazz = null;
        try {
            clazz =  Class.forName("indi.sword.util.base.reflect.BasePerson"); // 传入完整的“包.类”名称实例化Class对象
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /**
         * 注意：public methods.
         * getMethods()
         * Returns an array containing {@code Method} objects reflecting all the
         * public methods of the class or intef represented by this {@code
         * Class} object, including those declared by the class or intef and
         * those inherited from superclasses and superinterfaces.
         */
        Method[] method = clazz.getMethods(); //得到本类中的全部public方法，包括继承来的或者接口来的
        System.out.println("clazz.getMethods().length -> " + method.length);
        String methodsStr = Arrays.stream(method).map(methodArgs -> methodArgs.getName()).collect(Collectors.joining(",", "---", "---"));
        System.out.println("clazz.getMethods() -> " + methodsStr);

        System.out.println("---------------------------------");
        System.out.println("Object.class.getMethods().length -> " + Object.class.getMethods().length); // 拿出Object的方法
        Arrays.stream(Object.class.getMethods()).map(methodArgs -> methodArgs.getName()).forEach(name -> {
            System.out.print(name + ",");
        });

        System.out.println();
        System.out.println("---------------------------------");
        /**
         * 注意：excluding inherited methods
         * Returns an array containing {@code Method} objects reflecting all the
         * declared methods of the class or intef represented by this {@code
         * Class} object, including public, protected, default (package)
         * access, and private methods, but excluding inherited methods.
         */
        Method[] declaredMethods = clazz.getDeclaredMethods();//得到本类中的全部方法，public  private default protected都取出来，但是父类的不算
        System.out.println("clazz.getDeclaredMethods().length -> " + declaredMethods.length);
        String declaredMethodsStr = Arrays.stream(declaredMethods).map(methodArgs -> methodArgs.getName()).collect(Collectors.joining(",", "---", "---"));
        System.out.println("clazz.getDeclaredMethods() -> " + declaredMethodsStr);
    }

    @Test
    public void test_Constructors() {
        /**
         * 注意： 只列举出public的contructor
         * getConstructors()
         * Returns an array containing {@code Constructor} objects reflecting
         * all the public constructors of the class represented by this
         * {@code Class} object.  An array of length 0 is returned if the
         * class has no public constructors, or if the class is an array class, or
         * if the class reflects a primitive type or void.
         */
        Constructor<?>[] constructors = clazz.getConstructors();
        System.out.println("clazz.getConstructors().length -> " + constructors.length); // 得到本类中的全部public构造方法
        Arrays.stream(constructors).forEach(System.out::println);
        System.out.println("----------------------------------");

        Arrays.stream(constructors).map(constructor -> {
            int modifiers = constructor.getModifiers(); //得到权限符号 这里是 1 2 4 8 16 等等
            String modifierStr = Modifier.toString(modifiers); // 翻译成权限符号
            String name = constructor.getName(); //得到方法名
            Class<?>[] paramClazz = constructor.getParameterTypes(); //得到参数列表
            String paramClazzStr = Arrays.stream(paramClazz).map(Class::getName).collect(Collectors.joining(",")); //拼装参数列表

            Class<?>[] exceptionTypesClazz = constructor.getExceptionTypes(); //得到异常信息列表
            String exceptionTypesStr = Arrays.stream(exceptionTypesClazz).map(Class::getName).collect(Collectors.joining(",")); //得到异常列表

            String returnStr = "构造方法:" + modifierStr + " " + name + "(" + paramClazzStr + ")";
            return returnStr;
        }).forEach(System.out::println);
    }

    @Test
    public void test_getFields() {
        /**
         * 注意：excludes inherited fields.
         * getDeclaredFields()
         * Returns an array of {@code Field} objects reflecting all the fields
         * declared by the class or intef represented by this
         * {@code Class} object. This includes public, protected, default
         * (package) access, and private fields, but excludes inherited fields.
         */
        Field[] declaredFields = clazz.getDeclaredFields();
        System.out.println("clazz.getDeclaredFields().length -> " + declaredFields.length); // 得到本类中单独定义的全部属性,不包括继承来的
        Arrays.stream(declaredFields).forEach(System.out::println);

        System.out.println("------------------------");

        /**
         * 注意，public的field.
         * getFields()
         * Returns an array containing {@code Field} objects reflecting all
         * the accessible public fields of the class or intef represented by
         * this {@code Class} object.
         */
        Field[] fields = clazz.getFields();
        System.out.println("clazz.getFields().length -> " + fields.length); // 取得本类继承而来的全部public属性
        Arrays.stream(fields).forEach(System.out::println);
    }
}

interface interface001 {}

interface interface002 {}

class X implements interface001 ,interface002{
    public int x = 0;

    public X() {
    }

    public void helloX() {
        System.out.println("hello X ...");
    }
}

class Y extends X {
    public int y = 0;

    public void helloY() {
        System.out.println("hello Y ...");
    }
}

class BasePerson extends Y {
    private String name;
    private int age;

    private BasePerson(String name) {
    } // //private构造方法

    BasePerson(int age) {
    }

    ; //default 构造方法

    protected BasePerson(int age, String name) {
    } //protected构造方法

    public BasePerson() {
    }

    // java.lang.InstantiationException
    // 因为类中并没有存在无参构造方法，所以根本无法直接使用newInstance()方法进行实例化的。
    public BasePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // private方法
    private void privateMethod() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void hello() {
        System.out.println("hello person ...");
    }
}

