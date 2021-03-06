package bs.common.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import lombok.val;
import lombok.extern.slf4j.Slf4j;
import bs.common.util.DateTimeUtil.Formatter;

@Slf4j
public class ValidatorUtil implements Serializable {
	private static final long serialVersionUID = -6770441566967388488L;

	private ValidatorUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * <p>
	 * Used for Java Bean Validation
	 * </p>
	 * <b>messages_es_MX.properties:</b> El valor de este campo es requerido <br>
	 * <b>messages_en_US.properties:</b>
	 */
	public static final String REQUIRED = "{validator.text.required}";
	/**
	 * <p>
	 * Used for Error Validation
	 * </p>
	 * <b>messages_es_MX.properties:</b> El valor de este campo es requerido <br>
	 * <b>messages_en_US.properties:</b> <br>
	 */
	public static final String REQUIRED_MESSAGE = "validator.text.required";

	/**
	 * <p>
	 * Used for Error Validation
	 * </p>
	 * <b>messages_es_MX.properties:</b> El {0} es requerido <br>
	 * <b>messages_en_US.properties:</b> <br>
	 */
	public static final String REQUIRED_M_MESSAGE = "validator.text.required.m";

	/**
	 * <p>
	 * Used for Error Validation
	 * </p>
	 * <b>messages_es_MX.properties:</b> La {0} es requerida <br>
	 * <b>messages_en_US.properties:</b> <br>
	 */
	public static final String REQUIRED_F_MESSAGE = "validator.text.required.f";

	// ##### messages_es_MX.properties: no puede estar vacío
	// ##### messages_en_US.properties:
	public static final String NOT_EMPTY = "{validator.text.not.empty}";
	public static final String NOT_EMPTY_MESSAGE = "validator.text.not.empty";

	// ##### messages_es_MX.properties: El valor de este campo no es válido
	// ##### messages_en_US.properties:
	public static final String INVALID_FIELD = "{validator.text.invalid.field}";
	public static final String INVALID_FIELD_MESSAGE = "validator.text.invalid.field";

	// ##### messages_es_MX.properties: El {0} no es válido
	// ##### messages_en_US.properties:
	public static final String INVALID_M_MESSAGE = "validator.text.invalid.field";

	// ##### messages_es_MX.properties: La {0} no es válida
	// ##### messages_en_US.properties:
	public static final String INVALID_F_MESSAGE = "validator.text.invalid.field";

	// ##### messages_es_MX.properties: Formato no válido
	// ##### messages_en_US.properties:
	public static final String INVALID_FORMAT = "{validator.text.invalid.format}";
	public static final String INVALID_FORMAT_MESSAGE = "validator.text.invalid.format";

	// ##### messages_es_MX.properties: Este campo solo acepta letras
	// ##### messages_en_US.properties:
	public static final String INVALID_ALPHABETIC = "{validator.text.alphabetic.field}";
	public static final String INVALID_ALPHABETIC_MESSAGE = "validator.text.alphabetic.field";

	// ##### messages_es_MX.properties: Este campo solo acepta números
	// ##### messages_en_US.properties:
	public static final String INVALID_NUMERIC = "{validator.text.numeric.field}";
	public static final String INVALID_NUMERIC_MESSAGE = "validator.text.numeric.field";

	// ##### messages_es_MX.properties: El correo especificado no es válido
	// ##### messages_en_US.properties:
	public static final String INVALID_EMAIL = "{validator.text.invalid.email}";
	public static final String INVALID_EMAIL_MESSAGE = "validator.text.invalid.email";

	// ##### messages_es_MX.properties: El teléfono especificado no es válido
	// ##### messages_en_US.properties:
	public static final String INVALID_PHONE = "{validator.text.invalid.phone}";
	public static final String INVALID_PHONE_MESSAGE = "validator.text.invalid.phone";

	// ##### messages_es_MX.properties: El teléfono especificado no es válido
	// ##### messages_en_US.properties:
	public static final String INVALID_ZIP_CODE = "{validator.text.invalid.zip.code}";
	public static final String INVALID_ZIP_CODE_MESSAGE = "validator.text.invalid.zip.code";

	// ##### messages_es_MX.properties: La fecha especificada no es válida o se
	// encuentra fuera de rango
	// ##### messages_en_US.properties:
	public static final String INVALID_DATE_OUT_RANGE = "{validator.text.invalid.date.out.range}";
	public static final String INVALID_DATE_OUT_RANGE_MESSAGE = "validator.text.invalid.date.out.range";

