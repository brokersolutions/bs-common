package bs.common.billing.sw;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Receiver {
	@JsonProperty("Nombre")
	private String name;
	@JsonProperty("UsoCFDI")
	@Default
	private String cfdiUse = "P01";
	private String NumRegIdTrib;
	@JsonProperty("Rfc")
	private String rfc;
}