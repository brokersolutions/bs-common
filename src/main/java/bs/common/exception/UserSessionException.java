package bs.common.exception;

import bs.common.custom.BaseResponse;

public class UserSessionException extends BaseException {
	private static final long serialVersionUID = -189365452227508599L;	
	
	public UserSessionException() {
		super();
	}
	
	public UserSessionException(String message) {
		super(message);
	}
	
	public UserSessionException(String message, String cause) {
		super(message, cause);
	}
	
	public UserSessionException(String message, Object[] params, String cause) {
		super(message, params, cause);
	}
	
	public UserSessionException(BaseResponse response) {
		super(response);
	}
	
}