	// ##### messages_es_MX.properties: El valor de este campo debe ser mayor que
	// {min}
	// ##### messages_en_US.properties:
	public static final String INVALID_FIELD_GREATER_THAN_VALUE = "{validator.text.invalid.field.greater.than.value}";
	// ##### messages_es_MX.properties: El valor de este campo debe ser mayor que
	// {0}
	// ##### messages_en_US.properties:
	public static final String INVALID_FIELD_GREATER_THAN_MESSAGE = "validator.text.invalid.field.greater.than";
	// ##### messages_es_MX.properties: El valor de este campo debe ser mayor que
	// ##### messages_en_US.properties:
	public static final String INVALID_FIELD_GREATER_THAN_VALIDATION = "validator.text.invalid.field.greater.than.validation";

	/**
	 * <b>messages_es_MX.properties:</b> El mínimo de caracteres permitido es {min}
	 * <br>
	 * <b>messages_en_US.properties:</b>
	 * 
	 */
	public static final String INVALID_MIN_LENGTH = "{validator.text.field.min.invalid.length}";
	// ##### messages_es_MX.properties: El mínimo de caracteres permitido es {0}
	// ##### messages_en_US.properties:
	public static final String INVALID_MIN_LENGTH_MESSAGE = "validator.text.field.min.length";
	// ##### messages_es_MX.properties: El mínimo de caracteres permitido es
	// ##### messages_en_US.properties:
	public static final String INVALID_MIN_LENGTH_VALIDATION = "validator.text.field.min.validation";

	// ##### messages_es_MX.properties: El máximo de caracteres permitido es {max}
	// ##### messages_en_US.properties:
	public static final String INVALID_MAX_LENGTH = "{validator.text.field.max.invalid.length}";
	// ##### messages_es_MX.properties: El máximo de caracteres permitido es {0}
	// ##### messages_en_US.properties:
	public static final String INVALID_MAX_LENGTH_MESSAGE = "validator.text.field.max.length";
	// ##### messages_es_MX.properties: El máximo de caracteres permitido es
	// ##### messages_en_US.properties:
	public static final String INVALID_MAX_LENGTH_VALIDATION = "validator.text.field.max.validation";

	// ##### messages_es_MX.properties: Este campo debe contener entre {min} y {max}
	// caracteres
	// ##### messages_en_US.properties:
	public static final String INVALID_MIN_MAX_LENGTH = "{validator.text.field.min.max.invalid.length}";
	// ##### messages_es_MX.properties: Este campo debe contener entre {0} y {1}
	// caracteres
	// ##### messages_en_US.properties:
	public static final String INVALID_MIN_MAX_LENGTH_MESSAGE = "validator.text.field.min.max.length";
	// ##### messages_es_MX.properties: Este campo debe contener entre
	// ##### messages_en_US.properties:
	public static final String INVALID_MIN_MAX_LENGTH_VALIDATION = "validator.text.field.min.max.validation";

	// ##### messages_es_MX.properties: El máximo de dígitos permitido es {max}
	// ##### messages_en_US.properties:
	public static final String INVALID_MAX_DIGIT_LENGTH = "{validator.text.field.max.invalid.digits.length}";
	// ##### messages_es_MX.properties: El máximo de dígitos permitido es {0}
	// ##### messages_en_US.properties:
	public static final String INVALID_MAX_DIGIT_LENGTH_MESSAGE = "validator.text.field.max.digits.length";
	// ##### messages_es_MX.properties: El máximo de dígitos permitido es
	// ##### messages_en_US.properties:
	public static final String INVALID_MAX_DIGIT_LENGTH_VALIDATION = "validator.text.field.max.digits.validation";
	// ##### messages_es_MX.properties: Este campo debe contener {0} dígitos
	// ##### messages_en_US.properties:
	public static final String INVALID_DIGIT_LENGTH_MESSAGE = "validator.text.field.min.max.digits.length";

	// ##### messages_es_MX.properties: tiene que corresponder a la expresión
	// regular
	// ##### messages_en_US.properties:
	public static final String INVALID_REGEXP_VALIDATION = "validator.text.field.regexp";

