package indi.sword.util.lombok;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 4:39 PM 04/06/2018
 * @MODIFIED BY
 */
@AllArgsConstructor
@EqualsAndHashCode // 尝试把这个注释掉，会得到不同的结果
public class TestEqualAndHashCode {
    private String name;
    private int age;

    public static void main(String[] args) {
        TestEqualAndHashCode a1 = new TestEqualAndHashCode("ljb",25);
        TestEqualAndHashCode a2 = new TestEqualAndHashCode("ljb",25);
        System.out.println(a1.equals(a2));

        Set<TestEqualAndHashCode> set = new HashSet<>();
        set.add(a1);
        set.add(a2);
        System.out.println(set.size());
    }
}
