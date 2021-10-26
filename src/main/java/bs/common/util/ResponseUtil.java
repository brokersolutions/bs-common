package bs.common.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import bs.common.enums.EApiGeneric;
import bs.common.enums.EIziToastPosition;
import bs.common.enums.ENotificationType;
import bs.common.error.CustomError;
import bs.common.exception.ResponseException;
import bs.common.json.JGenericResponse;
import bs.common.json.JResponse;
import bs.common.json.JResponse.JConfirmation;
import bs.common.json.JResponse.JNotification;
import bs.common.json.JResponse.JNotification.CssStyleClass;
import bs.common.json.JResponse.JNotification.MessageType;
import bs.common.notification.IziToast;
import bs.common.notification.Swal;
import bs.common.notification.Toastr;
import bs.common.wrapper.WResponse;
import bs.common.wrapper.WResponse.ResponseJSON;
import bs.common.wrapper.WResponse.ResponseJSON.ResponseError;
import bs.common.wrapper.WResponse.ResponseJSON.ResponseMessage;

@Slf4j
public class ResponseUtil implements Serializable {
	private static final long serialVersionUID = 6913983201535188254L;

	private ResponseUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	private static String invalidFieldMessage;
	private static String invalidFieldGreaterThanValidation;
	private static String invalidMinLengthValidation;
	private static String invalidMaxLengthValidation;
	private static String invalidMinMaxLengthValidation;
	private static String invalidRegexpValidation;
	private static String invalidFileSizeValidation;
	
	private static List<String> errorMessages;
	private static Map<String, Integer> errorHierarchy;
	private static Map<String, Integer> customErrorHierarchy;
	private static boolean isCustomizedHierarchy = false;
	
	public static void instance(Map<String, Integer> errorHierarchy) {
		customErrorHierarchy = errorHierarchy;
		isCustomizedHierarchy = true;
	}
	
	public static WResponse response(String message) {
		return new WResponse(message);
	}

	public static WResponse response(String message, String error) {
		return new WResponse(message, error);
	}

	public static WResponse response(BindingResult bindingResult, String error) {
		init();
		val errorMap = new HashMap<String, ErrorMessage>();
		val bindingErrors = bindingResult.getAllErrors();

		for (val obj : bindingErrors) {
			if(obj instanceof FieldError) {
				val field = ((FieldError) obj).getField();
            	val msg = obj.getDefaultMessage();

            	val em = errorMap.entrySet().stream()
            								.filter(map -> map.getKey().equals(field))
            								.map(map -> map.getValue())
            								.findAny().orElse(null);
            	if(em == null) {
            		errorMap.put(field, getMessage(msg));
            	} else {
            		val evaluateError = evaluateError(em, msg);
            		if(evaluateError != null && evaluateError.getError() != null && !em.getError().equalsIgnoreCase(evaluateError.getError())) {
            			errorMap.replace(field, evaluateError);
            		}
            	}
			}
		}

		val result = errorMap.entrySet().stream()
										.map(item -> mapped(item))
			 	  				  		.map(item -> "{\"field\":\"" + item.getField() + "\",\"defaultMessage\":\"" + item.getError() + "\"}")
			 	  				  		.collect(Collectors.joining(","));
		
		val message = "[" + result + "]";
		return new WResponse(message, error);
	}

	public static List<ErrorMessage> response(BindingResult bindingResult) {
		init();
		try {
			
			val errorMap = new HashMap<String, ErrorMessage>();
			val bindingErrors = bindingResult.getAllErrors();

			for (val obj : bindingErrors) {
				if(obj instanceof FieldError) {
					val field = ((FieldError) obj).getField();
	            	val msg = obj.getDefaultMessage();

	            	val em = errorMap.entrySet().stream()
	            								.filter(map -> map.getKey().equals(field))
	            								.map(map -> map.getValue())
	            								.findAny().orElse(null);
	            	if(em == null) {
	            		errorMap.put(field, getMessage(msg));
	            	} else {
	            		val evaluateError = evaluateError(em, msg);
	            		if(evaluateError != null && evaluateError.getError() != null && !em.getError().equalsIgnoreCase(evaluateError.getError())) {
	                		errorMap.replace(field, evaluateError);
	            		}
	            	}
				}
			}

			if(errorMap != null) {
				return errorMap.entrySet().stream()
									  	  .map(item -> mapped(item))
									  	  .collect(Collectors.toList());
			}
			
		} catch (Exception e) {
			log.error("ResponseUtil#response error {}", e);
		}
		return Collections.emptyList();
	}
	
