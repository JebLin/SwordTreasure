package indi.sword.util.lombok;

import lombok.NonNull;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 4:05 PM 04/06/2018
 * @MODIFIED BY
 */
public class TestNonNull {
    private String name;

    public TestNonNull(@NonNull LombokPerson person){
        this.name = person.name;
        System.out.println("TestNonNull -> " + this.name);
    }
/*
    // 不使用 lombok
    public NonNullExample(Person person) {
        if (person == null) {
            throw new NullPointerException("person");
        }
        this.name = person.getName();
    }

*/

    public static void main(String[] args) {
//        new TestNonNull(new LombokPerson("Jeb"));
        new TestNonNull(null);
    }
}

class LombokPerson{
    public String name;
    public LombokPerson(String name){
        this.name = name;
    }

}
