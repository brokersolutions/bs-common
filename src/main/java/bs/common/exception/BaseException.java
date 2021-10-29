package bs.common.exception;

import lombok.Getter;

import org.springframework.http.HttpStatus;

import bs.common.custom.BaseResponse;
import bs.common.custom.CustomRedAttr;

@Getter
public abstract class BaseException extends Exception {
	private static final long serialVersionUID = -189365452227508599L;
	
	protected HttpStatus status = null;
	protected Object data = null;
	protected Object[] params = null;
	protected String exception = null;
	protected BaseResponse response = null;
	protected CustomRedAttr throwable = null;
	
	protected BaseException() {
		super();
	}
	
	protected BaseException(String message) {
		super(message);
	}
	
	protected BaseException(final Throwable cause) {
		super(cause);
		this.exception = cause.getMessage();
	}
	
	protected BaseException(final String message, final Throwable cause) {
		super(message, cause);
		this.exception = cause.getMessage();
	}
	
	protected BaseException(final String message, final Object[] params, final Throwable cause) {
		super(message, cause);
		this.exception = cause.getMessage();
		this.params = params;
	}
	
	protected BaseException(String message, String cause) {
		super(message);
		this.exception = cause;
	}
	
	protected BaseException(String message, Object[] params, String cause) {
		super(message);
		this.params = params;
		this.exception = cause;
	}
	
	protected BaseException(BaseResponse response) {
		super();
		this.response = response;
	}
	
	protected BaseException(CustomRedAttr throwable) {
		super();
		this.throwable = throwable;
	}
	
	public BaseException(HttpStatus status, String message) {
		this(message);
		this.status = status;
	}

	public BaseException(HttpStatus status, String message, Object data) {
		this(status, message);
		this.data = data;
	}
	
}