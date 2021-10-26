package bs.common.util;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

/**
 * Utilities methods
 * 
 * @since 1.0.0
 * @author Maikel Guerra Ferrer
 */
@Slf4j
public class ValueUtil implements Serializable {
	private static final long serialVersionUID = 9011977813506314643L;

	private ValueUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final String EMPTY = "";
	public static final String SPACE = " ";
	public static final String DASH = "-";
	public static final String NULL_SQL = "NULL";
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

	public static String generateUUID() {
		try {
			
			return UUIDGeneratorUtil.generateUniqueKeysWithUUIDAndMessageDigest();
			
		} catch (Exception e) {
			log.error("ValueUtil#generateUUID error {}", e.getMessage());
		}
		return EMPTY;
	}

	public static String str(final Object value) {
		try {

			if (ValidatorUtil.isNotEmpty(value))
				return value.toString();

		} catch (Exception e) {
			log.error("ValueUtil#str error {}", e.getMessage());
		}
		return null;
	}
	
	public static String str(final String str) {
		try {

			if (ValidatorUtil.isNotEmpty(str)) {
				return str.trim();
			}

		} catch (Exception e) {
			log.error("ValueUtil#str error {}", e.getMessage());
		}
		return null;
	}
	
	public static String strCapitalize(final String str) {
		try {

			if (ValidatorUtil.isNotEmpty(str)) {
				val capitalize = str.toLowerCase();
				return Character.toUpperCase(capitalize.charAt(0)) + capitalize.substring(1, capitalize.length());
			}

		} catch (Exception e) {
			log.error("ValueUtil#strCapitalize error {}", e.getMessage());
		}
		return null;
	}
	
	public static Character toChar(final String str) {
		try {

			if (ValidatorUtil.isNotEmpty(str))
				return str.trim().charAt(0);

		} catch (Exception e) {
			log.error("ValueUtil#toChar error {}", e.getMessage());
		}
		return null;
	}
	
	public static String nullableValue(final String str) {
		if(ValidatorUtil.isEmpty(str)) {
			return null;
		}
		return str.trim();
	}
	
	public static boolean contains(final String sentence, final String substring) {
		try {
			
			if(ValidatorUtil.isNotEmpty(sentence) && ValidatorUtil.isNotEmpty(substring)) {
				return StringUtils.contains(sentence, substring);
			}
			
		} catch (Exception e) {
			log.error("ValueUtil#isStrContainsSubstring error {}", e.getMessage());
		}
		return false;
	}
	
	public static String replaceAllCaseInsensitive(String sentence, String contains, String replaceWith) {
		try {
			
			if(ValidatorUtil.isNotEmpty(sentence) && ValidatorUtil.isNotEmpty(contains) && ValidatorUtil.isNotEmpty(replaceWith)) {
				val replaceCaseInsensitive = "(?i)" + contains;
				if(sentence.toUpperCase().contains(contains.toUpperCase())) {
					return sentence.replaceAll(replaceCaseInsensitive, replaceWith);
				}
			}
			
		} catch (Exception e) {
			log.error("ValueUtil#replaceAllCaseInsensitive error {}", e.getMessage());
		}
		return EMPTY;
	}
	
	public static String removeFirstCharacter(final String str) {
		try {
			
			return Optional.ofNullable(str)
				   	   	   .filter(sStr -> sStr.length() != 0)								
				   	   	   .map(sStr -> sStr.substring(1))
				   	   	   .orElse(str);
			
		} catch (Exception e) {
			log.error("ValueUtil#removeFirstCharacter(String) error {}", e.getMessage());
		}
		return null;
	}
	
	public static String removeFirstCharacter(final String str, final String character) {
		try {
			
			return Optional.ofNullable(str)
						   .filter(sStr -> sStr.length() != 0)
						   .filter(sStr -> sStr.startsWith(character))
						   .map(sStr -> sStr.substring(1))
						   .orElse(str);
			
		} catch (Exception e) {
			log.error("ValueUtil#removeFirstCharacter(String, String) error {}", e.getMessage());
		}
		return null;
	}
	
	public static String removeLastCharacter(String str) {
		try {
			
			return Optional.ofNullable(str)
						   .filter(sStr -> sStr.length() != 0)								
						   .map(sStr -> sStr.substring(0, sStr.length() - 1))
						   .orElse(str);
			
		} catch (Exception e) {
			log.error("ValueUtil#removeFirstAndLastCharacter(String) error {}", e.getMessage());
		}
		return null;
	}

	public static String removeLastCharacter(final String str, final String character) {
		try {
			
			return Optional.ofNullable(str)
						   .filter(sStr -> sStr.length() != 0)
						   .filter(sStr -> sStr.endsWith(character))
						   .map(sStr -> sStr.substring(0, sStr.length() - 1))
						   .orElse(str);
			
		} catch (Exception e) {
			log.error("ValueUtil#removeFirstAndLastCharacter(String, String) error {}", e.getMessage());
		}
		return null;
	}
	
	public static String removeFirstAndLastCharacter(final String str) {
		try {
			
			return Optional.ofNullable(str)
						   .filter(sStr -> sStr.length() != 0)								
						   .map(sStr -> sStr.substring(1, sStr.length() - 1))
						   .orElse(str);
			
		} catch (Exception e) {
			log.error("ValueUtil#removeFirstAndLastCharacter(String) error {}", e.getMessage());
		}
		return null;
	}
	
