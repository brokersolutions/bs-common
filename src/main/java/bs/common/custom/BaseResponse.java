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
public class BaseResponse implements Serializable {
	private static final long serialVersionUID = 5043127194055659884L;
	
	private String message;
    private String error;
    private ResponseJSON responseJSON;

    public BaseResponse(final String message) {
        super();
        this.message = message;
    }

    public BaseResponse(final String message, final String error) {
        super();
        this.message = message;
        this.error = error;
    }
    
    public BaseResponse(final ResponseJSON responseJSON) {
        super();
        this.responseJSON = responseJSON;
    }
}