package bs.common.wrapper;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WResponse implements Serializable {
	private static final long serialVersionUID = -3042686055658047285L;
	
    private String message;
    private String error;
    private ResponseJSON responseJSON;

    public WResponse(final String message) {
        super();
        this.message = message;
    }

    public WResponse(final String message, final String error) {
        super();
        this.message = message;
        this.error = error;
    }
    
    public WResponse(final ResponseJSON responseJSON) {
        super();
        this.responseJSON = responseJSON;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResponseJSON implements Serializable {
    	private static final long serialVersionUID = -3042686055658047285L;
    	
    	private ResponseMessage message;
    	@Singular
    	private List<ResponseMessage> errors;
    	
    	public ResponseJSON(final String defaultMessage) {
    		this.message = new ResponseMessage(defaultMessage);
    	}
    	
    	@Data
    	@Builder
        @NoArgsConstructor
        @AllArgsConstructor
    	public static class ResponseMessage implements Serializable {
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
    	}
    	
    	@Data
    	@Builder
        @NoArgsConstructor
        @AllArgsConstructor
    	public static class ResponseError implements Serializable {
    		private static final long serialVersionUID = -3042686055658047285L;
    		
    		private String description;
    		private String action;
    		private String url;
    	}
    }

}