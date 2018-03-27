package indi.sword.util.basic._other;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 10:34 PM 27/03/2018
 * @MODIFIED BY
 */
public class TestBlankTo20 {

    public static void main(String[] args) {
        String str = " we are family  ";


        replaceAllBlank(str);

    }

    private static void replaceAllBlank(String str) {

        int newCharArrLength = getNewCharArrLength(str);

        for (int i = 0; i < str.length(); i++) {


        }

        replaceAllBlank(str.toCharArray());

    }



    private static void replaceAllBlank(char[] charArr) {


    }

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
