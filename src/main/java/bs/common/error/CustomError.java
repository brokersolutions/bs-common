package bs.common.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomError {
	private String field;
	private String code;
	private String cause;
	private String message;
	private String description;
	private String action;
	private String url;
}
