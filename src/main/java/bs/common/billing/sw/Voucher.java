package bs.common.billing.sw;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.Builder.Default;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Voucher {
	@JsonProperty("Version")
	@Default
	private String version = "3.3";
	@JsonProperty("TipoDeComprobante")
	@Default
	private String voucherType = VoucherType.I.name();
	@JsonProperty("Serie")
	private String serie;
	@JsonProperty("Folio")
	private String folio;
	@JsonProperty("Emisor")
	private Emitter emitter;
	@JsonProperty("Receptor")
	private Receiver receiver;
	@JsonProperty("LugarExpedicion")
	private Integer expeditionPlace;
	@JsonProperty("Fecha")
	private String date;
	@JsonProperty("Conceptos")
	private List<Item> items;
	@JsonProperty("Moneda")
	@Default
	private String currency = "MXN";
	@JsonProperty("FormaPago")
	private Integer paymentForm;
	@JsonProperty("MetodoPago")
	private String paymentMethod;
	@JsonProperty("CondicionesDePago")
	private String paymentConditions;
	@JsonProperty("TipoCambio")
	private Double exchangeRate;
	@JsonProperty("SubTotal")
	private Double subTotal;
	@JsonProperty("Total")
	private Double total;
	@JsonProperty("Impuestos")
	private Tax tax;
	@JsonProperty("NoCertificado")
	private String noCert;
	@JsonProperty("Certificado")
	private String cert;	
	@JsonProperty("Sello")
	private String stamp;
	@JsonProperty("CfdiRelacionados")
	private RelatedCfdi relatedCfdis;
	@JsonProperty("Complemento")
	private String complement;
	@JsonProperty("Addenda")
	private String addenda;
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RelatedCfdi {
		private String tipoRelacion;
		@JsonProperty("cfdiRelacionados")
		@Singular
		private List<RelatedCfdiUuid> uuids;
		
		@Data
		@Builder
		@NoArgsConstructor
		@AllArgsConstructor
		public static class RelatedCfdiUuid {
			private String uuid;
		}
	}
	
	public enum VoucherType {
		I, N
	}
}
