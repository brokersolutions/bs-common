package bs.common.util;

import java.io.Serializable;

public class MathUtil implements Serializable {
	private static final long serialVersionUID = -4424258832338413391L;

	private MathUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static boolean isInRange(int i, int minValueInclusive, int maxValueInclusive) {
		return (i >= minValueInclusive && i <= maxValueInclusive);
	}

	public static Integer greatestNumber(Integer n1, Integer n2) {
		if(n1 == null && n2 == null) {
			return null;
		} else if(n1 == null) {
			return n2;
		} else if(n2 == null) {
			return n1;
		} else if(n1 > n2) {
			return n1;
		}
		return n2;
	}

	public static Integer lowerNumber(Integer n1, Integer n2) {
		if(n1 == null && n2 == null) {
			return null;
		} else if(n1 == null) {
			return n2;
		} else if(n2 == null) {
			return n1;
		} else if(n1 < n2) {
			return n1;
		}
		return n2;
	}

}