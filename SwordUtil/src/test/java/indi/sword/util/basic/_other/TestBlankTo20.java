package indi.sword.util.basic._other;

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
        String str = "  we are family  ";


        char[] newCharArr = replaceAllBlank(str);
        for (int i = 0; i < newCharArr.length; i++) {
            System.out.print(newCharArr[i]);
        }

    }

    private static char[] replaceAllBlank(String str) {

        int newCharArrLength = getNewCharArrLength(str);
        char[] newCharArr = new char[newCharArrLength];
        for (int i = 0; i < str.length(); i++) {
            newCharArr[i] = str.charAt(i);
        }
        int oldLastIndex = str.length() - 1 ;
        int newLastIndex = newCharArrLength - 1;
        for (int i = oldLastIndex; i >= 0 ; i--) {
            char tempChar = str.charAt(i);
            if(tempChar == ' '){
                newCharArr[newLastIndex--] = '0';
                newCharArr[newLastIndex--] = '2';
                newCharArr[newLastIndex--] = '%';
            }else{
                newCharArr[newLastIndex--] = tempChar;
            }
        }
        return newCharArr;
    }

    /**
     * @Description
     * 获取新数组长度
     * @Author jeb_lin
     * @Date 12:21 AM 28/03/2018
     * @MODIFIED BY
     */
    private static int getNewCharArrLength(String str){
        int length = str.length();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == ' ') {
                length += 2;
            }
        }
        System.out.println(length);
        return length;
    }

}
