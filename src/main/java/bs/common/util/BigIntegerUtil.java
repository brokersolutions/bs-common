package bs.common.util;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BigIntegerUtil implements Serializable {
	private static final long serialVersionUID = -7986684093968896929L;

	private BigIntegerUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static BigInteger value(Integer number) {
		try {

			if (number != null) {
				return BigInteger.valueOf(number.intValue());
			}

		} catch (Exception e) {
			log.error("BigIntegerUtil#value error {}", e.getMessage());
		}
		return null;
	}

	public static BigInteger bigInteger(String str) {
		try {
			
			if(str != null && !str.isBlank() && !str.isEmpty()) {
				return new BigInteger(str);
			}			

		} catch (Exception e) {
			log.error("BigIntegerUtil#bigInteger error {}", e.getMessage());
		}
		return null;
	}
	
}