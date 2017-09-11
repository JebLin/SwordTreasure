package indi.sword.util.base;

import java.security.MessageDigest;

/**
 * @Description  MD5加密工具
 * @Author:rd_jianbin_lin
 * @Date: 18:38 2017/9/11
 */
public class MD5Util {
    /**
     * @Description 生成MD5
     * @Author:rd_jianbin_lin
     * @Date: 18:38 2017/9/11
     */
    public static String getMD5(String str) {
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象
            byte[] messageByte = str.getBytes("UTF-8");
            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位
            md5 = NumberUtils.parseByte2HexStr(md5Byte);        // 转换为16进制字符串
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return md5;
    }

    /**
     * @Description 使用时间戳获取一个唯一的MD5字符串
     * @Author:rd_jianbin_lin
     * @Date: 19:11 2017/9/11
     */
    public static String getMD5(){
        return getMD5(System.currentTimeMillis() + "");
    }

    /**
     * @Description 校验密码准确性
     * @Author:rd_jianbin_lin
     * @Date: 18:38 2017/9/11
     */
    public static boolean checkpassword(String str, String passwd) {
        return passwd.equals(getMD5(str));
    }

    public static void main(String[] args) {
        System.out.println(getMD5("林剑斌"));
        System.out.println(checkpassword("林剑斌","A0B4507A75D44138A3DA5C8C4AEB4594"));
    }
}
