package indi.sword.util.base;

/**
 * @Descrption 专门用来处理数字的工具类
 * @author rd_jianbin_lin
 * @Date Jul 15, 2017 5:13:44 PM
 */
public class NumberUtils {

	private static final char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };
	
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F' };
	
	/**
	 * @Descrption 10进制byte转16进制String
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 5:53:17 PM
	 */
	public static String parseByte2HexStr(byte[] b) {
		int length = b.length;
		char[] buf = new char[length * 2];
		for (int i = 0, j = 0, k; i < length;) {
			k = b[i++];
			buf[j++] = HEX_DIGITS[(k >>> 4) & 0x0F];
			buf[j++] = HEX_DIGITS[k & 0x0F];
		}
		return new String(buf);
	}


	/**
	 * 16进制 转 10进制的byte  hexFromString("20") --> 32
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 5:28:42 PM
	 */
	public static byte[] parseHexStr2Byte(String hex) {
		int len = hex.length();
		byte[] buf = new byte[((len + 1) / 2)];

		int i = 0, j = 0;
		if ((len % 2) == 1)
			buf[j++] = (byte) hexDigit2DecimalDigit(hex.charAt(i++));

		while (i < len) { // 乘法 移位更牛逼
			buf[j++] = (byte) ((hexDigit2DecimalDigit(hex.charAt(i++)) << 4) | hexDigit2DecimalDigit(hex.charAt(i++)));
		}
		return buf;
	}

	/**
	 * @Descrption 16进制字符转10进制，A -> 10， B -> 11 , C -> 12 , D -> 13, E -> 14, F -> 15 
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 5:18:06 PM
	 */
	public static int hexDigit2DecimalDigit(char ch) {
		if (ch >= '0' && ch <= '9')
			return ch - '0';
		if (ch >= 'A' && ch <= 'F')
			return ch - 'A' + 10;
		if (ch >= 'a' && ch <= 'f')
			return ch - 'a' + 10;

		throw new IllegalArgumentException("invalid hex digit '" + ch + "'");
	}
	
	/**
	 * 
	 * @Descrption 将指定 long 值转化为16进制字符串格式
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 5:14:16 PM
	 */
	public static String long2HexStr(long x, int chars) {
		char[] buf = new char[chars];
		for (int charPos = chars; --charPos >= 0;) {
			buf[charPos] = hexDigits[(int) (x & 0x0f)];
			x >>>= 4;
		}
		return new String(buf);
	}
	
	public static void main(String[] args){
	}

}