	public static String removeFirstAndLastCharacter(final String str, final String starts, final String ends) {
		try {
			
			return Optional.ofNullable(str)
						   .filter(sStr -> sStr.length() != 0)
						   .filter(sStr -> sStr.startsWith(starts))
						   .filter(sStr -> sStr.endsWith(ends))
						   .map(sStr -> sStr.substring(1, sStr.length() - 1))
						   .orElse(str);
			
		} catch (Exception e) {
			log.error("ValueUtil#removeFirstAndLastCharacter(String, String, String) error {}", e.getMessage());
		}
		return null;
	}

	public static byte[] stringToByte(final String str) {
		try {

			if (ValidatorUtil.isNotEmpty(str)) {
				return str.getBytes(StandardCharsets.UTF_8);
			}

		} catch (Exception e) {
			log.error("ValueUtil#stringToByte error {}", e.getMessage());
		}
		return null;
	}

	public static String byteToString(byte[] b) {
		try {

			return new String(b, StandardCharsets.UTF_8);

		} catch (Exception e) {
			log.error("ValueUtil#byteToString error {}", e.getMessage());
		}
		return ValueUtil.EMPTY;
	}

	public static String customReplace(String str) {
		try {

			if (ValidatorUtil.isNotEmpty(str)) {
				str = str.replace("&", "&amp;");
				str = str.replace("\"", "&quot;");
				str = str.replace("<", "&lt;");
				str = str.replace(">", "&gt;");
				str = str.replace("'", "&apos;");
			}

		} catch (Exception e) {
			log.error("ValueUtil#customReplace error {}", e.getMessage());
		}
		return str;
	}

	public static String customReplaceInverse(String str) {
		try {

			if (ValidatorUtil.isNotEmpty(str)) {
				str = str.replace("&amp;", "&");
				str = str.replace("&quot;", "\"");
				str = str.replace("&lt;", "<");
				str = str.replace("&gt;", ">");
				str = str.replace("&apos;", "'");
			}

		} catch (Exception e) {
			log.error("ValueUtil#customReplaceInverse error {}", e.getMessage());
		}
		return str;
	}
	
	public static String textReplace(final String text) {
		try {
			
			if(ValidatorUtil.isNotEmpty(text)) {
				return text.trim()
						   .toLowerCase()
						   .replaceAll(Pattern.quote(" "), "-")
						   .replaceAll(Pattern.quote("¿"), "")
						   .replaceAll(Pattern.quote("?"), "")
						   .replaceAll(Pattern.quote("¡"), "")
						   .replaceAll(Pattern.quote("!"), "")
						   .replaceAll(Pattern.quote("\""), "")
						   .replaceAll(Pattern.quote("<"), "")
						   .replaceAll(Pattern.quote(">"), "")
						   .replaceAll(Pattern.quote("'"), "")
						   .replaceAll(Pattern.quote(","), "")
						   .replaceAll(Pattern.quote("."), "")
						   .replaceAll(Pattern.quote("&"), "")
						   .replaceAll(Pattern.quote("@"), "")
						   .replaceAll(Pattern.quote("á"), "a")
						   .replaceAll(Pattern.quote("é"), "e")
						   .replaceAll(Pattern.quote("í"), "i")
						   .replaceAll(Pattern.quote("ó"), "o")
						   .replaceAll(Pattern.quote("ú"), "u")
						   .replaceAll(Pattern.quote("ü"), "u")
						   .replaceAll(Pattern.quote("ñ"), "n");
			}
			
		} catch (Exception e) {
			log.error("ValueUtil#textReplace error {}", e.getMessage());
		}
		return text;
	}
	
	public static String reverse(final String source) {
		try {
			
			if(ValidatorUtil.isNotEmpty(source)) {
				//##### Using own implementation
				/*
				for (int i = source.length() - 1; i >= 0; i--) {
					reverse = reverse + source.charAt(i);
				}*/
				
				//##### Using StringBuffer
				//reverse = new StringBuffer(source).reverse().toString();
				
				//##### Using StringBuilder
				return new StringBuilder(source).reverse().toString();
			}
			
		} catch (Exception e) {
			log.error("ValueUtil#reverse error {}", e.getMessage());
		}
		return ValueUtil.EMPTY;
	}

	public static String bytesToHex(byte[] bytes) {
		var hexChars = new char[bytes.length * 2];
		for (var j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
		}
		return new String(hexChars);
	}

	public static byte[] bytesFromUUID(String uuidHexString) {
		val normalizedUUIDHexString = uuidHexString.replace("-","");
		assert normalizedUUIDHexString.length() == 32;

		byte[] bytes = new byte[16];
		for (var i = 0; i < 16; i++) {
			val b = hexToByte(normalizedUUIDHexString.substring(i*2, i*2+2));
			bytes[i] = b;
		}
		return bytes;
	}

	public static byte hexToByte(String hexString) {
		int firstDigit = Character.digit(hexString.charAt(0),16);
		int secondDigit = Character.digit(hexString.charAt(1),16);
		return (byte) ((firstDigit << 4) + secondDigit);
	}

	public static byte[] joinBytes(byte[] byteArray1, byte[] byteArray2) {
		int finalLength = byteArray1.length + byteArray2.length;
		var result = new byte[finalLength];

		for(var i = 0; i < byteArray1.length; i++) {
			result[i] = byteArray1[i];
		}

		for(var i = 0; i < byteArray2.length; i++) {
			result[byteArray1.length+i] = byteArray2[i];
		}

		return result;
	}
}