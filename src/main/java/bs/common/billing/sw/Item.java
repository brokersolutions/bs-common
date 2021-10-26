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
public class Item {
	@JsonProperty("ClaveProdServ")
	private String claveProdServ;
	@JsonProperty("Importe")
	private Integer amount;
	@JsonProperty("Parte")
	private ConceptPart part;
	@JsonProperty("Impuestos")
	private Tax tax;
	@JsonProperty("ValorUnitario")
	private Integer unitValue;
	@JsonProperty("ComplementoConcepto")
	private ConceptComplement complement;
	@JsonProperty("NoIdentificacion")
	private String noIdentificacion;
	@JsonProperty("ClaveUnidad")
	private String unitKey;
	@JsonProperty("Cantidad")
	private Integer quantity;
	@JsonProperty("CuentaPredial")
	private String propertyAccount;
	@JsonProperty("Descripcion")
	private String description;
	@JsonProperty("InformacionAduanera")
	private String customsInformation ;
	@JsonProperty("Unidad")
	private String Unit;
	
	@NoArgsConstructor
	public static class ConceptPart {
		
	}
	
	@NoArgsConstructor
	public static class ConceptComplement {
		
	}
}
