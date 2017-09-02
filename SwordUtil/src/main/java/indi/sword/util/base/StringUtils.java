package indi.sword.util.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import indi.sword.util.base.regex.BaseRegexEnum;
import indi.sword.util.code.Base64Encoder;

/**
 * 
 * @Descrption 字符串辅助处理工具类
 * @author rd_jianbin_lin
 * @Date Sep 2, 2016 4:32:21 PM
 */
public class StringUtils {

	/**
	 * 判断对象是否为空
	 * @Description
	 * @author rd_jianbin_lin
	 * @date Jun 5, 2017 11:42:50 AM
	 * @Exception
	 */
	public static boolean isEmpty(Object obj) {
		if (null == obj) {
			return true;
		}
		if (obj instanceof String) {
			String str = (String) obj;
			return str == null || str.trim().isEmpty();
		}
		if(obj instanceof StringBuffer || obj instanceof StringBuilder){
			return isEmpty(obj.toString());
		}
		if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		return false;
	}

	/**
	 * 判断字符串里面有没有东西
	 * 
	 * @Description
	 * @author rd_jianbin_lin
	 * @date Jun 5, 2017 11:43:03 AM
	 * @Exception
	 */
	public static boolean isBlank(String str) {
		return trim(str).isEmpty();
	}

	/**
	 * html中的空格
	 */
	public static String NBSP = "\u00a0";

	/**
	 * 除去包含NBSP在内的前后空格
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {

		while (str.startsWith(NBSP)) {
			str = str.substring(1, str.length());
		}
		while (str.endsWith(NBSP)) {
			str = str.substring(0, str.length() - 1);
		}
		return str.trim();
	}

	/**
	 * 转换字符串的编码
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Sep 2, 2017 4:00:47 PM
	 */
	public static String codeConvert(String origin, String origin_code, String result_code)
			throws UnsupportedEncodingException {
		String result = new String(origin.getBytes(result_code), result_code);
		return result;
	}

	/**
	 * 判断一个字符串是否验证码（4位英文or数字）
	 * 
	 * @param string
	 *            字符串
	 * @return
	 */
	public static boolean isCaptcha(String string) {
		Pattern pattern = Pattern.compile("[0-9a-zA-Z]{4}");
		return pattern.matcher(string).matches();
	}

