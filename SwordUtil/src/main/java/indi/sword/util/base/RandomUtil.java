package indi.sword.util.base;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 
 * @Descrption RandomUtil
 * @author rd_jianbin_lin
 * @Date Fru 11, 2017 4:28:01 PM
 */
public class RandomUtil {

    private RandomUtil() {
    }

    private static final Random random = ThreadLocalRandom.current();
    public static final String BASE_CHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-*&()[]+!@#$%^;',.<>{}?~`";
    public static final String LETTER_EN = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LETTER_WITH_DIGIT = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * 获取任意字符的随机串 ，包含 字母数字与符号
     * @author rd_jianbin_lin
     * @date Feb 7, 2017 10:32:25 AM
     * @param length
     * @return
     */
    public static String getRamdomString(int length) { //length
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < length; i++){
            int r = (int) (Math.random() * BASE_CHAR.length());
            sb.append(BASE_CHAR.charAt(r));
        }

        return sb.toString();
    }

    /**
     * @Description  获取任何随机串，包含 字母与数字
     * @Author:rd_jianbin_lin
     * @Date: 19:50 2017/9/11
     */
    public static String getRandomStringLetterWithDigit(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++){
        	int r = (int) (Math.random() * LETTER_WITH_DIGIT.length());
            sb.append(LETTER_WITH_DIGIT.charAt(r));
        }
        return sb.toString();
    }
    
    
    /**
     * @Description 获取任何随机串，只包含英文
     * @Author:rd_jianbin_lin
     * @Date: 19:50 2017/9/11
     */
    public static String getRandomStringLetter(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++){
        	int r = (int) (Math.random() * LETTER_EN.length());
            sb.append(LETTER_EN.charAt(r));
        }
        return sb.toString();
    }
    

}
