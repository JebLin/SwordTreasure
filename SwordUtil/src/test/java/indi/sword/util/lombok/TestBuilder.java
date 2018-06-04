package indi.sword.util.lombok;

import lombok.*;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 4:23 PM 04/06/2018
 * @MODIFIED BY
 */
/*
    @Builder：用在类、构造器、方法上，为你提供复杂的builder APIs，
    让你可以像如下方式一样调用Person.builder().name("Adam Savage").city("San Francisco").job("Mythbusters").job("Unchained Reaction").build();
 */
@Data
@Builder
public class TestBuilder {
    @NonNull private String name;
    @NonNull private int age ;
    @NonNull private boolean male;

    public static void main(String[] args) {
//        TestBuilder testValue = new TestBuilder("ljb",24,true);
        TestBuilder testValue = TestBuilder.builder().name("ljb").age(25).male(true).build();


        System.out.println(testValue.getAge());
        System.out.println(testValue.getName());
        System.out.println(testValue.isMale());
    }

}
