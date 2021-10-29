package bs.common.exception;

import bs.common.custom.BaseResponse;

public class UnexpectedErrorException extends BaseException {
	private static final long serialVersionUID = -189365452227508599L;
	
	public UnexpectedErrorException() {
		super();
	}
	
	public UnexpectedErrorException(String message) {
		super(message);
	}
	
	public UnexpectedErrorException(String message, String cause) {
		super(message, cause);
	}
	
	public UnexpectedErrorException(String message, Object[] params, String cause) {
		super(message, params, cause);
	}
	
	public UnexpectedErrorException(BaseResponse response) {
		super(response);
	}
	
}