	public static WResponse response(final CustomError error) {
		val field = error.getField();
		val errorMessage = ValidatorUtil.isNotEmpty(error.getMessage()) ? MessageUtil.getMessage(error.getMessage()) : null;
		val errorCode = ValidatorUtil.isNotEmpty(error.getCode()) ? error.getCode() : null;
		val errorCause = ValidatorUtil.isNotEmpty(error.getCause()) ? error.getCause() : null;
		val description = ValidatorUtil.isNotEmpty(error.getDescription()) ? MessageUtil.getMessage(error.getDescription()) : null;
		val action = ValidatorUtil.isNotEmpty(error.getAction()) ? MessageUtil.getMessage(error.getAction()) : null;
		val url = ValidatorUtil.isNotEmpty(error.getUrl()) ? error.getUrl() : null;
		
		val message = ResponseMessage
						.builder()
						.field(field)
						.defaultMessage(errorMessage)
						.error(errorMessage)
						.errorCode(errorCode)
						.errorCause(errorCause)
						.description(description)
						.action(action)
						.url(url)
						.build();
		
		val responseJSON = new ResponseJSON();
		responseJSON.setMessage(message);
		
		return new WResponse(responseJSON);
	}
	
	public static WResponse response(final String errorCode, final String field, final String cause) {
		val errorMessage = MessageUtil.getMessage(errorCode);
		val message = ResponseMessage
						.builder()
						.field(field)
						.defaultMessage(errorMessage)
						.error(cause != null ? cause : null)
						.build();
				
		val responseJSON = new ResponseJSON();
		responseJSON.setMessage(message);
		
		return new WResponse(responseJSON);
	}

	public static WResponse response(final String errorCode, final Object[] params, final String cause) {
		val errorMessage = MessageUtil.getMessage(errorCode, params);
		val message = ResponseMessage
						.builder()
						.defaultMessage(errorMessage)
						.error(cause != null ? cause : null)
						.build();
				
		val responseJSON = new ResponseJSON();
		responseJSON.setMessage(message);
		
		return new WResponse(responseJSON);
	}
	
	public static WResponse response(final String errorCode, final Object[] params, final String field, final String cause) {
		val errorMessage = MessageUtil.getMessage(errorCode, params);
		val message = ResponseMessage
						.builder()
						.field(field)
						.defaultMessage(errorMessage)
						.error(cause != null ? cause : null)
						.build();
				
		val responseJSON = new ResponseJSON();
		responseJSON.setMessage(message);
		
		return new WResponse(responseJSON);
	}

	public static WResponse response(final String errorCode, final ResponseError responseError, final EApiGeneric cause) {
		val errorMessage = MessageUtil.getMessage(errorCode);
		
		String description = null;
		String action = null;
		String url = null;
		
		if(responseError != null) {
			description = responseError.getDescription();
			action = responseError.getAction();
			url = responseError.getUrl();
		}
		
		val message = ResponseMessage
						.builder()
						.defaultMessage(errorMessage)
						.error(cause != null ? cause.value() : null)
						.description(description)
						.action(action)
						.url(url)
						.build();
				
		val responseJSON = new ResponseJSON();
		responseJSON.setMessage(message);
		
		return new WResponse(responseJSON);
	}

	public static WResponse response(final String errorCode, final ResponseError responseError, final Object[] params, final String cause) {
		val errorMessage = MessageUtil.getMessage(errorCode, params);
		
		String description = null;
		String action = null;
		String url = null;
		
		if(responseError != null) {
			description = responseError.getDescription();
			action = responseError.getAction();
			url = responseError.getUrl();
		}
		
		val message = ResponseMessage
						.builder()
						.defaultMessage(errorMessage)
						.error(cause != null ? cause : null)
						.description(description)
						.action(action)
						.url(url)
						.build();
				
		val responseJSON = new ResponseJSON();
		responseJSON.setMessage(message);
		
		return new WResponse(responseJSON);
	}
	
