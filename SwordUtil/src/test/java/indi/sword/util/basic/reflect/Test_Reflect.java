package indi.sword.util.basic.reflect;

import org.junit.Test;

import java.lang.reflect.*;

/**
 * @Description 测试Class类
 * @Author rd_jianbin_lin
 * @Date 22:31 2017/10/8
 */
public class Test_Reflect {

    /**
     * show the difference about generating Object with three way.
     *
     * @Description
     * @Author rd_jianbin_lin
     * @Date 23:26 2017/10/8
     */
    @Test
    public void test_generateObject(){
/*
        使用forName()方法更加具备灵活性
        除了forName()方法外，其他的两种：“对象.getClass()”、“类.class”都需要导入一个明确的类，
        如果一个类操作不明确时，使用起来可能会受到一些限制。但是forName()传入时只需要以字符串的方式传入即可。
 */
        Class<?> c1 = null;
        Class<?> c2 = null;
        Class<?> c3 = null;
        try {
            c1 = Class.forName("indi.sword.util.basic.reflect.X"); //推荐使用
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        c2 = new X().getClass(); // 通过Object类中的方法实例
        c3 = X.class;   //通过类.class实例化
        System.out.println("类名称：" + c1.getName());
        System.out.println("类名称：" + c2.getName());
        System.out.println("类名称：" + c3.getName());
    }

    static Class<?> person_Clazz = null;
    static{
        try {
            person_Clazz = Class.forName("indi.sword.util.basic.reflect.Person");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     *   Class.forName()方法实例化Class对象之后，
         直接调用newInstance()方法就可以根据传入的完整“包.类”名称方式进行对象的实例化操作，
         完全替代了之前使用的关键字new的操作方式。被实例化对象的类中必须存在无参构造方法。
     *
     * @Description
     * @Author rd_jianbin_lin
     * @Date 23:24 2017/10/8
     */
    @Test
    public void test_newInstance(){
        try {
            Person person = (Person) person_Clazz.newInstance(); // 报错
            System.out.println(person);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_newInstance_Constructor(){
        try {
            Constructor<?>[] constructors = person_Clazz.getConstructors();
            // 调用有参构造实例化对象（补救Person类 不存在无参构造函数的错误）
            Person person = (Person) constructors[1].newInstance("Jeb",25);// 下标为0表示第一个构造方法
            System.out.println(person);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_MethodInvoke(){
        try {
            Method method = person_Clazz.getMethod("hello"); // 此方法没有参数
            method.invoke(person_Clazz.newInstance()); //调用方法，必须传递对象实例
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Method method2 = person_Clazz.getMethod("hello",String.class,int.class); //此方法需要传递两个参数
            method2.invoke(person_Clazz.newInstance(),"Jeb",24);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Method method2 = person_Clazz.getMethod("sayHello",String.class,int.class); //此方法需要传递两个参数
            String returnStr = (String) method2.invoke(person_Clazz.newInstance(),"Jeb",24); // 这个地方有返回值
            System.out.println("returnStr == " + returnStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 类中的属性必须封装，封装之后的属性要通过setter以及getter方法进行设置与取得。
     *
     * @Description
     * @Author rd_jianbin_lin
     * @Date 7:24 2017/10/10
     */
    @Test
    public void test_InvokeSetGetDemo(){
        Object obj = null;
        try {
            obj = person_Clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        String name = "Jeb";
        int age = 24;
        setter(obj,"name",name,String.class);
        setter(obj,"age",age,int.class);

        String name_getter = (String)getter(obj,"name");
        int age_getter = (int)getter(obj,"age");
        System.out.println(name_getter + " - " + age_getter);
    }

    private Object getter(Object obj, String attribute) {

        try {
            Method method = obj.getClass().getMethod("get" + initAttr(attribute));
            Object object = method.invoke(obj);
            return object;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setter(Object obj, String attribute, Object value, Class<?> type) {

        try {
            Method method = obj.getClass().getMethod("set" + initAttr(attribute),type);
            method.invoke(obj,value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private String initAttr(String attribute) {
        return attribute.substring(0,1).toUpperCase() + attribute.substring(1);
    }


    /**
     * 以下程序属于扩大类属性的访问权限后直接操作属性，所以在Person类中并不需要编写setter与getter方法，
     * 但是开发中调用属性时要使用setter与getter方法。
     * 开发时不要直接操作属性，而是要通过getter与setter调用。
     *
     * 在反射机制中可以通过Field类操作类中的属性，通过Field类提供的set()与get()方法可以完成设置与取得属性内容的操作。
     * 但是由于属性权限是private，所以需要setAccessible(true)方法将属性设置成可以被外部访问。（破坏了封装性）
     * @Description
     * @Author rd_jianbin_lin
     * @Date 7:32 2017/10/10
     */
    @Test
    public void test_InvokeFiledDemo(){
        try {
            Object obj = person_Clazz.newInstance();
            Field nameField = person_Clazz.getDeclaredField("name"); //取得name属性
            Field ageField = person_Clazz.getDeclaredField("age"); //取得age属性

            nameField.setAccessible(true);  //将name属性设置为可被外部访问（原本是private）
            ageField.setAccessible(true); //将age属性设置为可被外部访问（原本是private）

            nameField.set(obj,"Jeb"); //设置name内容
            ageField.set(obj,24); //设置age内容

            System.out.println("name = " + nameField.get(obj) + ",age = " + ageField.get(obj));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反射机制不仅可以用在类上，还可以应用在任意的引用类型的数据上，其中就包括了数组。
     * 通过getComponentType()获得数组的Class对象
     *
     * @Description
     * @Author rd_jianbin_lin
     * @Date 7:52 2017/10/10
     */
    @Test
    public void test_InvokeArrayDemo(){
        int temp[] = {1,2,3};
        /**
         * Returns the {@code Class} representing the component type of an
         * array.  If this class does not represent an array class this method
         * returns null.
         */
        Class<?> clazz = temp.getClass().getComponentType();
        int len = Array.getLength(temp);
        System.out.println("类型：" + clazz.getName());
        System.out.println("长度：" + len);
        System.out.println("第一个内容：" + Array.get(temp,0));
        Array.set(temp,0,0);
        System.out.println("第一个内容：" + Array.get(temp,0));

        Object newArray = Array.newInstance(clazz,8); // 开辟新的数组大小,开辟的长度写成 8
        System.out.println(Array.getLength(newArray));
        System.arraycopy(temp,0,newArray,0,len); //复制数组内容

        for(int i = 0 ;i < Array.getLength(newArray);i++){
            System.out.print(Array.get(newArray,i) + " , ");
        }
    }

}



class Person{
    private String name;
    private int age;

    public Person(){}  //这个无参构造方法如果没有的话，上面的test_newInstance()会报错。

    // java.lang.InstantiationException
    // 因为类中并没有存在无参构造方法，所以根本无法直接使用newInstance()方法进行实例化的。
    public Person(String name,int age){
        this.name = name;
        this.age = age;
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
    public void hello(){
        System.out.println("hello person ...");
    }
    public void hello(String name,int age){
        this.name = name;
        this.age = age;
        System.out.println("hello " + this.name + "(age = " + this.age + ")");
    }
    public String sayHello(String name,int age){
        this.name = name;
        this.age = age;
        return ("hello " + this.name + "(age = " + this.age + ")");
    }
}
