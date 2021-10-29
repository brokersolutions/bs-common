package bs.common.exception;

import bs.common.custom.BaseResponse;

public class SolveMessageException extends BaseException {
	private static final long serialVersionUID = -189365452227508599L;	
	
	public SolveMessageException() {
		super();
	}
	
	public SolveMessageException(String message) {
		super(message);
	}
	
	public SolveMessageException(String message, String cause) {
		super(message, cause);
	}
	
	public SolveMessageException(String message, Object[] params, String cause) {
		super(message, params, cause);
	}
	
	public SolveMessageException(BaseResponse response) {
		super(response);
	}
	
}