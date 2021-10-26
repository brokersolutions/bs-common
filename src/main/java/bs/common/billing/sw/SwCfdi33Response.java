package bs.common.billing.sw;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SwCfdi33Response {
	private CfdiData data;
	private String status;
	@JsonInclude(Include.NON_NULL)
	private String message;
	@JsonInclude(Include.NON_NULL)
	private String messageDetail;
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CfdiData {
		@JsonProperty("cadenaOriginalSAT")
		private String originalString;
		@JsonProperty("noCertificadoSAT")
		private String noCertSAT;
		@JsonProperty("noCertificadoCFDI")
		private String noCertCFDI;
		private String uuid;
		@JsonProperty("selloSAT")
		private String satSeal;
		@JsonProperty("selloCFDI")
		private String cfdiSeal;
		@JsonProperty("fechaTimbrado")
		private String stampedDate;
		private String qrCode;
		private String cfdi;
	}
	
	public static enum Status {
		SUCCESS("success"), 
		FAIL("fail"), 
		ERROR("error");
		
		private String status;
		
		Status(final String value) {
			status = value;
		}

		public String getStatus() {
			return status;
		}
	}
	
}