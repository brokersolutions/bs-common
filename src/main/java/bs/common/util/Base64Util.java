package bs.common.util;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import jakarta.xml.bind.DatatypeConverter;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Base64Util implements Serializable {
	private static final long serialVersionUID = -6821604629293537172L;

	private Base64Util() {
		throw new IllegalStateException("Utility class");
	}

	public static String encode(final String value) {
		try {

			val data = ValueUtil.str(value);
			if (ValidatorUtil.isNotEmpty(data)) {
				return b64Encode(data, true);
			}

		} catch (Exception e) {
			log.error("UBase64#encode error {}", e);
		}
		return ValueUtil.EMPTY;
	}
	
	public static String encode(final String value, final Boolean padding) {
		try {
			
			val data = ValueUtil.str(value);
			if (ValidatorUtil.isNotEmpty(data)) {
				return b64Encode(data, padding);
			}
			
		} catch (Exception e) {
			log.error("UBase64#encodeWP error {}", e);
		}
		return ValueUtil.EMPTY;
	}
	
	private static String b64Encode(final String data, final Boolean padding) {
		if(Boolean.TRUE.equals(padding)) {
			return Base64.getEncoder().withoutPadding().encodeToString(data.getBytes(StandardCharsets.UTF_8));
		} else {
			return Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
		}
	}

	public static String decode(final String value) {
		try {

			if (ValidatorUtil.isNotEmpty(value)) {
				byte[] decodedBytes = Base64.getDecoder().decode(value.getBytes());
				return new String(decodedBytes, StandardCharsets.UTF_8);
			}

		} catch (Exception e) {
			log.error("UBase64#decode error {}", e);
		}
		return ValueUtil.EMPTY;
	}
	
	public static String urlEncode(final String url) {
		try {
			
			val data = ValueUtil.str(url);
			if (ValidatorUtil.isNotEmpty(data)) {
				return Base64.getUrlEncoder().withoutPadding().encodeToString(data.getBytes(StandardCharsets.UTF_8));
			}
			
		} catch (Exception e) {
			log.error("UBase64#urlEncode error {}", e);
		}
		return ValueUtil.EMPTY;
	}
	
	public static String urlDecode(final String url) {
		try {

			if (ValidatorUtil.isNotEmpty(url)) {
				byte[] decodedBytes = Base64.getUrlDecoder().decode(url.getBytes(StandardCharsets.UTF_8));
				return new String(decodedBytes, StandardCharsets.UTF_8);
			}

		} catch (Exception e) {
			log.error("UBase64#urlDecode error {}", e);
		}
		return ValueUtil.EMPTY;
	}

	public static byte[] byteFromBase64(String token) {
		try {

			if (token != null) {
				return DatatypeConverter.parseBase64Binary(token);
			}

		} catch (Exception e) {
			log.error("UBase64#byteFromBase64 error {}", e.getMessage());
		}
		return new byte[0];
	}

	public static String byteEncode(byte[] value) {
		String digitalSign = null;
		try {

			byte[] bytes = Base64.getEncoder().encode(value);

			digitalSign = new String(bytes);
			digitalSign = digitalSign.replace("\r\n", "");
			digitalSign = digitalSign.replace("\n", "");
			digitalSign = digitalSign.replace("\r", "");

		} catch (Exception e) {
			log.error("UBase64#byteEncode error {}", e.getMessage());
		}
		return digitalSign;
	}
}
