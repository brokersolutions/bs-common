package bs.common.util;

import java.io.Serializable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShortUtil implements Serializable {
	private static final long serialVersionUID = -2668551968057972446L;

	private ShortUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Short value(final String str) {
		try {

			if (ValidatorUtil.isNotEmpty(str)) {
				return Short.parseShort(str);
			}

		} catch (Exception e) {
			log.error("ShortUtil#value(String) error {}", e.getMessage());		
		}
		return null;
	}
	
	public static String value(final Short sh) {
		try {

			if (ValidatorUtil.isEmpty(sh)) {
				return Short.toString(sh);
			}

		} catch (Exception e) {
			log.error("ShortUtil#value(Short) error {}", e.getMessage());		
		}
		return null;
	}
	
}