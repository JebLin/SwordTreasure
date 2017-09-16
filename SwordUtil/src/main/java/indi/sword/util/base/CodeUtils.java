package indi.sword.util.base;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @Description 一些code工具类
 * @Author:rd_jianbin_lin
 * @Date: 19:05 2017/9/11
 */
public class CodeUtils {

    /**
     * @Description getUUID()
     * @Author:rd_jianbin_lin
     * @Date: 19:08 2017/9/11
     */
    public static final String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","").toUpperCase();
    }

    public static void main(String[] args){

    }

}
