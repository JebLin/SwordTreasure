package indi.sword.util.base.regex;

/**
 * 基础资料的Pattern适配符
 * @Descrption
 * @author rd_jianbin_lin
 * @Date Jun 22, 2017 5:21:48 PM
 */
public enum BaseRegexEnum {
	
	//中国大陆的手机号 13+任意数 、 15+除4的任意数 、 18+除1和4的任意数、  17+除9的任意数 、147 
	REGEX_CELL_CN("中国大陆的手机号","((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}"),
	
	//香港号码：5|6|8|9开头+7位任意数 
	REGEX_CELL_CN_HK("香港号码","(5|6|8|9)\\d{7}"),
	
	//邮箱
	REGEX_MAIL("邮箱","\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*");
	
	private final String key;
	
	private final String value;
	
	BaseRegexEnum(String key,String value){
		this.key = key;
		this.value = value;
	}
	
	public String key(){
		return this.key;
	}
	
	public String value(){
		return this.value;
	}
	
	public String fullValue(){
		return "^" + this.value + "$";
	}
	
}

