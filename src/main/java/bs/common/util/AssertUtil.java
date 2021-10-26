package bs.common.util;

import java.io.Serializable;

import org.apache.commons.lang3.Validate;

import bs.common.exception.BaseException;

public class AssertUtil implements Serializable {
	private static final long serialVersionUID = -201735594018975777L;

	private AssertUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static void isTrue(final boolean expression, BaseException exception) throws BaseException {
		try {
			
			Validate.notNull(expression);
			Validate.isTrue(expression);
			
		} catch (NullPointerException | IllegalArgumentException e) {
			throw exception;
		}
	}
	
	public static void isTrue(final boolean expression, final String message, BaseException exception) throws BaseException {
		try {
			
			Validate.notNull(expression, message);
			Validate.isTrue(expression, message);
			
		} catch (NullPointerException | IllegalArgumentException e) {
			throw exception;
		}
	}
	
}