	/**
	 * 从字符串中提取出符合正则表达式的子字符串
	 * 
	 * @param string
	 * @param regex
	 *            正则表达式
	 * @return 子字符串
	 */
	public static String getStringWithRegex(String string, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		if (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	/**
	 * 判断一个字符是否英文或数字
	 * 
	 * @param tempChar
	 * @return
	 */
	public static boolean isDigitorLetter(char tempChar) {
		// TODO Auto-generated method stub
		return (('0' <= tempChar && tempChar <= '9') || ('a' <= tempChar && tempChar <= 'z')
				|| ('A' <= tempChar && tempChar <= 'Z'));
	}

	/**
	 * 获取文件路径的目录地址
	 * @param path
	 * @return
	 */
	public static String getDir(String path) {
		// TODO Auto-generated method stub
		String dir = path.substring(0, getLastSeparator(path));
		// System.out.println(dir);
		return dir;
	}

	/**
	 * 获取路径中的文件名
	 * 
	 * @param path
	 * @return
	 */
	public static String getFileName(String path) {
		// TODO Auto-generated method stub
		String fileName = path.substring(getLastSeparator(path) + 1);
		// System.out.println(fileName);
		return fileName;
	}

	public static Integer getLastSeparator(String path) {
		if (path.contains("/")) {
			return path.lastIndexOf('/');
		} else {
			return path.lastIndexOf('\\');
		}
	}

	/**
	 * 一般用于分隔从文件中读取的键值对
	 * 
	 * @param key_value
	 *            要分割的键值对
	 * @param separator
	 *            键-值的分隔符（一般如'：' '='）
	 * @return key_value[] 以字符串数据存储的分割后的键值对，key_value[0]为键，key_value[1]为值
	 * @author rongyang_lu
	 * @date 2015年8月17日 上午9:04:28
	 */
	public static String[] splitKeyValue(String key_value, String separator) {
		return key_value.split(separator, 2);
	}

	/**
	 * 判断一个字符串是否以目标字符串结尾
	 * 
	 * @param hql_builder
	 *            要判断的字符串
	 * @param target
	 *            目标字符串
	 * @return
	 */
	public static boolean isEndWith(StringBuilder hql_builder, String target) {
		return hql_builder.length() - hql_builder.lastIndexOf(target) <= target.length();
	}

	/**
	 * 移除最后一次出现的某一字符串
	 * 
	 * @param hql_builder
	 *            要处理的字符串
	 * @param string_to_be_removed
	 *            将被移除的字符串
	 * @return
	 */
	public static void removeLastOf(StringBuilder hql_builder, String string_to_be_removed) {
		int start = hql_builder.lastIndexOf(string_to_be_removed);
		hql_builder.replace(start, start + string_to_be_removed.length() + 1, "");
	}

	/**
	 * 判断两个字符串是否相等
	 * 
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEqual(String str1, String str2) {
		if (str1 == str2) {
			return true;
		}
		if ((str1 == null && str2 != null) || (str1 != null && str2 == null)) {
			return false;
		}
		return str1.equals(str2);
	}

	public static String removeSpace(String str) {
		return str.replaceAll(NBSP, "").replaceAll("\\s", "");
	}

	/**
	 * 获取要下载的请求的文件类型
	 * 
	 * @param file_full_path
	 * @param content_type
	 * @return
	 * @author rongyang_lu
	 * @date 2015年9月29日 下午4:19:18
	 */
	public static String getAttachmentSuffix(String content_type) {
		if ("application/msword".equals(content_type)) {
			return ".doc";
		}
		if ("application/pdf".equals(content_type)) {
			return ".pdf";
		}
		return "";
	}

	/**
	 * 把map转换成格式化的String，方便转回来
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jun 19, 2017 3:29:34 PM
	 * @param map
	 * @return
	 */
	public static String convertMapToJBString(Map<String, String> map) {

		StringBuilder sb = new StringBuilder("");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append("\"");
			sb.append(entry.getKey());
			sb.append("\"");
			sb.append("=");
			sb.append("\"");
			sb.append(entry.getValue());
			sb.append("\"");
			sb.append(",");
		}
		sb.deleteCharAt(sb.length() - 1); // 把最后的逗号给删了

		return sb.toString();
	}

	/**
	 * 把WS格式化的String转回Map
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jun 19, 2017 3:30:07 PM
	 * @param wsStr
	 * @return
	 */
	public static Map<String, String> convertJBStringToMap(String wsStr) {

		Map<String, String> map = new HashMap<String, String>();
		String key = "key";
		String value = "value";
		for (String str : wsStr.split(",")) {
			key = strTrimQuotes(str.split("=")[0]);
			value = strTrimQuotes(str.split("=")[1]);
			map.put(key, value);
		}

		return map;
	}

	/**
	 * 
	 * @Descrption 去掉字符串左右两边的两个引号，为convertJBStringToMap服务.
	 * @author rd_jianbin_lin
	 * @Date Jun 19, 2017 3:23:58 PM
	 * @param str
	 * @return
	 */
	public static String strTrimQuotes(String str) {
		if (str.charAt(0) != '"' || str.charAt(str.length() - 1) != '"') {
			return "wrongStr";
		} else {
			return str.substring(1, str.length() - 1);
		}
	}

	/**
	 * 通过字符串提取其中的cellphone
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jun 22, 2017 5:47:53 PM
	 * @param str
	 * @return
	 */
	public static String getCellbyStr(String str) {

		return getStringWithRegex(str, BaseRegexEnum.REGEX_CELL_CN.value());

	}

	/**
	 * 通过字符串提取其中的Email
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jun 22, 2017 7:35:21 PM
	 * @param str
	 * @return
	 */
	public static String getEmailByStr(String str) {

		return getStringWithRegex(str, BaseRegexEnum.REGEX_MAIL.value());
	}

	/**
	 * 判断是否是手机号码
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jun 22, 2017 7:37:23 PM
	 * @param str
	 * @return
	 */
	public static boolean isCell(String str) {

		return !isEmpty(getStringWithRegex(str, BaseRegexEnum.REGEX_CELL_CN.fullValue()));
	}

	/**
	 * 判断是否是电子邮箱
	 * 
	 * @Descrption
	 * @author rd_jianbin_lin
	 * @Date Jun 22, 2017 7:38:40 PM
	 */
	public static boolean isEmail(String str) {
		return !isEmpty(getStringWithRegex(str, BaseRegexEnum.REGEX_MAIL.fullValue()));
	}

	/**
	 * @Descrption 返回字符串，以固定长度格式表示长整数，这里的长度为8。
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 3:34:21 PM
	 */
	public static String fixNumber(long n) {
		return fixNumber(n, 8);
	}

	/**
	 * @Descrption 返回字符串，以固定长度格式表示长整数。
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 3:34:21 PM
	 */
	public static String fixNumber(long n, int len) {
		char[] b = new char[len];
		for (int i = 0; i < len; i++)
			b[i] = '0';
		return new java.text.DecimalFormat(new String(b)).format(n);
	}

	/**
	 * @Descrption 返回字符串，以固定长度格式表示整数，这里的长度为4。
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 3:34:21 PM
	 */
	public static String fixNumber(int n) {
		return fixNumber((long) n, 4);
	}

	/**
	 * @Descrption 返回字符串，以固定长度格式表示整数。
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 3:34:58 PM
	 */
	public static String fixNumber(int n, int len) {
		return fixNumber((long) n, len);
	}

	/**
	 * 
	 * @Descrption 在字符串后添加空格使之长度为指定长度
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 4:01:31 PM
	 * @param int
	 *            指定长度
	 */
	public static String padStringWidth(String s, int i) {
		StringBuffer stringbuffer = null;
		if (s != null) {
			stringbuffer = new StringBuffer(s);
			stringbuffer.setLength(i);
			int j = s.length();
			for (int l = j; l < i; l++)
				stringbuffer.setCharAt(l, ' ');

		} else {
			stringbuffer = new StringBuffer(i);
			stringbuffer.setLength(i);
			for (int k = 0; k < i; k++)
				stringbuffer.setCharAt(k, ' ');

		}
		return stringbuffer.toString();
	}

	/**
	 * @see padStringWidth(String, int)
	 */
	public static String padStringWidth(int i, int j) {
		return padStringWidth(String.valueOf(i), j);
	}

	/**
	 * @see padStringWidth(String, int)
	 */
	public static String padStringWidth(float f, int i) {
		return padStringWidth(String.valueOf(f), i);
	}

	/**
	 * @see padStringWidth(String, int)
	 */
	public static String padStringWidth(long l, int i) {
		return padStringWidth(String.valueOf(l), i);
	}

	/**
	 * @see padStringWidth(String, int)
	 */
	public static String padStringWidth(double d, int i) {
		return padStringWidth(String.valueOf(d), i);
	}

	/**
	 * @Descrption 异常信息转换成字符串
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 4:58:36 PM
	 */
	public static String stackToString(Throwable e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return "------\r\n" + sw.toString() + "------\r\n";
		} catch (Exception e2) {
			return "(bad stack2string) " + e.getMessage();
		}
	}

