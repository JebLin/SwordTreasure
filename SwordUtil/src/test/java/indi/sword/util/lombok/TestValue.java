package indi.sword.util.lombok;

import lombok.*;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 4:23 PM 04/06/2018
 * @MODIFIED BY
 */
/*
    @Data: 自动为所有字段添加@ToString为非final字段添加@Setter @Setter,和@RequiredArgsConstructor
    @Value：用在类上，是@Data的不可变形式，相当于为属性添加final声明，只提供getter方法，而不提供setter方法

 */
@NoArgsConstructor
@Value
public class TestValue {
    private String name = "name";
    private int age = 25;
    private boolean male = false;
    private static final int count = 10;

    public static void main(String[] args) {
        TestValue testValue = new TestValue();
        System.out.println(testValue.getAge());
        System.out.println(testValue.getName());
        System.out.println(testValue.isMale());
    }

}
