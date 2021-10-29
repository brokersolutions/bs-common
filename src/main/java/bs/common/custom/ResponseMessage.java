package bs.common.custom;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ResponseMessage implements Serializable {
	private static final long serialVersionUID = -3042686055658047285L;
	
	private String field;
	private String defaultMessage;
	private String error;
	private String errorCode;
	private String errorCause;
	private String description;
	private String action;
	private String url;
	
	public ResponseMessage(final String defaultMessage) {
		this.defaultMessage = defaultMessage;
	}
	
	public ResponseMessage(final String defaultMessage, final String errorCause) {
		this.defaultMessage = defaultMessage;
		this.errorCause = errorCause;
	}
	
	public ResponseMessage(final String error, final String errorCode, final String errorCause) {
		this.error = error;
		this.errorCode = errorCode;
		this.errorCause = errorCause;
	}
}