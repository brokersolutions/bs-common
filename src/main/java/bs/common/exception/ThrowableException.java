package bs.common.exception;

import bs.common.wrapper.WResponse;
import bs.common.wrapper.WThrowable;

public class ThrowableException extends BaseException {
	private static final long serialVersionUID = -189365452227508599L;	
	
	public ThrowableException() {
		super();
	}
	
	public ThrowableException(String message) {
		super(message);
	}
	
	public ThrowableException(String message, String cause) {
		super(message, cause);
	}
	
	public ThrowableException(String message, Object[] params, String cause) {
		super(message, params, cause);
	}
	
	public ThrowableException(WResponse response) {
		super(response);
	}
	
	public ThrowableException(WThrowable throwable) {
		super(throwable);
	}
	
}