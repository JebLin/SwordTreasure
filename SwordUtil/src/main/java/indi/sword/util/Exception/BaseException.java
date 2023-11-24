package indi.sword.util.Exception;

/**
 * @Description 基础异常类
 * @Author:rd_jianbin_lin
 * @Date: 11:06 2017/9/14
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = 2323866548746629075L;

	public BaseException() {
		super();
	}

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

    public BaseException(String message, Throwable cause,
                         boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
