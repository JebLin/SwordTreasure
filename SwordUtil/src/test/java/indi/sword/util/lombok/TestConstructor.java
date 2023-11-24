package indi.sword.util.lombok;

import lombok.*;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 4:23 PM 04/06/2018
 * @MODIFIED BY
 */
/*
    @NoArgsConstructor: 自动生成无参数构造函数。
    @AllArgsConstructor: 自动生成全参数构造函数。
    @Data: 自动为所有字段添加@ToString为非final字段添加@Setter @Setter,和@RequiredArgsConstructor

 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "male")
public class TestConstructor {
    @NonNull private String name;
    @NonNull private int age;
    @NonNull private boolean male;
    private static final int count = 10;

    public static void main(String[] args) {
        TestConstructor testConstructor = new TestConstructor("ljb",25,true);
        System.out.println(testConstructor.getAge());
        System.out.println(testConstructor.getName());
        System.out.println(testConstructor.isMale());
        System.out.println("toString -> " + testConstructor);
    }

}
