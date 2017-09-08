package indi.sword.util.base.bean;

/**
 * 
 * 后续添加exceptionMsg属性，存储异常信息
 * @author rd_jianbin_lin update
 * @date Feb 6, 2017 2:42:03 PM
 */
public class Result {

	//返回认证状态码
	private int code;
	//返回请求的数据
	private Object data;
	//返回响应详细描述（报错的时候）
	private String info;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}