	/**
	 * @Descrption 异常信息转换成字符串
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 4:58:36 PM
	 */
	public static String stackToString(Exception e) {
		try {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return "------\r\n" + sw.toString() + "------\r\n";
		} catch (Exception e2) {
			return "(bad stack2string) " + e.getMessage();
		}
	}

	/**
	 * @Descrption 将输入String转换为不含XML特殊标记的String
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 5:54:55 PM
	 */
	public static String escapeXMLstr(String input)
	{
		if (input == null)
			return null;

		StringBuffer output = new StringBuffer("");
		int len = input.length();
		for (int i = 0; i < len; i++)
		{
			char ch = input.charAt(i);
			if (ch == '&')
				output.append("&amp;");
			else if (ch == '<')
				output.append("&lt;");
			else if (ch == '>')
				output.append("&gt;");
			else if (ch == '\'')
				output.append("&apos;");
			else if (ch == '"')
				output.append("&quot;");
			else
				output.append(ch);
		}

		return output.toString();
	}

	/**
	 * 
	 * @Descrption  将输入String转换为不含HTML特殊标记的String
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 5:54:45 PM
	 */
	public static String escapeHTMLstr(String input)
	{
		if (input == null)
			return null;

		StringBuffer output = new StringBuffer("");
		int len = input.length();
		for (int i = 0; i < len; i++)
		{
			char ch = input.charAt(i);
			if (ch == '&')
				output.append("&amp;");
			else if (ch == '<')
				output.append("&lt;");
			else if (ch == '>')
				output.append("&gt;");
			else if (ch == '"')
				output.append("&quot;");
			else
				output.append(ch);
		}

		return output.toString();
	}
	
