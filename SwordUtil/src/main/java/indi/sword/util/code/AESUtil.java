package indi.sword.util.code;

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

import indi.sword.util.base.StringUtils;

/**
 * 改进AESUtil方法
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Jun 26, 2017 11:56:02 AM
 */
public class AESUtil {
	
	
	/**
	 * 加密（默认秘钥）
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jun 26, 2017 11:53:42 AM
	 * @param content
	 * @return
	 */
	public static String getAESFromContent(String content){
		return getAESFromContent(content,null);
	}
	
	/**
	 * 解密（默认秘钥）
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jun 26, 2017 11:54:38 AM
	 * @param content
	 * @return
	 */
	public static String getContentFromAES(String content){
		return getContentFromAES(content,null);
	}
	
	/**
	 * 加密（指定秘钥）
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jun 26, 2017 11:54:11 AM
	 * @param content
	 * @param password
	 * @return
	 */
	public static String getAESFromContent(String content, String password){
		byte[] encryptResult = encrypt(content, password);
		String encryptResultStr = parseByte2HexStr(encryptResult);
		return encryptResultStr;
	}
	
	/**
	 * 解密 （指定秘钥）
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jun 26, 2017 11:54:29 AM
	 * @param content
	 * @param password
	 * @return
	 */
	public static String getContentFromAES(String content, String password){
		byte[] decryptFrom = parseHexStr2Byte(content);  
		
		byte[] decryptResult = decrypt(decryptFrom,password);
		return new String(decryptResult);
	}
	
	/**
	 * @Description: AES加密
	 * @param content 需要加密的内容
	 * @param password 加密密码
	 * @author Jun 26, 2017 11:54:29 AM
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
	 * 本人懒，有时候不想输密码，那么就来一个默认秘钥吧
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jun 26, 2017 11:46:42 AM
	 * @param password
	 */
	public static String generatePassword(String password){
	    if (StringUtils.isEmpty(password)) {
	    	password = System.getenv("AES_SYS_KEY");
        }
        if (StringUtils.isEmpty(password)) {
        	password = System.getProperty("AES_SYS_KEY");
        }
        if (StringUtils.isEmpty(password)) {
        	password = "20170626keng_die@sword52888/*-+";// 默认种子
        }
        return password;
	}
	
	/**
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 2, 2017 4:15:24 PM
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

	/**
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 2, 2017 4:15:40 PM
	 */
	private static String parseByte2HexStr(byte buf[]) {  
		StringBuffer sb = new StringBuffer();  
		for (int i = 0; i < buf.length; i++) {  
			String hex = Integer.toHexString(buf[i] & 0xFF);  
			if (hex.length() == 1) {  
				hex = '0' + hex;  
			}  
			sb.append(hex.toUpperCase());  
		}  
		return sb.toString();  
	}
	
	/**
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 2, 2017 4:15:47 PM
	 */
	private static byte[] parseHexStr2Byte(String hexStr) {  
		if (hexStr.length() < 1) {
			return null;
		}  
		byte[] result = new byte[hexStr.length()/2];  
		for (int i = 0;i< hexStr.length()/2; i++) {  
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
			result[i] = (byte) (high * 16 + low);  
		}  
		return result;  
	} 
	
	public  static void main(String[] args){

		System.out.println("gg");
//		  if(null==args||args.length==0){
//	            args=new String[1];
//	            args[0]="ABCDefgh~！@#$%^&*()|'?>.<;1234567823401";
//	        }
//	        String layer = AESUtil.getAESFromContent(args[0]);
//	        System.out.println("密文 ："+layer);
//	        System.out.println("密文 ："+layer.length());
//	        String plain = AESUtil.getContentFromAES(layer);
//	        System.out.println("解密后文 ："+plain);
//	        System.out.println("原文 ："+args[0]);
//	        System.out.println("原文  == 解密文 ？"+ plain.equals(args[0]));
//	        System.out.println(System.getProperty("AES_SYS_KEY"));
//	        System.out.println(System.getenv("AES_SYS_KEY"));
//	        System.out.println(AESUtil.generatePassword(null));
//	        
	}

}