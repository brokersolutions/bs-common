package bs.common.billing.sw;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tax {
	@JsonProperty("Traslados")
	@Singular
	private List<TaxShifted> shifteds;
	@JsonProperty("Retenciones")
	@Singular
	private List<TaxWithheld> Withhelds;
}