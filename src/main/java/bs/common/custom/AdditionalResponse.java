package bs.common.custom;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalResponse implements Serializable {
	private static final long serialVersionUID = -7237815488388544060L;
	
	private String description;
	private String action;
	private String url;
}