package bs.common.json;

import bs.common.custom.BaseAddress;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JAddress {
	private BaseAddress address;
	private JResponse response;
	
	@Data
	@NoArgsConstructor
	public static class AddressData {
		private String state;
		private String town;
		private String colony;
		private String zipCode;
	}
}