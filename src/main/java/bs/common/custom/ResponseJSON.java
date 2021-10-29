package bs.common.custom;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ResponseJSON implements Serializable {
	private static final long serialVersionUID = -1387855812768271902L;
	
	private ResponseMessage message;
	@Singular
	private List<ResponseMessage> errors;
	
	public ResponseJSON(final String defaultMessage) {
		this.message = new ResponseMessage(defaultMessage);
	}
}