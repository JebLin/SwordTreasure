package indi.sword.util._00interview;

import java.util.Arrays;

// 不使用任何循环和递归，输出打印n条(n>1) "Hello World
public class TestPrintHW {
    public static void main(String[] args) {
        String str = "Hello";
        int num = 6;
        Object[] obj = new Object[num];

        // Assigns the specified Object reference to each element of the specified array of Objects.
        //  把 str 赋值给 obj数组中的每一个元素。
        Arrays.fill(obj, str);
        String s = Arrays.toString(obj);

        System.out.println(s);

        s = s.replaceAll("([\\[\\]]|[,][\\s])", "\n");
        System.out.println(s);
    }
}
