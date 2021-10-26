package bs.common.util;

import java.io.Serializable;

import bs.common.enums.EApiGeneric;
import bs.common.wrapper.WResponse;

public class ErrorUtil implements Serializable  {
	private static final long serialVersionUID = -8025981511221163429L;
	
	private ErrorUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final WResponse UNEXPECTED_ERROR = ResponseUtil.response(ValidatorUtil.UNEXPECTED_ERROR, null, EApiGeneric.NULL);
	public static final WResponse DATA_EXCEPTION = ResponseUtil.response(ValidatorUtil.UNEXPECTED_ERROR, null, EApiGeneric.DATA_INTEGRITY_VIOLATION_EXCEPTION);
	public static final WResponse DATA_INTEGRITY_VIOLATION = ResponseUtil.response(ValidatorUtil.UNEXPECTED_ERROR, null, EApiGeneric.DATA_INTEGRITY_VIOLATION_EXCEPTION);
	public static final WResponse FORBIDDEN_REQUEST_EXCEPTION = ResponseUtil.response(ValidatorUtil.UNEXPECTED_ERROR, null, EApiGeneric.FORBIDDEN_REQUEST_EXCEPTION);
	
}