	// ##### messages_es_MX.properties: Debe seleccionar una imagen
	// ##### messages_en_US.properties:
	public static final String SINGLE_FILE_IMG_MISSING_MESSAGE = "validator.text.single.file.img.missing";
	// ##### messages_es_MX.properties: Debe seleccionar al menos una imagen
	// ##### messages_en_US.properties:
	public static final String MULTIPLE_FILE_IMG_MISSING_MESSAGE = "validator.text.multiple.file.img.missing";
	// ##### messages_es_MX.properties: Debe seleccionar un archivo
	// ##### messages_en_US.properties:
	public static final String SINGLE_FILE_MISSING_MESSAGE = "validator.text.single.file.missing";
	// ##### messages_es_MX.properties: Debe seleccionar al menos un archivo
	// ##### messages_en_US.properties:
	public static final String MULTIPLE_FILE_MISSING_MESSAGE = "validator.text.multiple.file.missing";
	// ##### messages_es_MX.properties: Extensión de archivo no válida
	// ##### messages_en_US.properties:
	public static final String INVALID_FILE_EXTENSION_MESSAGE = "validator.text.invalid.file.extension";
	// ##### messages_es_MX.properties: El tamaño del archivo excede el permitido
	// ##### messages_en_US.properties:
	public static final String INVALID_FILE_SIZE_MESSAGE = "validator.text.invalid.file.size";
	// ##### messages_es_MX.properties: Tamaño de archivo inválido
	// ##### messages_en_US.properties:
	public static final String INVALID_FILE_SIZE_VALIDATION = "validator.text.invalid.file.size.validation";
	// ##### messages_es_MX.properties: Tamaño de archivo inválido (Excede {0}KB)
	// ##### messages_en_US.properties:
	public static final String INVALID_FILE_SIZE_KB_MESSAGE = "validator.text.invalid.file.size.kb";
	// ##### messages_es_MX.properties: Tamaño de archivo inválido (Excede {0}MB)
	// ##### messages_en_US.properties:
	public static final String INVALID_FILE_SIZE_MB_MESSAGE = "validator.text.invalid.file.size.mb";
	// ##### messages_es_MX.properties: Tamaño de archivo inválido (Excede {0}GB)
	// ##### messages_en_US.properties:
	public static final String INVALID_FILE_SIZE_GB_MESSAGE = "validator.text.invalid.file.size.gb";

	// ##### messages_es_MX.properties: La contraseña debe tener una longitud entre
	// 8 y 30 caracteres. Debe contener letras mayúsculas, números y caracteres
	// especiales
	// ##### messages_en_US.properties:
	public static final String INVALID_PASSWORD = "{validator.text.invalid.password}";
	public static final String INVALID_PASSWORD_MESSAGE = "validator.text.invalid.password";

	// ##### messages_es_MX.properties: La contraseña actual no es correcta
	// ##### messages_en_US.properties:
	public static final String INVALID_OLD_PASSWORD = "{validator.text.invalid.old.password}";
	public static final String INVALID_OLD_PASSWORD_MESSAGE = "validator.text.invalid.old.password";

	// ##### messages_es_MX.properties: Las contraseñas no coinciden
	// ##### messages_en_US.properties:
	public static final String INVALID_PASSWORD_MATCHING = "{validator.text.matching.password}";
	public static final String INVALID_PASSWORD_MATCHING_MESSAGE = "validator.text.matching.password";

	// ##### messages_es_MX.properties: La nueva contraseña debe ser diferente a la
	// contraseña actual
	// ##### messages_en_US.properties:
	public static final String INVALID_PASSWORD_DIFFERENT = "{validator.text.different.password}";
	public static final String INVALID_PASSWORD_DIFFERENT_MESSAGE = "validator.text.different.password";

	// ##### messages_es_MX.properties: El cupón especificado no es válido
	// ##### messages_en_US.properties:
	public static final String INVALID_COUPON = "{validator.text.invalid.coupon}";
	public static final String INVALID_COUPON_MESSAGE = "validator.text.invalid.coupon";

	// ##### messages_es_MX.properties: Ya has utilizado este cupón previamente
	// ##### messages_en_US.properties:
	public static final String INVALID_USED_COUPON_MESSAGE = "validator.text.used.coupon";

	// ##### messages_es_MX.properties: Debes iniciar sesión para realizar esta
	// acción
	// ##### messages_en_US.properties:
	public static final String USER_NO_AUTHENTICATED_ACTION = "{text.error.user.no.authenticated.action}";
	public static final String USER_NO_AUTHENTICATED_ACTION_MESSAGE = "text.error.user.no.authenticated.action";

