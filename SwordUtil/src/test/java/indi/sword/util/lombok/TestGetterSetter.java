package indi.sword.util.lombok;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 4:16 PM 04/06/2018
 * @MODIFIED BY
 */
// @Getter / @Setter自动生成Getter/Setter方法
public class TestGetterSetter {
    @Getter @Setter private int age = 10;
    @Setter(AccessLevel.PROTECTED) private String name;

    public static void main(String[] args) {
        TestGetterSetter getterSetterDemo = new TestGetterSetter();
        System.out.println(getterSetterDemo.getAge());
    }

/*

    // 不使用lombok
    private int age = 10;
    private String name;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    protected void setName(String name) {
        this.name = name;
    }
*/
}
