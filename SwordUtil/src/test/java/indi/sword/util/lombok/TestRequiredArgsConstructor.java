package indi.sword.util.lombok;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 4:58 PM 04/06/2018
 * @MODIFIED BY
 */
@RequiredArgsConstructor(staticName = "of")
@ToString
public class TestRequiredArgsConstructor {
    @NonNull private String name;
    @NonNull private int age;
    public static void main(String[] args) {
        System.out.println(new TestRequiredArgsConstructor("ljb",25));
        // 使用静态工厂方法
        System.out.println(TestRequiredArgsConstructor.of("ljb",25));
    }

}