	// ##### messages_es_MX.properties: Ya existe una cuenta con la dirección de
	// correo especificada
	// ##### messages_en_US.properties:
	public static final String USER_ALREADY_EXISTS = "{validator.text.user.already.exists}";
	public static final String USER_ALREADY_EXISTS_MESSAGE = "validator.text.user.already.exists";

	// ##### messages_es_MX.properties: Ya existe una cuenta con el RFC especificado
	// ##### messages_en_US.properties:
	public static final String USER_RFC_ALREADY_EXISTS = "{validator.text.user.rfc.already.exists}";
	public static final String USER_RFC_ALREADY_EXISTS_MESSAGE = "validator.text.user.rfc.already.exists";

	// ##### messages_es_MX.properties: Ya existe una cuenta con la razón social
	// especificada
	// ##### messages_en_US.properties:
	public static final String USER_BNAME_ALREADY_EXISTS = "{validator.text.user.bname.already.exists}";
	public static final String USER_BNAME_ALREADY_EXISTS_MESSAGE = "validator.text.user.bname.already.exists";

	// ##### messages_es_MX.properties: No existe ninguna cuenta activa que corresponda al correo electrónico especificado
	// ##### messages_en_US.properties:
	public static final String USER_UNKNOWN = "{validator.text.user.unknown}";
	public static final String USER_UNKNOWN_MESSAGE = "validator.text.user.unknown";

	// ##### ===== BAD REQUEST =====
	/**
	 * <b>messages_es_MX.properties:</b> Solicitud no válida <br>
	 * <b>messages_en_US.properties:</b> Bad request
	 */
	public static final String BAD_REQUEST = "validation.bad.request";

	// ##### ===== GENERAL ERRORS =====
	/**
	 * <b>messages_es_MX.properties:</b> Ha ocurrido un error inesperado procesando
	 * la solicitud <br>
	 * <b>messages_en_US.properties:</b> An unexpected error occurred while
	 * processing the request
	 */
	public static final String UNEXPECTED_ERROR = "validation.unexpected.error";

	// ##### ===== THROWABLE ERRORS =====
	/**
	 * <b>messages_es_MX.properties:</b> Error interno del servidor <br>
	 * <b>messages_en_US.properties:</b> Internal server error
	 */
	public static final String THROWABLE_INTERNAL_SERVER_ERROR = "validation.throwable.internal.server.error";
	/**
	 * <b>messages_es_MX.properties:</b> Ha ocurrido un error interno en el
	 * servidor. La solicitud ha sido cancelada <br>
	 * <b>messages_en_US.properties:</b> An internal server error has occurred. The
	 * request has been cancelled
	 */
	public static final String THROWABLE_INTERNAL_SERVER_ERROR_MESSAGE = "validation.throwable.internal.server.error.message";
	/**
	 * <b>messages_es_MX.properties:</b> Access denegado <br>
	 * <b>messages_en_US.properties:</b> Resource Denied
	 */
	public static final String THROWABLE_RESOURCE_DENIED = "validation.throwable.resource.denied";
	/**
	 * <b>messages_es_MX.properties:</b> No tiene permisos para acceder al recurso
	 * solicitado <br>
	 * <b>messages_en_US.properties:</b> You don't have permission to access the
	 * requested resource
	 */
	public static final String THROWABLE_RESOURCE_DENIED_MESSAGE = "validation.throwable.resource.denied.message";
	/**
	 * <b>messages_es_MX.properties:</b> Recurso no disponible <br>
	 * <b>messages_en_US.properties:</b> Resource not available
	 */
	public static final String THROWABLE_RESOURCE_NOT_AVAILABLE = "validation.throwable.resource.not.available";
	/**
	 * <b>messages_es_MX.properties:</b> El recurso al que está intentando acceder
	 * no existe o no está disponible <br>
	 * <b>messages_en_US.properties:</b> The resource you are trying to access does
	 * not exist or is not available
	 */
	public static final String THROWABLE_RESOURCE_NOT_AVAILABLE_MESSAGE = "validation.throwable.resource.not.available.message";
	/**
	 * <b>messages_es_MX.properties:</b> No autorizado <br>
	 * <b>messages_en_US.properties:</b> Unauthorized
	 */
	public static final String THROWABLE_UNAUTHORIZED = "validation.throwable.unauthorized";
	/**
	 * <b>messages_es_MX.properties:</b> No está autorizado para realizar esta
	 * acción <br>
	 * <b>messages_en_US.properties:</b> You are not authorized to perform this
	 * action
	 */
	public static final String THROWABLE_UNAUTHORIZED_MESSAGE = "validation.throwable.unauthorized";
	/**
	 * <b>messages_es_MX.properties:</b> Error de sesión <br>
	 * <b>messages_en_US.properties:</b> Session error
	 */
	public static final String THROWABLE_SESSION_ERROR = "validation.throwable.session.error";
	/**
	 * <b>messages_es_MX.properties:</b> Su sesión ha expirado o no ha podido ser
	 * validada internamente <br>
	 * <b>messages_en_US.properties:</b> Your session has expired or could not be
	 * validated internally
	 */
	public static final String THROWABLE_SESSION_ERROR_MESSAGE = "validation.throwable.session.error.message";

