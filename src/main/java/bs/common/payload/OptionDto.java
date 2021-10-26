package bs.common.payload;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import bs.common.util.CryptoUtil;
import bs.common.util.MessageUtil;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class OptionDto {
	private String id;
	private String code;
	private String name;
	private String value;
	
	public static List<OptionDto> empty() {
		return new ArrayList<OptionDto>();
	}
	
	public static OptionDto optDefault() {
		String SELECT_OPTION_DEFAULT = MessageUtil.getMessage("select.text.default");
		return OptionDto
				.builder()
				.id("")
				.code(SELECT_OPTION_DEFAULT)
				.value(SELECT_OPTION_DEFAULT)
				.build();
	}
	
	public static OptionDto mapper(Object objDto) {
		String id = null;
		String code = null;
		String value = null;
		String name = null;
		
		if(objDto instanceof IOptionDto) {
			val dto = (IOptionDto) objDto;
			id = CryptoUtil.idEncode(dto.getId());
			code = dto.getCode();
			value = dto.getValue();
			name = dto.getName();
		} else if(objDto instanceof LOptionDto) {
			val dto = (LOptionDto) objDto;
			id = CryptoUtil.idEncode(dto.getId());
			code = dto.getCode();
			value = dto.getValue();
			name = dto.getName();
		} else if(objDto instanceof BIOptionDto) {
			val dto = (BIOptionDto) objDto;
			id = CryptoUtil.idEncode(dto.getId());
			code = dto.getCode();
			value = dto.getValue();
			name = dto.getName();
		}
		
		return OptionDto
				.builder()
				.id(id)
				.code(code)
				.name(name)
				.value(value)
				.build();
	}
}