package bs.common.util;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LongUtil implements Serializable {
	private static final long serialVersionUID = -9166840619780289717L;

	private LongUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Long value(final String str) {
		try {

			if (ValidatorUtil.isNotEmpty(str)) {
				return Long.valueOf(str);
			}

		} catch (Exception e) {
			log.error("LongUtil#value(String) error {}", e.getMessage());
		}
		return null;
	}
	
	public static Long value(final Integer intVal) {
		try {

			if (intVal != null) {
				return Long.valueOf(intVal);
			}

		} catch (Exception e) {
			log.error("LongUtil#value(Integer) error {}", e.getMessage());
		}
		return null;
	}

	public static String value(final Long l) {
		try {

			if (l != null) {
				return Long.toString(l);
			}

		} catch (Exception e) {
			log.error("LongUtil#value(Long) error {}", e.getMessage());
		}
		return null;
	}

	public static Long value(BigInteger bi) {
		try {

			if (bi != null) {
				return bi.longValue();
			}

		} catch (Exception e) {
			log.error("LongUtil#value(BigInteger) error {}", e.getMessage());
		}
		return null;
	}

	public static Long value(final Number number) {
		try {

			if (number != null) {
				return number.longValue();
			}

		} catch (Exception e) {
			log.error("LongUtil#value(Number) error {}", e.getMessage());
		}
		return null;
	}
}