	private final static String[] JAVA_KEYWORDS =
		{
			"assert",
			"abstract",
			"continue",
			"for",
			"new",
			"switch",
			"boolean",
			"default",
			"goto",
			"null",
			"synchronized",
			"break",
			"do",
			"if",
			"package",
			"this",
			"byte",
			"double",
			"implements",
			"private",
			"threadsafe",
			"byvalue",
			"else",
			"import",
			"protected",
			"throw",
			"case",
			"extends",
			"instanceof",
			"public",
			"transient",
			"catch",
			"false",
			"int",
			"return",
			"true",
			"char",
			"final",
			"interface",
			"short",
			"try",
			"class",
			"finally",
			"long",
			"static",
			"void",
			"const",
			"float",
			"native",
			"super",
			"while",
			"volatile",
			"strictfp" };

	/**
	 * 
	 * @Descrption 是否合法的Java标识符
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 6:50:06 PM
	 */
	public static boolean isJavaIdentifier(String s)
	{
		if (isEmpty(s))
			return false;
		else
		{
			char[] chars = s.toCharArray();
			for (int i = 0; i < chars.length; i++)
			{
				if (!Character.isJavaIdentifierPart(chars[i])
					|| (i == 0 && !Character.isJavaIdentifierStart(chars[i])))
				{
					return false;
				}
			}
			if (Arrays.asList(JAVA_KEYWORDS).contains(s))
				return false;
		}
		return true;
	}

	/**
	 * @Descrption 是否合法的Java Class Name 
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 6:50:18 PM
	 */
	public static boolean isJavaClassName(String name)
	{
		if (isEmpty(name))
			return true;
		if (name.startsWith(" ")
			|| name.startsWith(".")
			|| name.endsWith(" ")
			|| name.endsWith("."))
		{
			return false;
		} else
		{
			String[] pks = name.split(".");
			for (int i = 0; i < pks.length; i++)
			{
				if (!isJavaIdentifier(pks[i]))
				{
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @Descrption 将一个实现序列化接口的Object转换成以base64编码的String.
	 * @param obj
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 6:50:29 PM
	 */
	public static String objToString(Object obj)
	{
		try
		{
			ByteArrayOutputStream byteOS = new ByteArrayOutputStream(2048);
			ObjectOutputStream objectOS = new ObjectOutputStream(byteOS);
			objectOS.writeObject(obj);
			objectOS.flush();
			return Base64Encoder.byteArrayToBase64(byteOS.toByteArray());
			
		} catch (Exception ex)
		{
			return null;
		}
	}
	/**
	 * 
	 * @Descrption 将一个以base64编码的String转换成一个Object.
	 * @author rd_jianbin_lin
	 * @Date Jul 15, 2017 6:50:42 PM
	 */
	public static Object strToObject(String str)
	{
		try
		{
			ObjectInputStream objectIN =
				new ObjectInputStream(
					new ByteArrayInputStream(Base64Encoder.base64ToByteArray(str)));
			
			Object obj = objectIN.readObject();
			return obj;
		} catch (Exception ex)
		{
			return null;
		}
	}

	public static void main(String[] args) {

		int i = 0;
		int j = 2;
		try {
			int t = j / i;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(stackToString(e));

		}

	}

}
