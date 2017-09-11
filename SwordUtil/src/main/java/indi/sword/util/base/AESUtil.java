package indi.sword.util.base;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * AESUtil 加密算法方法
 *
 * @author rd_jianbin_lin
 * @Descrption
 * @Date Jun 26, 2017 11:56:02 AM
 */
public class AESUtil {


    /**
     * 加密（默认秘钥）
     *
     * @param content
     * @return
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Jun 26, 2017 11:53:42 AM
     */
    public static String getAESFromContent(String content) {
        return getAESFromContent(content, null);
    }

    /**
     * 解密（默认秘钥）
     *
     * @param content
     * @return
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Jun 26, 2017 11:54:38 AM
     */
    public static String getContentFromAES(String content) {
        return getContentFromAES(content, null);
    }

    /**
     * 加密（指定秘钥）
     *
     * @param content
     * @param password
     * @return
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Jun 26, 2017 11:54:11 AM
     */
    public static String getAESFromContent(String content, String password) {
        byte[] encryptResult = encrypt(content, password);
        String encryptResultStr = NumberUtils.parseByte2HexStr(encryptResult);

        return encryptResultStr;
    }

    /**
     * 解密 （指定秘钥）
     *
     * @param content
     * @param password
     * @return
     * @Descrption
     * @author rd_jianbin_lin
     * @Date Jun 26, 2017 11:54:29 AM
     */
    public static String getContentFromAES(String content, String password) {
        byte[] decryptFrom = NumberUtils.parseHexStr2Byte(content);
        byte[] decryptResult = decrypt(decryptFrom, password);
        return new String(decryptResult);
    }

    /**
     * @Description 加密
     * @Author:rd_jianbin_lin
     * @Date: 18:45 2017/9/11
     */
    private static byte[] encrypt(String content, String password) {
        password = generatePassword(password); //免得password为空
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description 有时候不想输密码，那么就来一个默认秘钥吧
     * @Author:rd_jianbin_lin
     * @Date: 18:46 2017/9/11
     */
    public static String generatePassword(String password) {
	    if (StringUtils.isEmpty(password)) {
	    	password = System.getenv("AES_SYS_KEY");
        }
        if (StringUtils.isEmpty(password)) {
        	password = System.getProperty("AES_SYS_KEY");
        }
        if (StringUtils.isEmpty(password)) {
            password = "sword52888/*-+";// 默认种子
        }
        return password;
    }
    /**
     * @Description 解密
     * @Author:rd_jianbin_lin
     * @Date: 18:46 2017/9/11
     */
    private static byte[] decrypt(byte[] content, String password) {
        password = generatePassword(password); //免得password为空
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        if (null == args || args.length == 0) {
            args = new String[1];
            args[0] = "林剑斌ABCDefgh~！@#$%^&*()|'?>.<;1234567823401";
        }
        String layer = AESUtil.getAESFromContent(args[0]);
        System.out.println("密文 ：" + layer);
        System.out.println("密文 ：" + layer.length());
        String plain = AESUtil.getContentFromAES(layer);
        System.out.println("解密后文 ：" + plain);
        System.out.println("原文 ：" + args[0]);
        System.out.println("原文  == 解密文 ？" + plain.equals(args[0]));
        System.out.println(System.getProperty("AES_SYS_KEY"));
        System.out.println(System.getenv("AES_SYS_KEY"));
        System.out.println(AESUtil.generatePassword(null));

        System.out.println("--------------------");
        String encryptResultStr = NumberUtils.parseByte2HexStr("林剑斌".getBytes());
        System.out.println(encryptResultStr);

        System.out.println(new String(NumberUtils.parseHexStr2Byte("E69E97E58991E6968C")));

    }

}