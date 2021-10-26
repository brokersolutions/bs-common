package bs.common.billing.sw;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class TaxWithheld {
	@JsonProperty("Base")
	private Double base;
	@JsonProperty("Impuesto")
	private String tax;
	@JsonProperty("TipoFactor")
	@Default
	private String factorType = "Tasa";
	@JsonProperty("TasaOCuota")
	@Default
	private Double rateOrFee = 0.160000;
	@JsonProperty("Importe")
	private Double amount;
}