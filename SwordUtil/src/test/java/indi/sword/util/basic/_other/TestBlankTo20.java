package indi.sword.util.basic._other;


import java.util.Arrays;
import java.util.Scanner;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 10:34 PM 27/03/2018
 * @MODIFIED BY
 */
/*
    时间复杂度为O(n2)不足以拿到Offer
    一、
        最直观的做法是从头到尾扫描字符串，每一次碰到空格字符的时候做替换。由于是把1个字符替换称3个字符，我们必须把空格后面的所有的字符都后移两个字节，否则就有两个字符被覆盖了。
        假设字符的长度是n。对每个空格字符，需要移动后面O(n)个字符，因此对含有O(n)个空格字符串而言总的时间效率是O(n2).
    二、
        采用StringBuilder，那么就必须增加辅助空间。O(1)
    三、就是下面的方法
        时间复杂度为O(N) ,不需要辅助空间。
        首先准备两个指针，P1和P2.   P1指向原始字符串的末尾，而P2指向替换之后的字符串的末尾。接下来我们向前移动指针P1,逐个把它指向的字符复制到P2指向的位置，直到碰到第一个空格为止。
 */

public class TestBlankTo20 {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        String inputStr = scan.nextLine();
        System.out.println("your input is -> " + inputStr);
        char[] charArr = replaceAllBlank_Enter(inputStr);


        char[] newCharArr = replaceAllBlank(charArr,inputStr.length());
        for (int i = 0; i < newCharArr.length; i++) {
            System.out.print(newCharArr[i]);
        }
    }

    /**
     * @Description
     *  用户输入完字符串后，进入这个方法,
     *  在这里转换成数组的意义何在呢？
     *  就是为了传入的是一个字符数组，我的意思是，上面main方法可以不用传入一个String，而是直接传入一个char数组，那么这样的好处就是说，
     *  传入任何一个char数组，我都不用使用其他辅助空间。
     * @Author jeb_lin
     * @Date 6:09 PM 28/03/2018
     * @MODIFIED BY
     */
    private static char[] replaceAllBlank_Enter(String str) {
        char[] charArr = new char[999];
        System.arraycopy(str.toCharArray(),0,charArr,0,str.length());
        return charArr;
    }


    /**
     * @Description
     *
     * @Author jeb_lin
     * @Date 6:16 PM 28/03/2018
     * @MODIFIED BY
     * @param charArr 字符数组（新的老的都是用同一个)
     * @param originalLength 原来字符串的长度
     */
    private static char[] replaceAllBlank(char[] charArr,int originalLength) {

        int newCharArrLength = getNewCharArrLength(charArr,originalLength);
        int oldLastIndex = originalLength - 1 ;
        int newLastIndex = newCharArrLength - 1;
        for (int i = oldLastIndex; i >= 0 ; i--) {
            char tempChar = charArr[i];
            if(tempChar == ' '){
                charArr[newLastIndex--] = '0';
                charArr[newLastIndex--] = '2';
                charArr[newLastIndex--] = '%';
            }else{
                charArr[newLastIndex--] = tempChar;
            }
        }
        return charArr;
    }

    /**
     * @Description
     * 获取新数组长度
     * @Author jeb_lin
     * @Date 12:21 AM 28/03/2018
     * @MODIFIED BY
     */
    private static int getNewCharArrLength(char[] charArr,int originalLength){
        int length = originalLength;
        for (int i = 0; i < originalLength; i++) {
            if (charArr[i] == ' ') {
                length += 2;
            }
        }
        System.out.println("after length -> " + length);
        return length;
    }

}
