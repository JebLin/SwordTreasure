package indi.sword.util.base.bean;

/**
 * 
 * 后续添加exceptionMsg属性，存储异常信息
 * 
 * @author rd_jianbin_lin update
 * @date Feb 6, 2017 2:42:03 PM
 */
public class Result {

	// 返回认证状态码
	private int code;
	// 返回请求的数据
	private Object data;
	// 返回响应详细描述（报错的时候）
	private String info;

	public Result(){
		
	}
	
	public static class Builder {

		// 返回认证状态码
		private int code;
		// 返回请求的数据
		private Object data;
		// 返回响应详细描述（报错的时候）
		private String info;

		public int getCode() {
			return code;
		}

		private Builder code(int var) {
			this.code = var;
			return this;
		}

		public String getInfo() {
			return info;
		}

		private Builder info(String var) {
			this.info = var;
			return this;
		}

		public Object getData() {
			return data;
		}

		private Builder data(Object var) {
			this.data = var;
			return this;
		}
		
		public static Result getInstance(Builder builder){
			Result result = new Result();
			result.setCode(builder.getCode());
			result.setInfo(builder.getInfo());
			result.setData(builder.getData());
			return result;
		}
	}

	public int getCode() {
		return code;
	}

	public String getInfo() {
		return info;
	}

	public Object getData() {
		return data;
	}
	private void setCode(int code) {
		this.code = code;
	}

	private void setData(Object data) {
		this.data = data;
	}

	private void setInfo(String info) {
		this.info = info;
	}

	public static void main(String[] args){
		Result.Builder builder = new Result.Builder();
		builder.code(1).data("2").info("3");
		Result result = Builder.getInstance(builder);
		System.out.println(result.getCode());
	}
}
