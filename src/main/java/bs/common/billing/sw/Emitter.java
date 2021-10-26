package bs.common.billing.sw;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Emitter {
	@JsonProperty("Nombre")
	private String name;
	@JsonProperty("RegimenFiscal")
	private Integer taxRegime;
	@JsonProperty("Rfc")
	private String rfc;
}