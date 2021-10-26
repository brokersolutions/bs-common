package bs.common.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import bs.common.util.MessageUtil;
import bs.common.util.ValidatorUtil;

@Data
@NoArgsConstructor
public class ErrorBean {
	public static final String BAD_REQUEST = MessageUtil.getMessage(ValidatorUtil.BAD_REQUEST);
	public static final String UNEXPECTED_ERROR = MessageUtil.getMessage(ValidatorUtil.UNEXPECTED_ERROR);
	public static final String THROWABLE_INTERNAL_SERVER_ERROR = MessageUtil.getMessage(ValidatorUtil.THROWABLE_INTERNAL_SERVER_ERROR);
	public static final String THROWABLE_RESOURCE_DENIED = MessageUtil.getMessage(ValidatorUtil.THROWABLE_RESOURCE_DENIED);
	public static final String THROWABLE_RESOURCE_NOT_AVAILABLE = MessageUtil.getMessage(ValidatorUtil.THROWABLE_RESOURCE_NOT_AVAILABLE);
	public static final String THROWABLE_UNAUTHORIZED = MessageUtil.getMessage(ValidatorUtil.THROWABLE_UNAUTHORIZED);
	public static final String THROWABLE_SESSION_ERROR = MessageUtil.getMessage(ValidatorUtil.THROWABLE_SESSION_ERROR);
	
	private Boolean error = false;
	private String code;
	private String message;
	private String description;
	
	public ErrorBean(final String message) {
		this.error = true;
		this.message = message;
	}
	
	public ErrorBean(final String message, final String description) {
		this.error = true;
		this.message = message;
		this.description = description;
	}
	
	public ErrorBean(final String code, final String message, final String description) {
		this.error = true;
		this.code = code;
		this.message = message;
		this.description = description;
	}
	
	public static ErrorBean badRequest() {
		return new ErrorBean(BAD_REQUEST);
	}
	
	public static ErrorBean unexpectedError() {
		return new ErrorBean(UNEXPECTED_ERROR);
	}
	
	public static ErrorBean throwableInternalServerError() {
		return new ErrorBean(THROWABLE_INTERNAL_SERVER_ERROR, MessageUtil.getMessage(ValidatorUtil.THROWABLE_INTERNAL_SERVER_ERROR_MESSAGE));
	}
	
	public static ErrorBean throwableResourceDenied() {
		return new ErrorBean(THROWABLE_RESOURCE_DENIED, MessageUtil.getMessage(ValidatorUtil.THROWABLE_RESOURCE_DENIED_MESSAGE));
	}
	
	public static ErrorBean throwableResourceNotAvailable() {
		return new ErrorBean(THROWABLE_RESOURCE_NOT_AVAILABLE, MessageUtil.getMessage(ValidatorUtil.THROWABLE_RESOURCE_NOT_AVAILABLE_MESSAGE));
	}
	
	public static ErrorBean throwableUnauthorized() {
		return new ErrorBean(THROWABLE_UNAUTHORIZED, MessageUtil.getMessage(ValidatorUtil.THROWABLE_UNAUTHORIZED_MESSAGE));
	}
	
	public static ErrorBean throwableSessionError() {
		return new ErrorBean(THROWABLE_SESSION_ERROR, MessageUtil.getMessage(ValidatorUtil.THROWABLE_SESSION_ERROR_MESSAGE));
	}
	
}