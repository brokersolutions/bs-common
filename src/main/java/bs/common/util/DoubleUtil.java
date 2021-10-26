package bs.common.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DoubleUtil implements Serializable {
	private static final long serialVersionUID = 7913231677807936569L;

	private DoubleUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final DecimalFormat DECIMAL_FORMAT_DEFAULT = new DecimalFormat("#,###.00");
	public static final DecimalFormat DECIMAL_FORMAT_WITHOUT_DECIMALS = new DecimalFormat("#,###");
	public static final RoundingMode HALF_UP = RoundingMode.HALF_UP;
	
	public static Double dValue(String str) {
		try {

			if(ValidatorUtil.isNotEmpty(str)) {
				str = DecimalUtil.decimal(str);
				val d = Double.valueOf(str.replace(",", ""));
				val value = DECIMAL_FORMAT_DEFAULT.format(d);
				return Double.parseDouble(value.replace(",", ""));
			}

		} catch (Exception e) {
			log.error("UDouble#dValue(String) error {}", e.getMessage());
		}
		return null;
	}
	
	public static Double dValueWithoutDecimals(String str) {
		try {

			if(ValidatorUtil.isNotEmpty(str)) {
				str = DecimalUtil.decimal(str);
				val d = Double.valueOf(str.replace(",", ""));
				val value = DECIMAL_FORMAT_WITHOUT_DECIMALS.format(d);
				return Double.parseDouble(value.replace(",", ""));
			}

		} catch (Exception e) {
			log.error("UDouble#dValueWithoutDecimals error {}", e.getMessage());
		}
		return null;
	}

	public static Double dValue(Integer i) {
		try {

			if(i != null) {
				return i.doubleValue();
			}

		} catch (Exception e) {
			log.error("UDouble#dValue(Integer) error {}", e.getMessage());
		}
		return null;
	}
	
	public static Double dValue(BigDecimal b) {
		try {

			if(b != null) {
				return b.doubleValue();
			}

		} catch (Exception e) {
			log.error("UDouble#dValue(BigDecimal) error {}", e.getMessage());
		}
		return null;
	}
	
	public static String dValue(Double d) {
		try {

			if (d != null) {
				var str = DECIMAL_FORMAT_DEFAULT.format(d);
				if(str.startsWith(".")) {
					str = "0".concat(str);
				}
				return str;
			}

		} catch (Exception e) {
			log.error("UDouble#dValue(Double) error {}", e.getMessage());
		}
		return null;
	}
	
	public static String integerPart(final Double value) {
		try {
			
			if(ValidatorUtil.isNotEmpty(value)) {
				val doubleAsString = String.valueOf(value);
				val indexOfDecimal = doubleAsString.indexOf(".");
				
				var dValue = Double.valueOf(doubleAsString.substring(0, indexOfDecimal).replace(",", ""));
				
				val df = new DecimalFormat("#,###");
				return df.format(dValue);
			}
			
		} catch (Exception e) {
			log.error("UDouble#integerPart error {}", e.getMessage());
		}
		return null;
	}
	
	public static Double round(final Double value) {
		try {
			
			if(value != null) {
				var bd = BigDecimal.valueOf(value);
				bd = bd.setScale(2, HALF_UP); //##### 2 decimal places
				return dValue(bd);
			}
			
		} catch (Exception e) {
			log.error("UDouble#round error {}", e.getMessage());
		}
		return null;
	}
	
	public static Double roundCommision(final Double value) {
		try {
			
			if(value != null) {
				//##### Rounding initially to 2 decimal places
				val dVal = round(value);
				
				val strVal = DoubleUtil.dValue(dVal);
				if(strVal != null) {
					val strSplitted = strVal.split(Pattern.quote("."));
					
					int intVal = IntegerUtil.value(strSplitted[0]); 
					int result = intVal % 10; 
					
					do {
						intVal ++;
						result = intVal % 10;
					} while (result != 0);
					
					return DoubleUtil.dValue(intVal);
				}
			}
			
		} catch (Exception e) {
			log.error("UDouble#roundCommision error {}", e.getMessage());
		}
		return null;
	}
}