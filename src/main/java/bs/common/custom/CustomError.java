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
public class CustomError implements Serializable {
	private static final long serialVersionUID = -7789795816517088645L;
	
	private String field;
	private String code;
	private String cause;
	private String message;
	private String description;
	private String action;
	private String url;
}