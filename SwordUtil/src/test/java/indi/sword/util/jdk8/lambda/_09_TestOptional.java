package indi.sword.util.jdk8.lambda;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

/**
 *  Created by rd_jianbin_lin on 2017/9/20.
 *
 * 一、Optional 容器类：用于尽量避免空指针异常
 * 	Optional.of(T t) : 创建一个 Optional 实例
 * 	Optional.empty() : 创建一个空的 Optional 实例
 * 	Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
 * 	isPresent() : 判断是否包含值
 * 	orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
 * 	orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
 * 	map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
 * 	flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
 */
public class _09_TestOptional {


    //下面创建了一个不包含任何值的Optional实例
    Optional empty = Optional.ofNullable(null);
    //调用工厂方法创建Optional实例
    Optional<String> name = Optional.of("Sanaulla");


    @Test
    public void test_of(){
        //of方法通过工厂方法创建Optional类。需要注意的是，创建对象时传入的参数不能为null。
        // 如果传入参数为null，则抛出NullPointerException 。 
        Optional<_01_Employee> op = Optional.of(new _01_Employee());
        System.out.println(op.get());
/*
        //传入参数为null，抛出NullPointerException.
        Optional<String> someNull = Optional.of(null);
*/
        //为指定的值创建一个Optional，如果指定的值为null，则返回一个空的Optional。
        //ofNullable与of方法相似，唯一的区别是可以接受参数为null的情况。
        Optional<_01_Employee> op2 = Optional.ofNullable(null);
        System.out.println(op2.get());  // get的时候才抛异常

        Optional<_01_Employee> op3 = Optional.empty();
        System.out.println(op3.get());
    }

    // 如果Optional有值则将其返回，否则抛出NoSuchElementException。
    @Test
    public void test_get(){
        //执行下面的代码会输出：No value present
        try {
            //在空的Optional实例上调用get()，抛出NoSuchElementException
            System.out.println(empty.get());
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void test_present(){
        //调用工厂方法创建Optional实例
        Optional<String> name = Optional.of("Sanaulla");
        //isPresent方法用来检查Optional实例中是否包含值
        if (name.isPresent()) {
            //在Optional实例内调用get()返回已存在的值
            System.out.println(name.get());//输出Sanaulla
        }
    }

    //如果Optional实例有值则将其返回，否则返回orElse方法传入的参数。
    @Test
    public void test_OrElse(){
        //如果值不为null，orElse方法返回Optional实例的值。
        //如果为null，返回传入的消息。
        //输出：There is no value present!
        System.out.println(empty.orElse("There is no value present!"));
        //输出：Sanaulla
        System.out.println(name.orElse("There is some value!"));

    }

    //orElseGet与orElse方法类似，区别在于得到的默认值。
    // orElse方法将传入的字符串作为默认值，orElseGet方法可以接受Supplier接口的实现用来生成默认值
    @Test
    public void test_OrElseGet(){
        //orElseGet与orElse方法类似，区别在于orElse传入的是默认值，
        //orElseGet可以接受一个lambda表达式生成默认值。
        //输出：Default Value
        System.out.println(empty.orElseGet(() -> "Default Value"));
        //输出：Sanaulla
        System.out.println(name.orElseGet(() -> "Default Value"));


        System.out.println("-------------------------------------------------");
        Optional<_01_Employee> op1 = Optional.ofNullable(new _01_Employee());
        if(op1.isPresent()){
            System.out.println(op1.get());
        }
        op1 = Optional.ofNullable(null);
        _01_Employee emp = op1.orElse(new _01_Employee("zhangsan"));
        System.out.println(emp);

        _01_Employee emp2 = op1.orElseGet(_01_Employee::new); //Supplier
        System.out.println(emp2);
    }

    //在orElseGet方法中，我们传入一个Supplier接口。
    // 然而，在orElseThrow中我们可以传入一个lambda表达式或方法，如果值不存在来抛出异常
    @Test
    public void test_OrElseThrow(){
        try {
            empty.orElseThrow(ValueAbsentException::new);
        } catch (Throwable throwable) {
            System.out.println(throwable.getMessage());
        }
    }

    class ValueAbsentException extends Throwable {
        public ValueAbsentException() {
            super();
        }
        public ValueAbsentException(String msg) {
            super(msg);
        }
        @Override
        public String getMessage() {
            return "No value present in the Optional instance";
        }
    }


    @Test
    public void test_map(){
        //map方法执行传入的lambda表达式参数对Optional实例的值进行修改。
        //为lambda表达式的返回值创建新的Optional实例作为map方法的返回值。
        Optional<String> upperName = name.map((value) -> value.toUpperCase());
        System.out.println(upperName.orElse("No value found"));


        Optional<_01_Employee> op1 = Optional.of(new _01_Employee(111,"zhangsan",18,999.99));
        System.out.println(op1.get());

        //map方法执行传入的lambda表达式参数对Optional实例的值进行修改。
        //为lambda表达式的返回值创建新的Optional实例作为map方法的返回值。
        Optional<String> op2 = op1.map(_01_Employee::getName);
        System.out.println(op2.get());

    }

    //如果有值，为其执行mapping函数返回Optional类型返回值，否则返回空Optional。
    // flatMap与map（Funtion）方法类似，区别在于flatMap中的mapper返回值必须是Optional。
    // 调用结束时，flatMap不会对结果用Optional封装。
    @Test
    public void test_flatMap(){
        //flatMap与map（Function）非常类似，区别在于传入方法的lambda表达式的返回类型。
        //map方法中的lambda表达式返回值可以是任意类型，在map函数返回之前会包装为Optional。
        //但flatMap方法中的lambda表达式返回值必须是Optionl实例。
        Optional<String> upperName = name.flatMap((e) -> Optional.of(e.toUpperCase()));
        System.out.println(upperName.orElse("No value found"));//输出SANAULLA

        Optional<_01_Employee> op1 = Optional.of(new _01_Employee(111,"zhangsan",18,999.99));
        Optional<String> op3 = op1.flatMap((e) -> Optional.of(e.getName())); //要求返回值必须是Optional
        System.out.println(op3.get());
    }

    //如果有值并且满足断言条件返回包含该值的Optional，否则返回空Optional。
    @Test
    public void test_filter(){
        Optional<String> op1 = name.filter(e -> e.length() > 6);
        System.out.println(op1);
        System.out.println(op1.get());
        System.out.println(op1.orElse("The name is less than 6 characters"));

        System.out.println("------------------------------");

        Optional<String> op2 = name.filter(e -> e.length() > 12);
        System.out.println(op2.orElse("The name is less than 12 characters"));

    }

}