	// ##### ===== UVALIDATOR METHODS =====
	public static final int MIN_DECIMALS = 2;
	public static final int MAX_DECIMALS = 6;

	public static boolean nonNull(List<Object> args) {
		return args.stream().filter(ObjectUtils::isEmpty).count() == 0;
	}
	
	public static boolean nonNull(Object value) {
		return Objects.nonNull(value);
	}
	
	public static boolean isEmpty(Object value) {
		return ObjectUtils.isEmpty(value);
	}

	public static boolean isNotEmpty(Object value) {
		return !isEmpty(value);
	}

	public static boolean hasErrors(Boolean error, Map<String, String> errorMap) {
		return Boolean.TRUE.equals(error) || ValidatorUtil.isNotEmpty(errorMap);
	}
	
	public static boolean hasErrors(Boolean error) {
		return Boolean.TRUE.equals(error);
	}
	
	public static boolean hasErrors(Map<String, String> errorMap) {
		return ValidatorUtil.isNotEmpty(errorMap);
	}
	
	public static boolean hasNoErrors(Boolean error, Map<String, String> errorMap) {
		return Boolean.FALSE.equals(error) && ValidatorUtil.isEmpty(errorMap);
	}
	
	public static boolean hasNoErrors(Boolean error) {
		return Boolean.FALSE.equals(error);
	}
	
	public static boolean hasNoErrors(Map<String, String> errorMap) {
		return ValidatorUtil.isEmpty(errorMap);
	}

	/**
	 * Evaluates if Object... is non-null
	 * 
	 * @param args Object...
	 * @return True if Object... is non-null, otherwise false
	 */
	public static boolean isNotNullArgs(final Object... args) {
		if (Objects.nonNull(args)) {
			val nonNullArgs = Arrays.stream(args).filter(Objects::nonNull).count();
			return nonNullArgs > 0;
		}
		return false;
	}

	/**
	 * Evaluates if Object... is non-null
	 * 
	 * @param args Object...
	 * @return True if Object... is non-null, otherwise false
	 */
	public static boolean isNotNullArgs(final String... args) {
		if (Objects.nonNull(args)) {
			val nonNullArgs = Arrays.stream(args).filter(Objects::nonNull).count();
			return nonNullArgs > 0;
		}
		return false;
	}

	public static boolean isNumeric(final String str) {
		try {
			
			if(isNotEmpty(str)) {
				Integer.parseInt(str);
				return true;
			}

		} catch (NumberFormatException nfe) {
			log.error("ValidatorUtil#isNumeric error {}", nfe.getMessage());
		}
		return false;
	}

	public static boolean isValidExtension(final List<String> extensions, final String ext) {
		val isValid = extensions.stream().filter(e -> e.equalsIgnoreCase(ext)).findAny().orElse(null);
		return isValid != null;
	}
	
	public static boolean validateRequired(final Object obj) {
		if(ObjectUtils.isEmpty(obj)) {
			return false;
		}
		
		if (obj instanceof String) {
			val str = obj.toString().trim();
			return StringUtils.hasLength(str);
		}
		
		return true;
	}
	
