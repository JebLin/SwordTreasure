package indi.sword.util.chaos;

import java.util.regex.Pattern;

/**
 * @author jeb_lin
 * 8:46 PM 2020/1/2
 */
public class TT {

    private static String str = "疫.*情";

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(str);
        String textStr = "疫情";
        System.out.println(pattern.matcher(textStr).matches());

        String text2Str = "疫233情";
        System.out.println(pattern.matcher(text2Str).matches());


    }
}