	public static ResponseEntity<WResponse> handleBindException(final BindingResult bindingResult, final WebRequest request, @Nullable final String error) {
		val bindingErrors = bindingResult.getAllErrors();
		val errors = bindingErrors.stream()
							 	  .filter(FieldError.class::isInstance)
							 	  .map(FieldError.class::cast)
							 	  .map(item -> "{\"field\":\"" + item.getField() + "\",\"defaultMessage\":\"" + item.getDefaultMessage() + "\"}")
							 	  .collect(Collectors.joining(","));
		val resultJSON = "[" + errors + "]";
		val response = new WResponse(resultJSON, error);
		
		request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, new Exception(), RequestAttributes.SCOPE_REQUEST);
		return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}
	
	public static String response(final List<ObjectError> allErrors) {
		try {
			
			if(allErrors != null) {
				val errors = allErrors.stream()
						.filter(FieldError.class::isInstance)
						.map(FieldError.class::cast)
						.map(item -> ResponseMessage
										.builder()
										.field(item.getField())
										.defaultMessage(item.getDefaultMessage())
										.build())
						.collect(Collectors.toList());
				
				val responseJson = ResponseJSON
									.builder()
									.clearErrors()
									.errors(errors)
									.build();
				
				val mapper = new ObjectMapper();
				return mapper.writeValueAsString(new WResponse(responseJson));
			}
			
		} catch (Exception e) {
			log.error("UBindingResult#response error {}", e);
		}
		return ValueUtil.EMPTY;
	}
	
	private static void init() {
		val requiredMessage = i18nMessage(ValidatorUtil.REQUIRED_MESSAGE);
		val notEmptyMessage = i18nMessage(ValidatorUtil.NOT_EMPTY_MESSAGE);
		val invalidAlphabeticMessage = i18nMessage(ValidatorUtil.INVALID_ALPHABETIC_MESSAGE);
		val invalidNumericMessage = i18nMessage(ValidatorUtil.INVALID_NUMERIC_MESSAGE);
		val invalidEmailMessage = i18nMessage(ValidatorUtil.INVALID_EMAIL_MESSAGE);
		val invalidPhoneMessage = i18nMessage(ValidatorUtil.INVALID_PHONE_MESSAGE);
		val invalidFormatMessage = i18nMessage(ValidatorUtil.INVALID_FORMAT_MESSAGE);
		val invalidOldPasswordMessage = i18nMessage(ValidatorUtil.INVALID_OLD_PASSWORD_MESSAGE);
		val invalidPasswordDifferentMessage = i18nMessage(ValidatorUtil.INVALID_PASSWORD_DIFFERENT_MESSAGE);
		val invalidPasswordMatchingMessage = i18nMessage(ValidatorUtil.INVALID_PASSWORD_MATCHING_MESSAGE);
		val invalidPasswordMessage = i18nMessage(ValidatorUtil.INVALID_PASSWORD_MESSAGE);
		val userAlreadyExistsMessage = i18nMessage(ValidatorUtil.USER_ALREADY_EXISTS_MESSAGE);
		val userRfcAlreadyExistsMessage = i18nMessage(ValidatorUtil.USER_RFC_ALREADY_EXISTS_MESSAGE);
		val userBusinessNameAlreadyExistsMessage = i18nMessage(ValidatorUtil.USER_BNAME_ALREADY_EXISTS_MESSAGE);
		val userUnknownMessage = i18nMessage(ValidatorUtil.USER_UNKNOWN_MESSAGE);
		val singleFileImgMissingMessage = i18nMessage(ValidatorUtil.SINGLE_FILE_IMG_MISSING_MESSAGE);
		val multipleFileImgMissingMessage = i18nMessage(ValidatorUtil.MULTIPLE_FILE_IMG_MISSING_MESSAGE);
		val singleFileMissingMessage = i18nMessage(ValidatorUtil.SINGLE_FILE_MISSING_MESSAGE);
		val multipleFileMissingMessage = i18nMessage(ValidatorUtil.MULTIPLE_FILE_MISSING_MESSAGE);
		val invalidFileExtensionMessage = i18nMessage(ValidatorUtil.INVALID_FILE_EXTENSION_MESSAGE);
		val invalidFileSizeMessage = i18nMessage(ValidatorUtil.INVALID_FILE_SIZE_MESSAGE);
		val invalidDateOutRangeMessage = i18nMessage(ValidatorUtil.INVALID_DATE_OUT_RANGE_MESSAGE);
		val invalidZipCodeMessage = i18nMessage(ValidatorUtil.INVALID_ZIP_CODE_MESSAGE);
		val userNoAuthenticatedActionMessage = i18nMessage(ValidatorUtil.USER_NO_AUTHENTICATED_ACTION_MESSAGE);
		val invalidCouponMessage = i18nMessage(ValidatorUtil.INVALID_COUPON_MESSAGE);
		val invalidUsedCouponMessage = i18nMessage(ValidatorUtil.INVALID_USED_COUPON_MESSAGE);
		
		invalidFieldMessage = i18nMessage(ValidatorUtil.INVALID_FIELD_MESSAGE);
		invalidFieldGreaterThanValidation = i18nMessage(ValidatorUtil.INVALID_FIELD_GREATER_THAN_VALIDATION);
		invalidMinLengthValidation = i18nMessage(ValidatorUtil.INVALID_MIN_LENGTH_VALIDATION);
		invalidMaxLengthValidation = i18nMessage(ValidatorUtil.INVALID_MAX_LENGTH_VALIDATION);
		invalidMinMaxLengthValidation = i18nMessage(ValidatorUtil.INVALID_MIN_MAX_LENGTH_VALIDATION);
		invalidRegexpValidation = i18nMessage(ValidatorUtil.INVALID_REGEXP_VALIDATION);
		invalidFileSizeValidation = i18nMessage(ValidatorUtil.INVALID_FILE_SIZE_VALIDATION);
		
		errorMessages = Stream.of(
			requiredMessage, notEmptyMessage, invalidAlphabeticMessage, invalidNumericMessage, invalidEmailMessage, invalidPhoneMessage, invalidFormatMessage, 
			invalidFieldMessage, invalidFieldGreaterThanValidation, invalidMinLengthValidation, invalidMaxLengthValidation, invalidMinMaxLengthValidation, invalidRegexpValidation, 
			invalidOldPasswordMessage, invalidPasswordDifferentMessage, invalidPasswordMatchingMessage, invalidPasswordMessage, userAlreadyExistsMessage,
			userRfcAlreadyExistsMessage, userBusinessNameAlreadyExistsMessage, userUnknownMessage, singleFileImgMissingMessage, multipleFileImgMissingMessage, singleFileMissingMessage,
			multipleFileMissingMessage, invalidFileExtensionMessage, invalidFileExtensionMessage, invalidFileSizeMessage, invalidFileSizeValidation, invalidDateOutRangeMessage,
			invalidZipCodeMessage, userNoAuthenticatedActionMessage, invalidCouponMessage, invalidUsedCouponMessage
		).collect(Collectors.toList());
		
		//##### Defining the error hierarchy
    	errorHierarchy = new HashMap<>();
    	errorHierarchy.put(userNoAuthenticatedActionMessage, 1);
    	errorHierarchy.put(requiredMessage, 1);
    	errorHierarchy.put(notEmptyMessage, 1);
    	errorHierarchy.put(invalidAlphabeticMessage, 2);
    	errorHierarchy.put(invalidNumericMessage, 2);
    	errorHierarchy.put(invalidEmailMessage, 3);
    	errorHierarchy.put(invalidPhoneMessage, 3);
    	errorHierarchy.put(invalidFormatMessage, 3);
    	errorHierarchy.put(invalidFieldMessage, 3);
    	errorHierarchy.put(invalidRegexpValidation, 4);
    	errorHierarchy.put(userAlreadyExistsMessage, 4);
    	errorHierarchy.put(userRfcAlreadyExistsMessage, 4);
    	errorHierarchy.put(userBusinessNameAlreadyExistsMessage, 4);
    	errorHierarchy.put(userUnknownMessage, 4);
    	errorHierarchy.put(invalidFieldGreaterThanValidation, 4);
    	errorHierarchy.put(invalidMinLengthValidation, 4);
    	errorHierarchy.put(invalidMaxLengthValidation, 4);
    	errorHierarchy.put(invalidMinMaxLengthValidation, 4);	    	
    	errorHierarchy.put(invalidOldPasswordMessage, 4);
    	errorHierarchy.put(invalidPasswordDifferentMessage, 5);
    	errorHierarchy.put(invalidPasswordMatchingMessage, 6);
    	errorHierarchy.put(invalidPasswordMessage, 4);
    	errorHierarchy.put(singleFileImgMissingMessage, 3);
    	errorHierarchy.put(multipleFileImgMissingMessage, 3);
    	errorHierarchy.put(singleFileMissingMessage, 3);
    	errorHierarchy.put(multipleFileMissingMessage, 3);
    	errorHierarchy.put(invalidFileExtensionMessage, 4);
    	errorHierarchy.put(invalidFileSizeMessage, 4);
    	errorHierarchy.put(invalidFileSizeValidation, 4);
    	errorHierarchy.put(invalidDateOutRangeMessage, 2);
    	errorHierarchy.put(invalidZipCodeMessage, 3);
    	errorHierarchy.put(invalidCouponMessage, 3);
    	errorHierarchy.put(invalidUsedCouponMessage, 4);
		
    	if(isCustomizedHierarchy && customErrorHierarchy != null && !customErrorHierarchy.isEmpty()) {
			for (val entry : customErrorHierarchy.entrySet()) {
				val key = i18nMessage(entry.getKey());
				val value = entry.getValue();
				errorMessages.add(key);
				errorHierarchy.put(key, value);
			}
		}
    }
	
	private static ErrorMessage getMessage(final String msg) {
		String key = null;
		String alternative = null;
		
		if(msg != null) {
			for (val errorMessage : errorMessages) {
				if(msg.equalsIgnoreCase(errorMessage)) {
					key = errorMessage;
				} else if(msg.contains(invalidFieldGreaterThanValidation)) {
					key = invalidFieldGreaterThanValidation;
					alternative = msg;
				} else if(msg.contains(invalidMinLengthValidation)) {
					key = invalidMinLengthValidation;
					alternative = msg;
				} else if(msg.contains(invalidMaxLengthValidation)) {
					key = invalidMaxLengthValidation;
					alternative = msg;
				} else if(msg.contains(invalidMinMaxLengthValidation)) {
					key = invalidMinMaxLengthValidation;
					alternative = msg;
				} else if(msg.contains(invalidRegexpValidation)) {
					key = invalidRegexpValidation;
					alternative = invalidFieldMessage;
				} else if(msg.contains(invalidFileSizeValidation)) {
					key = invalidFileSizeValidation;
					alternative = msg;
				}
			}
			
			val errorMessage = new ErrorMessage();
			errorMessage.setError(key);
			errorMessage.setAlternativeError(alternative);
			return errorMessage;
		}
		return null;
	}
	
	private static ErrorMessage evaluateError(final ErrorMessage oldError, final String newMsg) {
    	val newError = getMessage(newMsg);
    	if(newError != null) {
    		Integer oldHierarchy = null;
    		if(errorHierarchy.containsKey(oldError.getError())) {
    			oldHierarchy = errorHierarchy.get(oldError.getError()); 
    		}
    		
    		Integer newHierarchy = null;
    		if(errorHierarchy.containsKey(newError.getError())) {
    			newHierarchy = errorHierarchy.get(newError.getError()); 
    		}
    		
    		if(oldHierarchy != null && newHierarchy != null && newHierarchy < oldHierarchy) {
    			return newError;
    		}
    	}
    	return oldError;
    }
	
	public static ResponseEntity<JGenericResponse> confirmation(final String message) {
		val response = confirmationJson(message);
		val genericResponse = JGenericResponse.response(response);
		return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
	}
	
	public static ResponseEntity<JGenericResponse> success() {
		val response = new JResponse();
		response.setSuccess(Boolean.TRUE);
		
		val genericResponse = JGenericResponse.response(response);
		return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
	}
	
	public static <T> ResponseEntity<T> success(T object) {
		return ResponseEntity.status(HttpStatus.OK).body(object);
	}
	
	public static ResponseEntity<JGenericResponse> response(final JResponse response) {
		val genericResponse = JGenericResponse.response(response);
		return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
	}

	public static <T> ResponseEntity<T> response(final T object) {
		return new ResponseEntity<>(object, new HttpHeaders(), HttpStatus.OK);
	}
	
	public static ResponseEntity<JGenericResponse> error() {
		val response = new JResponse();
		response.setError(Boolean.TRUE);
		
		val genericResponse = JGenericResponse.response(response);
		return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
	}
	
	public static ResponseEntity<JGenericResponse> sessionError() {
		val response = responseSessionError();
		val genericResponse = JGenericResponse.response(response);
		return ResponseEntity.status(HttpStatus.OK).body(genericResponse);
	}
	
	public static ResponseEntity<JGenericResponse> error(final JResponse response) {
		val genericResponse = JGenericResponse.response(response);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponse);
	}

	public static <T> ResponseEntity<T> error(final T object) {
		return new ResponseEntity<>(object, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public static ResponseEntity<JGenericResponse> internalServerError() {
		val response = new JResponse();
		response.setError(Boolean.TRUE);
		
		val genericResponse = JGenericResponse.response(response);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(genericResponse);
	}
	
	public static ResponseEntity<WResponse> internalServerError(WResponse wr) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(wr);
	}
	
	public static String responseStr(Object object) throws ResponseException {
		try {
			
			val mapper = new ObjectMapper();
			return mapper.writeValueAsString(object); 
			
		} catch (Exception e) {
			log.error("ResponseUtil#responseStr error {}", e);
			val wResponse = response(MessageUtil.getMessage(ValidatorUtil.UNEXPECTED_ERROR), null, EApiGeneric.MAPPED_EXCEPTION);
			throw new ResponseException(wResponse);
		}
	}
	
	public static JResponse responseOK() {
		val response = new JResponse();
		response.setSuccess(true);
		response.setSessionAlive(true);
		return response;
	}
	
	public static JResponse responseSessionError() {
		val response = new JResponse();
		response.setError(true);
		response.setSessionAlive(false);
		return response;
	}
	
	public static JResponse responseJson(final boolean isSessionAlive, final boolean isError, final String messageDetail, final MessageType messageType, final CssStyleClass cssStyleClass) {
		return responseJson(isSessionAlive, isError, messageDetail, messageType, cssStyleClass, false, null);
	}
	
	public static JResponse responseJson(final boolean isSessionAlive, final boolean isError, final String messageDetail, final MessageType messageType, final CssStyleClass cssStyleClass, final boolean isDismiss) {
		return responseJson(isSessionAlive, isError, messageDetail, messageType, cssStyleClass, isDismiss, null);
	}
	
	public static JResponse responseJsonSuccess(final boolean isSessionAlive, final String messageDetail) {
		return responseJson(isSessionAlive, false, messageDetail, MessageType.PAGE, CssStyleClass.SUCCESS, false, null);
	}
	
	public static JResponse responseJsonError(final boolean isSessionAlive, final String messageDetail) {
		return responseJson(isSessionAlive, false, messageDetail, MessageType.PAGE, CssStyleClass.ERROR, false, null);
	}
	
	public static JResponse responseJsonInfo(final boolean isSessionAlive, final String messageDetail) {
		return responseJson(isSessionAlive, false, messageDetail, MessageType.PAGE, CssStyleClass.INFO, false, null);
	}
	
	public static JResponse responseJsonWarning(final boolean isSessionAlive, final String messageDetail) {
		return responseJson(isSessionAlive, false, messageDetail, MessageType.PAGE, CssStyleClass.WARNING, false, null);
	}
	
	public static JResponse responseJson(final boolean isSessionAlive, final boolean isError, final String messageDetail, final MessageType messageType, final CssStyleClass cssStyleClass, final boolean isDismiss, final Integer fadeOut) {
		val notification = new JNotification();
		notification.setMessage(MessageUtil.getMessage(messageDetail));
		notification.setCssStyleClass(cssStyleClass.value());
		notification.setPageMessage(messageType.equals(MessageType.PAGE));
		notification.setModalPanelMessage(messageType.equals(MessageType.MODAL));
		notification.setPanelMessage(messageType.equals(MessageType.PANEL));
		notification.setDismiss(isDismiss);
		notification.setFadeOut(fadeOut);
		
		val response = new JResponse();
		response.setNotification(notification);
		response.setSuccess(!isError);
		response.setError(isError);
		response.setSessionAlive(isSessionAlive);
		
		return response;
	}
	
	public static JResponse confirmationJson(final String message) {
		return confirmationJsonRisk(message, false);
	}
	
	public static JResponse confirmationJsonRisk(final String message, final Boolean showRisk) {
		val confirmation = new JConfirmation(message, showRisk);
		
		val response = new JResponse();
		response.setConfirmation(confirmation);
		response.setSuccess(Boolean.TRUE);
		
		return response;
	}
	
	public static JResponse swal(final String messageDetail) {
		return swal(true, messageDetail, false);
	}
	
	public static JResponse swal(final boolean isSessionAlive, final String messageDetail) {
		return swal(isSessionAlive, messageDetail, false);
	}
	
	public static JResponse swal(final String messageDetail, final boolean isError) {
		return swal(true, messageDetail, isError);
	}
	
	public static JResponse swal(final boolean isSessionAlive, final String messageDetail, final boolean isError) {
		val swalType = isError ? ENotificationType.ERROR : ENotificationType.SUCCESS;
		val swal = Swal.swal(messageDetail, swalType);
		
		val notification = JNotification
							.builder()
							.swal(swal)
							.swalNotification(true)
							.build();

		return JResponse
				.builder()
				.notification(notification)
				.success(!isError)
				.error(isError)
				.sessionAlive(isSessionAlive)
				.build();
	}
	
	public static JResponse iziToast(final String message, final EIziToastPosition iziToastPosition, final boolean isError) {
		val iziToastType = !isError ? ENotificationType.SUCCESS : ENotificationType.ERROR; 
		return iziToast(true, iziToastType, null, message, iziToastPosition);
	}
	
	public static JResponse iziToast(final boolean isSessionAlive, final String message, final EIziToastPosition iziToastPosition, final boolean isError) {
		val iziToastType = !isError ? ENotificationType.SUCCESS : ENotificationType.ERROR; 
		return iziToast(isSessionAlive, iziToastType, null, message, iziToastPosition);
	}
	
	public static JResponse iziToast(final ENotificationType iziToastType, final String message, final EIziToastPosition iziToastPosition) {
		return iziToast(true, iziToastType, null, message, iziToastPosition);
	}
	
	public static JResponse iziToast(final boolean isSessionAlive, final ENotificationType iziToastType, final String message, final EIziToastPosition iziToastPosition) {
		return iziToast(isSessionAlive, iziToastType, null, message, iziToastPosition);
	}
	
	public static JResponse iziToast(final boolean isSessionAlive, final ENotificationType iziToastType, final String title, final String message, final EIziToastPosition iziToastPosition) {
		val iziToast = IziToast.instance(iziToastType, title, message, iziToastPosition);
		
		val notification = new JNotification();
		notification.setIziToast(iziToast);
		notification.setIziToastNotification(Boolean.TRUE);
		
		val response = new JResponse();
		response.setNotification(notification);
		response.setSuccess(!iziToastType.value().equals(ENotificationType.ERROR.value()));
		response.setError(iziToastType.value().equals(ENotificationType.ERROR.value()));
		response.setSessionAlive(isSessionAlive);
		
		return response;
	}
	
	public static JResponse toastr(final String toastrTitle, final String toastrMsg, ENotificationType toastrType) {
		return toastr(true, toastrTitle, toastrMsg, toastrType);
	}
	
	public static JResponse toastr(final boolean isSessionAlive, final String toastrTitle, final String toastrMsg, ENotificationType toastrType) {
		val toastr = new Toastr();
		toastr.setTitle(toastrTitle);
		toastr.setMessage(toastrMsg);
		toastr.setType(toastrType.value());
		
		val notification = new JNotification();
		notification.setToastr(toastr);
		notification.setToastrNotification(Boolean.TRUE);
		
		val response = new JResponse();
		response.setNotification(notification);
		response.setSuccess(!toastrType.value().equals(ENotificationType.ERROR.value()));
		response.setError(toastrType.value().equals(ENotificationType.ERROR.value()));
		response.setSessionAlive(isSessionAlive);
		
		return response;
	}
	
	@Data
    public static class ErrorMessage {
    	private String field;
    	private String error;
    	private String alternativeError;
    }
    
    private static ErrorMessage mapped(final Entry<String, ErrorMessage> entry) {
    	val field = entry.getKey();
		
		var error = entry.getValue().getError();
		if(entry.getValue().getAlternativeError() != null) {
			error = entry.getValue().getAlternativeError();
		}
		
		val errorMessage = new ErrorMessage();
		errorMessage.setField(field);
		errorMessage.setError(error);
		
		return errorMessage;
    }
    
    private static String i18nMessage(String key) {
    	return MessageUtil.getMessage(key);
	}

}