	public static boolean validatePattern(final String value, final String regexp) {
		if(!ObjectUtils.isEmpty(value) && !ObjectUtils.isEmpty(regexp)) {
			try {
				
				val isMatch = Pattern.matches(regexp, value);
				if (!isMatch) {
					log.warn("ValidatorUtil#validatePattern warning! - Constraint validation failed: The value {} doesn't match with the regex {}", value, regexp);
				}
				return isMatch;
				
			} catch (Exception e) {
				log.error("ValidatorUtil#validatePattern error {}", e.getMessage());
			}
			return false;
		}
		return true;
	}
	
	public static boolean validateAlphabetic(final String letter) {
		return Pattern.compile(PatternUtil.ALPHABETIC_PATTERN).matcher(letter).matches();
	}

	public static boolean validateNullableAlphabetic(final String letter) {
		if (letter == null) {
			return true;
		} else {
			val vletter = letter.trim();
			if (vletter.isEmpty()) {
				return true;
			}
		}
		return validateAlphabetic(letter);
	}

	public static boolean validateAlphabeticWithSpace(final String letter) {
		return Pattern.compile(PatternUtil.ALPHABETIC_WITH_SPACE_PATTERN).matcher(letter).matches();
	}

	public static boolean validateNullableAlphabeticWithSpace(final String letter) {
		if (letter == null) {
			return true;
		} else {
			val vletter = letter.trim();
			if (vletter.isEmpty()) {
				return true;
			}
		}
		return validateAlphabeticWithSpace(letter);
	}

	public static boolean validateLength(final String letter, final Integer min, final Integer max) {
		if (letter == null) {
			return false;
		} else {
			return (letter.length() >= min && letter.length() <= max);
		}
	}

	public static boolean validateLength(final String letter, final Integer max) {
		if (letter == null) {
			return false;
		} else {
			return letter.length() <= max;
		}
	}

	public static boolean validateNumber(final String number) {
		if (number == null) {
			return false;
		}
		return Pattern.compile(PatternUtil.NUMBER_PATTERN).matcher(number).matches();
	}

	public static boolean validateNullableNumber(final String number) {
		if (number == null) {
			return true;
		} else {
			val vnumber = number.trim();
			if (vnumber.isEmpty()) {
				return true;
			}
		}
		return validateNumber(number);
	}

	public static boolean validateNumberWithSpace(final String number) {
		return Pattern.compile(PatternUtil.NUMBER_WITH_SPACE_PATTERN).matcher(number).matches();
	}

	public static boolean validateNullableNumberWithSpace(final String number) {
		if (number == null) {
			return true;
		} else {
			val vnumber = number.trim();
			if (vnumber.isEmpty()) {
				return true;
			}
		}
		return validateNumberWithSpace(number);
	}

	public static boolean validateIntNumber(final Integer number) {
		if (number == null) {
			return false;
		}

		val pattern = Pattern.compile(PatternUtil.NUMBER_PATTERN);
		val matcher = pattern.matcher(IntegerUtil.value(number));
		return matcher.matches();
	}

	public static boolean validateIntNullableNumber(final Integer number) {
		if (number == null) {
			return true;
		} else {
			val vnumber = IntegerUtil.value(number).trim();
			if (vnumber.isEmpty()) {
				return true;
			}
		}
		return validateIntNumber(number);
	}

	public static boolean validateDate(final String date) {
		return dateValidator(date);
	}

	public static boolean validateNullableDate(final String date) {
		if (date == null) {
			return true;
		} else {
			val vdate = date.trim();
			if (vdate.isEmpty()) {
				return true;
			}
			return dateValidator(vdate);
		}
	}

	private static boolean dateValidator(final String date) {
		Date formattedDate = null;
		try {

			val format = Formatter.DATE_SIMPLE_FORMAT.getFormat();
			val sdf = new SimpleDateFormat(format);
			formattedDate = sdf.parse(date);

		} catch (Exception e) {
			return false;
		}
		return formattedDate != null;
	}

	public static boolean validateDateTime(final String date) {
		return dateValidator(date);
	}

	public static boolean validateNullableDateTime(final String date) {
		if (date == null) {
			return true;
		} else {
			val vdate = date.trim();
			if (vdate.isEmpty()) {
				return true;
			}
			return dateTimeValidator(vdate);
		}
	}

	private static boolean dateTimeValidator(final String date) {
		Date formattedDate = null;
		try {

			val format = Formatter.DATE_TIME_FULL_FORMAT.getFormat();
			val sdf = new SimpleDateFormat(format);
			formattedDate = sdf.parse(date);

		} catch (Exception e) {
			return false;
		}
		return formattedDate != null;
	}

