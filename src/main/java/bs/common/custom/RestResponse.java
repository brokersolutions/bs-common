package bs.common.custom;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import bs.common.exception.BaseException;
import bs.common.json.JResponse;
import bs.common.util.MessageUtil;
import bs.common.util.PredicateUtil;
import bs.common.util.ValidatorUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.val;

/**
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class RestResponse implements Serializable {
	private static final long serialVersionUID = 8522735446650175360L;
	
	private JResponse response;
	private Object data;
	@Singular
	private List<ErrorMessage> errors;
	private Integer status;
	private String error;
	private String message;
	private String path;
	private final LocalDateTime timestamp = LocalDateTime.now();
	
	public RestResponse(JResponse response) {
		this.response = response;
	}
	
	public RestResponse(Object data) {
		this.data = data;
	}
	
	public RestResponse(List<ErrorMessage> errors) {
		this.errors = errors;
	}
	
	public RestResponse(BaseException ex) {
		val errorsFromResponse = new ArrayList<ErrorMessage>();
		if (ValidatorUtil.isNotEmpty(ex)) {
			if (ValidatorUtil.isNotEmpty(ex.getResponse())) {
				val responseJSON = ex.getResponse().getResponseJSON();
				if (ValidatorUtil.isNotEmpty(responseJSON)) {
					if (ValidatorUtil.isNotEmpty(responseJSON.getErrors())) {
						val errors = responseJSON.getErrors().stream()
															 .filter(filterResponse())
															 .filter(Objects::nonNull)
															 .map(this::mapToError)
															 .toList();
						
						if (ValidatorUtil.isNotEmpty(errors)) {
							errorsFromResponse.addAll(errors);
						}
					}
					
					if (ValidatorUtil.isNotEmpty(responseJSON.getMessage())) {
						val errorMessage = mapToError(responseJSON.getMessage());
						val errors = List.of(errorMessage);
						errorsFromResponse.addAll(errors);
					}
				}
				
				val responseError = ex.getResponse().getError();
				if (ValidatorUtil.isNotEmpty(responseError)) {
					errorsFromResponse(errorsFromResponse, responseError);
				}
				
				val responseMessage = ex.getResponse().getMessage();
				if (ValidatorUtil.isNotEmpty(responseMessage)) {
					errorsFromResponse(errorsFromResponse, responseMessage);
				}
			}
			
			if (ValidatorUtil.isNotEmpty(ex.getMessage()) && ValidatorUtil.isNotEmpty(ex.getParams())) {
				val message = MessageUtil.getMessage(ex.getMessage(), ex.getParams());
				errorsFromResponse(errorsFromResponse, message);
			} else if (ValidatorUtil.isNotEmpty(ex.getMessage())) {
				errorsFromResponse(errorsFromResponse, ex.getMessage());
			} else if (ValidatorUtil.isNotEmpty(ex.getLocalizedMessage())) {
				errorsFromResponse(errorsFromResponse, ex.getLocalizedMessage());
			}
		}
		
		if (ValidatorUtil.isEmpty(errorsFromResponse)) {
			val message = MessageUtil.getMessage(ValidatorUtil.UNEXPECTED_ERROR);
			errorsFromResponse(errorsFromResponse, message);
		}
		
		this.errors = errorsFromResponse.stream()
										.filter(PredicateUtil.distinctByKey(ErrorMessage::getError))
										.toList();
	}
	
	public static RestResponseBuilder builder() {
		return new RestResponseBuilder();
	}
	
	private List<ErrorMessage> errorsFromResponse(List<ErrorMessage> errorsFromResponse, String message) {
		val errors = List.of(new ErrorMessage(message));
		errorsFromResponse.addAll(errors);
		return errorsFromResponse;
	}
	
	private static Predicate<ResponseMessage> filterResponse() {
		return p -> ValidatorUtil.isNotEmpty(p.getDefaultMessage()) || ValidatorUtil.isNotEmpty(p.getError());
	}
	
	private ErrorMessage mapToError(ResponseMessage rm) {
		String error = null;
		if (ValidatorUtil.isNotEmpty(rm.getDefaultMessage())) {
			error = rm.getDefaultMessage();
		} else if (ValidatorUtil.isNotEmpty(rm.getError())) {
			error = rm.getError();
		}
		return new ErrorMessage(error);
	}
}