package bs.common.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DecimalUtil implements Serializable {
	private static final long serialVersionUID = -9213056138924582663L;

	private DecimalUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String decimal(String str) {
		if(ValidatorUtil.isEmpty(str) || str.equals("0") || str.equals("0.0") || str.startsWith(".0")) {
			str = "0.00";
		}
		return str.trim();
	}
	
	public static Integer decimals(final String value){
		try {
			
			if(ValidatorUtil.isNotEmpty(value)) {
				int pos = value.indexOf(".");
				if(pos <= 0) {
					return 0;
				}
				
				return value.substring(pos + 1).length();
			} else {
				return 0;
			}
			
		} catch (Exception e) {
			log.error("UDecimal#decimals(String) error {}", e.getMessage());
		}
		return null;
	}
	
	public static Integer decimals(final BigDecimal value){
		try {
			
			if(ValidatorUtil.isEmpty(value) || value.scale() == 0) {
				return 0;
			}
			
			BigDecimal bgDecimalPart = value.subtract(value.setScale(0, RoundingMode.FLOOR)).movePointRight(value.scale());
			Integer decimalPart = bgDecimalPart.intValue();
			
			if(decimalPart == 0) {
				return 2;
			}
			return decimalPart.toString().length();
			
		} catch (Exception e) {
			log.error("UDecimal#decimals(BigDecimal) error {}", e.getMessage());
		}
		return null;
	}
	
	public static String decimalPart(final Double value) {
		try {
			
			if(ValidatorUtil.isEmpty(value)) {
				return ".00";
			}
			
			val doubleAsString = String.valueOf(value);
			return decimalPart(doubleAsString);
			
		} catch (Exception e) {
			log.error("UDecimal#decimalPart(Double) error {}", e.getMessage());
		}
		return null;
	}
	
	public static String decimalPart(final BigDecimal value) {
		try {
			
			if(ValidatorUtil.isEmpty(value)) {
				return ".00";
			}
			
			val bigDecimalAsString = String.valueOf(value);
			return decimalPart(bigDecimalAsString);
			
		} catch (Exception e) {
			log.error("UDecimal#decimalPart(BigDecimal) error {}", e.getMessage());
		}
		return null;
	}
	
	private static String decimalPart(final String value) {
		val indexOfDecimal = value.indexOf(".");
		
		//System.out.println("BigDecimal Number: " + value);
		//System.out.println("Integer Part: " + doubleAsString.substring(0, indexOfDecimal));
		//System.out.println("Decimal Part: " + doubleAsString.substring(indexOfDecimal));
		
		val result = value.substring(indexOfDecimal);
		if(ValidatorUtil.isEmpty(result) || result.equals(".0")) {
			return ".00";
		}
		return result;
	}
	
}