	public static boolean validateDecimal(final String number) {
		return decimalValidator(number);
	}

	public static boolean validateNullableDecimal(final String number) {
		if (number == null) {
			return true;
		} else {
			val vnumber = number.trim();
			if (vnumber.isEmpty()) {
				return true;
			}
		}
		return decimalValidator(number);
	}

	public static boolean validateDecimal(final Double number) {
		val dnum = DoubleUtil.dValue(number);
		return decimalValidator(dnum);
	}

	public static boolean validateNullableDecimal(final Double number) {
		if (number == null) {
			return true;
		} else {
			val vnumber = DoubleUtil.dValue(number).trim();
			if (vnumber.isEmpty()) {
				return true;
			}
		}
		return validateDecimal(number);
	}

	private static boolean decimalValidator(final String number) {
		if (number != null) {
			try (val scanner = new Scanner(number)) {

				if (scanner.hasNextDouble()) {
					int pos = number.indexOf(".");
					if (pos > 0) {
						int decimal = number.substring(pos + 1).length();
						return decimal <= MAX_DECIMALS;
					}
					return true;
				}

			} catch (Exception e) {
				log.error("ValidatorUtil#decimalValidator error {}", e);
			}
		}
		return false;
	}

	public static boolean validateEmail(final String email) {
		return Pattern.compile(PatternUtil.EMAIL_PATTERN).matcher(email).matches();
	}

	public static boolean validateNullableEmail(final String email) {
		if (email == null) {
			return true;
		} else {
			val vemail = email.trim();
			if (vemail.isEmpty()) {
				return true;
			}
			return validateEmail(vemail);
		}
	}

	public static boolean validateTime12H(final String time) {
		if(ValidatorUtil.isEmpty(time)) {
			return false;
		}
		
		val occurrences = StringUtils.countOccurrencesOf(time, ":");
		val formatter = occurrences == 1 ? Formatter.T12H : Formatter.T12H_FULL;
		
		return timeValidator(time, formatter);
	}
	
	public static boolean validateNullableTime12H(final String time) {
		if (ValidatorUtil.isEmpty(time)) {
			return true;
		}
		return validateTime12H(time);
	}
	
	public static boolean validateTime24H(final String time) {
		if(ValidatorUtil.isEmpty(time)) {
			return false;
		}
		
		val occurrences = StringUtils.countOccurrencesOf(time, ":");
		val formatter = occurrences == 1 ? Formatter.T24H : Formatter.T24H_FULL;
		
		return timeValidator(time, formatter);
	}

	public static boolean validateNullableTime24H(String time) {
		if (ValidatorUtil.isEmpty(time)) {
			return true;
		}
		return validateTime24H(time);
	}

	private static boolean timeValidator(final String time, final Formatter formatter) {
		val localTime = DateTimeUtil.localTime(time, formatter);
		return localTime != null;
	}

	public static boolean validateRFC(final String rfc) {
		return Pattern.compile(PatternUtil.RFC_PATTERN).matcher(rfc).matches();
	}

	public static boolean validateNullableRFC(final String rfc) {
		if (rfc == null) {
			return true;
		} else {
			val vrfc = rfc.trim();
			if (vrfc.isEmpty()) {
				return true;
			}
			return validateRFC(vrfc);
		}
	}

	public static boolean validateRFC12(final String rfc) {
		return Pattern.compile(PatternUtil.RFC12_PATTERN).matcher(rfc).matches();
	}

	public static boolean validateRFC13(final String rfc) {
		return Pattern.compile(PatternUtil.RFC13_PATTERN).matcher(rfc).matches();
	}

	public static boolean validateCURP(final String curp) {
		return Pattern.compile(PatternUtil.CURP_PATTERN).matcher(curp).matches();
	}

	public static boolean validateNullableCURP(final String curp) {
		if (curp == null) {
			return true;
		} else {
			val vcurp = curp.trim();
			if (vcurp.isEmpty()) {
				return true;
			}
			return validateCURP(vcurp);
		}
	}

	public static boolean validatePhoneMx(final String phone) {
		return Pattern.compile(PatternUtil.PHONE_MX_PATTERN).matcher(phone).matches();
	}

	public static boolean validateZipCodeMx(final String zipCode) {
		return Pattern.compile(PatternUtil.ZIP_CODE_MX_PATTERN).matcher(zipCode).matches();
	}
	
}